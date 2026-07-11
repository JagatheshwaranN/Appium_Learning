package com.jaga;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class SwipeGestureTest extends BaseTest {

    @Test
    public void swipeGestureTest() throws URISyntaxException, MalformedURLException, InterruptedException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Jaga Phone");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Photos")).click();
        WebElement firstImage = driver.findElement(By.xpath("//android.widget.Gallery[@resource-id='io.appium.android.apis:id/gallery']/android.widget.ImageView[1]"));
        Assert.assertEquals(firstImage.getAttribute("focusable"), "true");
        Assert.assertNotNull(((RemoteWebElement) firstImage).getId());
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement)firstImage).getId(),
                "direction", "left",
                "percent", 0.5
        ));
        Assert.assertEquals(driver.findElement(By.xpath("//android.widget.Gallery[@resource-id='io.appium.android.apis:id/gallery']/android.widget.ImageView[1]")).getAttribute("focusable"), "false");
        Thread.sleep(3000);
        driver.quit();
    }

}
