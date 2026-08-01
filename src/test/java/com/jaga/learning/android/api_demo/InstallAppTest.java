package com.jaga.learning.android.api_demo;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.appmanagement.AndroidInstallApplicationOptions;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class InstallAppTest extends BaseTest {

    @Test
    public void installAppTest() throws URISyntaxException, InterruptedException, MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 4");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        Thread.sleep(3000);
        String appUrl = System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk";
        driver.installApp(appUrl, new AndroidInstallApplicationOptions().withReplaceEnabled());
        Thread.sleep(2000);

        driver.quit();
    }


}
