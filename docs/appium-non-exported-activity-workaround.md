# Appium Android: "Not Exported" SecurityException — Issue & Workaround

## Environment
- **App under test:** `General-Store.apk` (`com.androidsample.generalstore`) — public Appium tutorial sample app
- **Automation stack:** Java, Appium Java client, `AndroidDriver`, `UiAutomator2Options`, TestNG
- **Platform:** Windows 11, Android emulator (`emulator-5554`)
- **Emulator API level:** 31+ (Android 12 or newer)

---

## The Issue

Every attempt to launch the app via an **explicit activity component** failed with:

```
org.openqa.selenium.WebDriverException: Error executing adbExec. Original error:
'Command '...\adb.exe -P 5037 -s emulator-5554 shell am start-activity
com.androidsample.generalstore/com.androidsample.generalstore.MainActivity' exited with code 255';
Command output:
Exception occurred while executing 'start-activity':
java.lang.SecurityException: Permission Denial: starting Intent
{ act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] ... }
from null (pid=..., uid=2000) not exported from uid 10232
```

This error surfaced from **three different code paths**, all of which internally issue the same kind of explicit component start (`am start -n package/.Activity`):

1. Appium's normal session-start auto-launch (driven by `appPackage`/`appActivity` capabilities)
2. `driver.activateApp("com.androidsample.generalstore")`
3. `driver.executeScript("mobile: startActivity", ...)` with an explicit `package/.Activity` intent string

---

## Root Cause

Since **Android 12 (API 31)**, any `<activity>` with an `<intent-filter>` must explicitly declare `android:exported="true"` in its manifest, or the OS blocks any external process (adb, Appium, etc.) from starting it via a direct component reference.

`General-Store.apk` is an old tutorial APK that predates this requirement — its `MainActivity` is effectively treated as **not exported** by the OS on a modern (API 31+) emulator image. This is a property of the app's manifest/target SDK, not a misconfiguration in the test framework.

### How this was confirmed
Running the launch manually via `monkey` (which resolves the launcher activity through **implicit** intent/package-manager resolution rather than an explicit component reference) succeeded:

```
adb shell monkey -p com.androidsample.generalstore -c android.intent.category.LAUNCHER 1
```
```
Events injected: 1
```

This proved the activity itself works fine — only the *explicit* component start path is blocked. That ruled out a bad `appActivity` value and confirmed the export restriction as the actual cause.

---

## Options Considered

| Option | Outcome |
|---|---|
| Fix `appActivity` capability value | Not the issue — value was already correct |
| `driver.activateApp()` | Fails — uses explicit component start internally |
| `mobile: startActivity` with explicit intent | Fails — same explicit component start |
| `appium:autoLaunch=false` + manual `monkey` launch | **Works** |
| Downgrade emulator to API ≤30 | Would also work, not used (kept current emulator/API) |
| Rebuild APK with `android:exported="true"` | Would also work, not used (avoided modifying the APK) |

---

## Final Working Setup

### 1. Session creation — disable auto-launch, launch manually via `monkey`

```java
UiAutomator2Options options = new UiAutomator2Options();
options.setDeviceName("Pixel 5");
options.setChromedriverExecutable(System.getProperty("user.dir") + "//src//test//resources//driver//chromedriver.exe");
options.setApp(System.getProperty("user.dir") + "//src//test//resources//General-Store.apk");
options.setAppPackage("com.androidsample.generalstore");
options.setCapability("appium:autoLaunch", false); // prevent Appium's own explicit-launch attempt

driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);

AppLauncher.launchViaMonkey("emulator-5554", "com.androidsample.generalstore");

new WebDriverWait(driver, Duration.ofSeconds(10)).until(d ->
        ((AndroidDriver) d).queryAppState("com.androidsample.generalstore") == ApplicationState.RUNNING_IN_FOREGROUND
);
```

### 2. Helper class — launches the app via implicit intent (`monkey`)

```java
public class AppLauncher {

    private static final String ADB_PATH =
        "C:\\Users\\jagat\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe";

    public static void launchViaMonkey(String deviceId, String appPackage) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
            ADB_PATH, "-s", deviceId,
            "shell", "monkey",
            "-p", appPackage,
            "-c", "android.intent.category.LAUNCHER", "1"
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("monkey launch failed with exit code " + exitCode);
        }
    }
}
```

### 3. Per-test reset — force-stop + relaunch to guarantee a known starting screen

```java
@BeforeMethod
public void preConstruct() throws IOException, InterruptedException {
    // force-stop to clear whatever screen the previous test left the app on
    driver.terminateApp("com.androidsample.generalstore");

    // relaunch via monkey (implicit intent) — avoids the explicit component
    // start that throws the "not exported" SecurityException
    AppLauncher.launchViaMonkey("emulator-5554", "com.androidsample.generalstore");

    new WebDriverWait(driver, Duration.ofSeconds(10)).until(d ->
            ((AndroidDriver) d).queryAppState("com.androidsample.generalstore") == ApplicationState.RUNNING_IN_FOREGROUND
    );
}
```

`driver.terminateApp()` is safe to use here since it force-stops the process directly and does not go through `am start` — it isn't affected by the export restriction.

---

## Notes / Caveats

- **`emulator-5554` is currently hardcoded** in multiple places. If running multiple emulators/devices (parallel execution, CI), pull the device ID dynamically instead of hardcoding it, or centralize it as a constant (e.g., `AndroidAppTest.DEVICE_ID`).
- **This workaround is specific to this app.** It exists only because `General-Store.apk`'s `MainActivity` is not exported. A properly manifested app targeting API 31+ correctly (with `android:exported="true"` set) would work fine with Appium's normal `appActivity`/`autoLaunch`/`noReset` capabilities — no `monkey` or manual `terminateApp` dance needed.
- Keep this pattern isolated inside a helper class (`AppLauncher`) so it doesn't leak into page objects or test logic — the `@Test` methods themselves stay unaffected by the workaround.
