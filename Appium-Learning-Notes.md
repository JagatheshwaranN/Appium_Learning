# Appium Learning Notes

Personal notes from my Appium mobile automation learning journey, covering setup, Android, iOS, real device testing, and BrowserStack integration.

## Table of Contents

- [General Appium](#general-appium)
  - [Architecture](#architecture)
  - [Setup Overview](#setup-overview)
- [Android](#android)
  - [Android Studio & Appium Server Setup](#android-studio--appium-server-setup)
  - [Useful References](#useful-references)
  - [Manual App Installation](#manual-app-installation)
  - [Switching to Mobile Web Context](#switching-to-mobile-web-context)
  - [ChromeDriver Versions](#chromedriver-versions)
  - [Real Device Testing (Android)](#real-device-testing-android)
- [iOS](#ios)
  - [Sample App & Xcode Setup](#sample-app--xcode-setup)
  - [Appium Configuration for iOS](#appium-configuration-for-ios)
  - [Bundle IDs](#bundle-ids)
  - [Real Device Testing (iOS)](#real-device-testing-ios)
  - [Creating a Provisioning Profile for Appium Tests](#creating-a-provisioning-profile-for-appium-tests)
- [CI/CD](#cicd)
- [BrowserStack Integration](#browserstack-integration)
  - [Setup Steps](#setup-steps)
  - [Useful cURL Commands](#useful-curl-commands)

---

## General Appium

### Architecture

```
Appium Client Code -> JSON -> Appium Server -> UIAutomator2 -> Android
Appium Client Code -> JSON -> Appium Server -> XCUITest    -> iOS
```

### Setup Overview

High-level steps to get an Appium environment up and running:

1. Download and install Java.
2. Download Android Studio and locate the Android SDK path.
3. Download Node.js.
4. Set `JAVA_HOME`, `ANDROID_HOME`, and `NODE` paths as Windows system environment variables.
5. Open Android Studio and configure an emulator.
6. Install and start the Appium Server via Node/npm.
7. Install an IDE (e.g., IntelliJ).
8. Understand Desired Capabilities to configure the Appium environment.
9. Write and run your first Appium program to launch an Android app.

---

## Android

### Android Studio & Appium Server Setup

1. Navigate to `C:\Users\<user>\AppData\Local` and check for an `Android` folder. By default it won't exist until you complete the setup wizard when first opening Android Studio.
2. In the setup wizard, select "No Activity" project and create it. Wait for the build to succeed — the `Android` folder should now appear inside `Local`.
3. In Android Studio, go to **Tools > SDK Manager > SDK Tools**. Uncheck "Hide Obsolete Tools", then select and install SDK Tools. A `tools` folder should now appear inside the `Android` folder.
4. Set environment variables:
   - **User variable**: `ANDROID_HOME` → `C:\Users\<user>\AppData\Local\Android\Sdk`
   - **System PATH**:
     - `C:\Users\<user>\AppData\Local\Android\Sdk\tools\bin`
     - `C:\Users\<user>\AppData\Local\Android\Sdk\tools`
     - `C:\Users\<user>\AppData\Local\Android\Sdk\platform-tools`
5. Add npm to the **system PATH**:
   - `C:\Program Files\nodejs\node_modules\npm\bin`
6. Reopen Android Studio, go to **Tools > Device Manager**, select a device type, and install the required dependencies. Once complete, the Virtual Device option should be available.
7. Install the Appium Server (run CMD as Administrator):
   ```
   npm install -g appium
   ```
8. Set up an IntelliJ project for Appium and add the Appium Java Client dependency.
9. Check the list of available drivers:
   ```
   appium driver list
   ```
10. Install the required drivers:
    ```
    appium driver install uiautomator2
    appium driver install gecko
    appium driver install chromium
    ```
11. Write your first Appium test and start the Appium server before executing it.
12. To start/stop the Appium Server programmatically, reference:
    ```
    C:\Users\<user>\AppData\Roaming\npm\node_modules\appium\build\lib\main.js
    ```
13. Elements can be interacted with using locator strategies such as: **XPath, ID, AccessibilityId, ClassName, AndroidUIAutomator**.
14. Download the Appium Inspector: [appium-inspector releases](https://github.com/appium/appium-inspector/releases)
15. After installing Appium Inspector, use the following Desired Capabilities:
    ```json
    {
      "appium:app": "D://Environments//Intellij//Appium_Learning//src//test//resources//ApiDemos-debug.apk",
      "appium:deviceName": "Jaga Phone",
      "appium:platformName": "android",
      "appium:automationName": "UIAutomator2"
    }
    ```
16. Start the Appium server in a terminal **before** clicking "Start Session" in Appium Inspector.
17. Once connected, the app opens in the Appium Inspector dashboard, where you can begin inspecting elements.

### Useful References

- Android mobile gestures: [appium-uiautomator2-driver gestures docs](https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md)
- Finding app package and activity: [Testsigma troubleshooting guide](https://testsigma.com/docs/troubleshooting/mobile-apps/app-package-and-activity/)
- Starting a mobile activity: [appium-uiautomator2-driver](https://github.com/appium/appium-uiautomator2-driver)

### Manual App Installation

Start the emulator first (`emulator-5554`), then:

```
adb devices
List of devices attached
emulator-5554   device

cd C:\Users\<user>\AppData\Local\Android\Sdk\platform-tools

adb install C:\Users\<user>\Downloads\General-Store.apk
Performing Streamed Install
Success
```

### Switching to Mobile Web Context

Use the `getContextHandles()` method to switch from native app context to mobile web (WebView/browser) context.

### ChromeDriver Versions

To download a specific ChromeDriver version for testing hybrid/web contexts: [Chrome for Testing](https://googlechromelabs.github.io/chrome-for-testing/)

### Real Device Testing (Android)

Android real device testing (remote debugging) is relatively straightforward:
[Chrome DevTools remote debugging docs](https://developer.chrome.com/docs/devtools/remote-debugging)

---

## iOS

> Note: The following section applies to a macOS environment.

### Sample App & Xcode Setup

1. Sample app: [appium/ios-uicatalog](https://github.com/appium/ios-uicatalog). Clone or download the project — the `UIKitCatalog` folder contains `UIKitCatalog.xcodeproj`, which needs to be compiled.
2. Download Xcode from the App Store (used to develop and compile iOS apps).
3. After opening the project in Xcode, locate the `UIKitCatalog.app` file under the **Products** folder.
4. Select your iPhone version/target from the top bar to compile and run the app. Once compiled, the `.app` file can be stored anywhere on the system.
5. The iPhone simulator opens and deploys the app.
6. In a terminal, list available drivers and install the XCUITest driver:
   ```
   appium driver list
   appium driver install xcuitest
   ```
7. Open IntelliJ and create a project for hands-on testing.
8. Create a base test file, similar to the Android setup, but using iOS syntax and methods.
9. Appium Inspector is also used to inspect iOS apps, using iOS-specific Desired Capabilities.

### Appium Configuration for iOS

```json
{
  "appium:app": "/Users/<user>/Desktop/UIKitCatalog.app",
  "appium:automationName": "XCUITest",
  "appium:deviceName": "iPhone 16 Pro",
  "appium:platformVersion": "17.5",
  "platformName": "iOS"
}
```

You can also directly reference a compiled `.app` (e.g., `TestApp_3.app`) from IntelliJ.

### Bundle IDs

The **Bundle ID** is the key used to identify an iOS app.

References for finding default app Bundle IDs:
- [Apple: Bundle IDs for iPhone and iPad apps](https://support.apple.com/guide/deployment/bundle-ids-for-iphone-and-ipad-apple-apps-depece748c41/web)
- [iOS Bundle ID Finder](https://iosbundleidfinder.vercel.app/)

### Real Device Testing (iOS)

Real device testing on iOS is not as straightforward as Android — additional tools are required.

**Install Homebrew:**
```
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```

**Install required tools:**
```
brew install libimobiledevice
brew install ios-deploy
```

**Four additional capabilities are needed for real device testing:**
```java
obj.setCapability("xcodeOrgId", "XXXXX");
obj.setCapability("xcodeSigningId", "iPhone Developer");
obj.setCapability("udid", "XXXXX");
obj.setCapability("updateWDABundleId", "XXXXX");
```

**How to get `xcodeOrgId`:**
This is the Team ID generated by Apple. Sign in to [developer.apple.com/account](https://developer.apple.com/account), click **Membership** in the sidebar — the Team ID appears under **Membership Information**.

**How to get `udid`:**
[WikiHow: Obtain the UDID for an iPhone, iPod, or iPad](https://www.wikihow.com/Obtain-the-Identifier-Number-(UDID)-for-an-iPhone,-iPod-or-iPad)

**What is `updateWDABundleId`?**
Running tests on real devices typically requires a Provisioning Profile from Apple. This profile generates a Bundle ID, which is used to sign the app so it can run on the device.

### Creating a Provisioning Profile for Appium Tests

Reference: [Appium docs — iOS XCUITest real devices](https://appium.io/docs/en/drivers/ios-xcuitest-real-devices/)

General flow:

1. Connect your device.
2. Verify the developer certificate is trusted on the device.
3. Build the **WebDriverAgentRunner** project with the correct provisioning profile (this is needed to obtain the Bundle ID):
   - Open Xcode → Create New Project.
   - **Product Name**: `WebDriverAgentRunner`
   - **Team**: Your Apple Developer Team ID (requires an Apple Developer account).
   - **Organization Name**: Any unique name.
   - **Organization Identifier**: Any unique identifier, e.g. `io.appium.jaga`.
   - **Bundle Identifier** (auto-generated): `io.appium.jaga.WebDriverAgentRunner`
   - Click **Next**, choose a save location, and click **Create**.
   - If you see "Signing for 'WebDriverAgentRunner' requires a development team," select your team from the dropdown — this creates the Provisioning Profile.
4. Build the app under test in Xcode with your connected device, which generates the `.app` file.
5. Fix any build errors as needed.
6. Reference the resulting `.app` file path in your Desired Capabilities.

---

## CI/CD

- Scheduling Appium test runs as Jenkins jobs: [CloudBees blog — How to schedule a Jenkins job](https://www.cloudbees.com/blog/how-to-schedule-a-jenkins-job)

---

## BrowserStack Integration

### Setup Steps

1. Create a BrowserStack account or log in with Google.
2. Click the user icon (top right) → **Settings** to get your username and access key.
3. Click the user icon (top right) → **Manage Projects** to view existing projects or create a new one.
4. Navigate to **App Live** (top nav) or the **Web icon** (left nav) → **App → App Live**.
5. Use **Upload your App** to upload the test app's `.apk` file.
6. Go to **App Automate** (left nav) → **Overview** → **App Management** to view uploaded app details and retrieve the app ID.
7. In IntelliJ IDEA, install the BrowserStack plugin, then right-click the project → **BrowserStack → Integrate with BrowserStack App Automate SDK**.
8. Follow the on-screen prompts — this updates `pom.xml` and generates a `browserstack.yml` file containing your BrowserStack configuration.
9. To run the project locally after integrating with BrowserStack, override the config with:
   ```
   BROWSERSTACK_AUTOMATION=false mvn test -PRegression
   ```

### Useful cURL Commands

**Upload an app:**
```bash
curl -u "YOUR_USERNAME:YOUR_ACCESS_KEY" \
  -X POST "https://api-cloud.browserstack.com/app-automate/upload" \
  -F "file=@/path/to/app/file/Application-debug.apk"
```

**Get recently uploaded apps:**
```bash
curl -u "YOUR_USERNAME:YOUR_ACCESS_KEY" \
  -X GET "https://api-cloud.browserstack.com/app-automate/recent_group_apps"
```
