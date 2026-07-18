package com.jaga.android.api_demo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class AppiumBasicsTest extends BaseTest {

    @Test
    public void appiumTest() throws URISyntaxException, MalformedURLException, InterruptedException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Jaga Phone");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        Thread.sleep(3000);
        driver.quit();

    }

    @Test
    public void appiumFixTest() throws URISyntaxException, MalformedURLException, InterruptedException {

        String appPackage = "io.appium.android.apis"; // ApiDemos package name
        String appPath = System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk";

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Jaga Phone");
        // don't set app here — we'll install manually after session starts
        options.setNoReset(true); // prevent Appium from auto-installing/resetting

        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);

        if (driver.isAppInstalled(appPackage)) {
            driver.removeApp(appPackage);
        }
        driver.installApp(appPath);
        driver.activateApp(appPackage);

        Thread.sleep(3000);
        driver.quit();
    }

}
