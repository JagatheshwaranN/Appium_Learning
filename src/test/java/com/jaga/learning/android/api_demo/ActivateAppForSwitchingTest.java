package com.jaga.learning.android.api_demo;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.appmanagement.BaseActivateApplicationOptions;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class ActivateAppForSwitchingTest extends BaseTest {

    @Test
    public void activateAppForSwitchingTest() throws URISyntaxException, InterruptedException, MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 4");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.terminateApp("io.appium.android.apis");
        Thread.sleep(3000);
        driver.activateApp("com.android.settings");
        Thread.sleep(3000);
        driver.activateApp("io.appium.android.apis");
        Thread.sleep(2000);
        driver.quit();
    }


}
