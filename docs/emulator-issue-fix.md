# Appium/Android Emulator Issue: Restart Loop & Broken Pipe Fix

## Symptom
Appium sessions fail during creation with errors like:

```
Error executing adbExec. Original error: 'Command '...adb.exe -P 5037 -s emulator-5554 shell 'settings put global hidden_api_policy...'' exited with code 224'; Command output: cmd: Failure calling service settings: Broken pipe (32)
```

Followed by:

```
org.openqa.selenium.SessionNotCreatedException: Could not start a new session.
```

## Root Cause
The Android emulator was stuck in a **boot/restart loop**. ADB briefly sees the device (`adb devices` shows `emulator-5554`), but the connection drops mid-command because the emulator process restarts underneath it — producing the "Broken pipe" errors on any `adb shell` call, especially the `hidden_api_policy` settings writes that UiAutomator2 driver performs on session start.

Contributing factors to check:
- Corrupted AVD snapshot
- Memory/resource pressure (running Jenkins, IntelliJ, and the emulator simultaneously)
- Hypervisor conflicts (Hyper-V / WHPX / WSL2 / Docker Desktop VM backend)
- Using a very new/preview system image (e.g. API 37) instead of a stable release

## Diagnostic Steps

**1. Run the emulator directly (not via Android Studio) to see verbose crash/restart logs**
```powershell
%LOCALAPPDATA%\Android\Sdk\emulator\emulator.exe -avd "Jaga_Phone" -verbose
```

**2. Try a cold boot, skipping the snapshot**
```powershell
emulator -avd "Jaga_Phone" -no-snapshot-load
```
If this fixes it, the snapshot was corrupted — wipe it via **AVD Manager → device → dropdown → Wipe Data**.

**3. Check RAM/resource pressure**
Watch Task Manager while the emulator runs. Emulators often silently die under memory pressure on Windows without a clear OOM error.

**4. Check for hypervisor conflicts**
```powershell
bcdedit /enum | findstr /i hypervisor
```
Conflicts between Hyper-V/WSL2/Docker Desktop and the emulator's WHPX backend can cause instability.

**5. Check root access (also tells you the image type)**
```powershell
adb root
```
`adbd cannot run as root in production builds` → you're on a locked-down **Google Play** image; switch to a **Google APIs** image to preserve root access needed for automation.

## Fix: Kill, Delete, and Recreate the AVD

### 1. Kill the running emulator
```powershell
adb -e emu kill
```
If it won't close (common when stuck restarting), force-kill:
```powershell
taskkill /F /IM qemu-system-x86_64.exe
taskkill /F /IM emulator.exe
```
Confirm nothing remains:
```powershell
adb devices
```

### 2. Delete the AVD
```powershell
"%LOCALAPPDATA%\Android\Sdk\cmdline-tools\latest\bin\avdmanager.bat" delete avd -n "Jaga_Phone"
```
Or via Android Studio: **Tools → Device Manager → ⋮ menu → Delete**.

### 3. Clean up leftover files (optional but recommended)
```powershell
dir %USERPROFILE%\.android\avd
```
Remove any orphaned `Jaga_Phone.avd` folder or `Jaga_Phone.ini` left behind after crash loops.

### 4. Create a new AVD
Via Android Studio: **Tools → Device Manager → Create Device** → pick device profile (e.g. Pixel 5) → choose a system image.

**Recommended image choice:**
- Use a **stable, well-tested API level** (e.g. API 33 / Android 13 or API 34 / Android 14) rather than a preview/bleeding-edge version (API 37 was the likely source of the original instability)
- Choose **Google APIs** (not Google Play) if root access is needed for automation
- Set RAM to **4GB+** and enable hardware acceleration (WHPX) if not already enabled

## Fallback Workaround (if hidden API policy error recurs on a healthy emulator)
Add this capability to skip the failing step without blocking session creation:
```java
options.setCapability("appium:ignoreHiddenApiPolicyError", true);
```
