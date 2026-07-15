package com.jaga.api_demo;

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

public class InteractionWithAppTest extends BaseTest {

    // App Interaction can be done with below utils
    // Xpath, Id, AccessibilityId, ClassName, AndroidUIAutomator

    @Test
    public void appInteractionTest() throws URISyntaxException, InterruptedException, MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Jaga Phone");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Accessibility Id
        driver.findElement(AppiumBy.accessibilityId("Preference")).click();
        // Xpath
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='3. Preference dependencies']")).click();
        // Id
        driver.findElement(By.id("android:id/checkbox")).click();
        // //android.widget.ListView[@resource-id='android:id/list']/android.widget.LinearLayout[2]/android.widget.RelativeLayout
        // Custom Xpath
        driver.findElement(By.xpath(
                "(//android.widget.RelativeLayout)[2]")).click();
        //Assert for Wifi Title
        String title = driver.findElement(By.id("android:id/alertTitle")).getText();
        Assert.assertEquals(title, "WiFi settings");
        // Id
        driver.findElement(By.id("android:id/edit")).sendKeys("Jaga Wifi");
        // ClassName
        driver.findElements(AppiumBy.className("android.widget.Button")).get(1).click();

        Thread.sleep(3000);
        driver.quit();
    }

}
