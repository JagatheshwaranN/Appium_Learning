package com.jaga.android.api_demo;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class StartActivityAppTest extends BaseTest {

    @Test
    public void startActivityAppTest() throws URISyntaxException, InterruptedException, MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Jaga Phone");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Start Activity
        driver.executeScript("mobile: startActivity", ImmutableMap.of(
                "intent", "io.appium.android.apis/io.appium.android.apis.preference.PreferenceDependencies"));

        driver.findElement(By.id("android:id/checkbox")).click();
        driver.findElement(By.xpath(
                "(//android.widget.RelativeLayout)[2]")).click();
        String title = driver.findElement(By.id("android:id/alertTitle")).getText();
        Assert.assertEquals(title, "WiFi settings");
        driver.findElement(By.id("android:id/edit")).sendKeys("Jaga Wifi");
        driver.findElements(AppiumBy.className("android.widget.Button")).get(1).click();
        Thread.sleep(3000);
        driver.quit();
    }

}
