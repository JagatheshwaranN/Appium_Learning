package com.jaga.test.android;

import java.io.IOException;

public class AppLauncher {

    private static final String ADB_PATH =
            "C:\\Users\\jagat\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe";

    public static void launchViaMonkey(String deviceId, String appPackage) {
        ProcessBuilder pb = new ProcessBuilder(
                ADB_PATH, "-s", deviceId,
                "shell", "monkey",
                "-p", appPackage,
                "-c", "android.intent.category.LAUNCHER", "1"
        );
        pb.redirectErrorStream(true);
        Process process = null;
        try {
            process = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int exitCode = 0;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (exitCode != 0) {
            throw new RuntimeException("monkey launch failed with exit code " + exitCode);
        }
    }
}