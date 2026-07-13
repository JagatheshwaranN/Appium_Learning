package com.jaga;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class AssignmentTest extends BaseTest {

    @Test
    public void alertDialogTest() throws URISyntaxException, MalformedURLException, InterruptedException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Jaga Phone");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.findElement(AppiumBy.accessibilityId("App")).click();
        driver.findElement(AppiumBy.accessibilityId("Alert Dialogs")).click();
        driver.findElement(By.id("io.appium.android.apis:id/two_buttons")).click();
        WebElement alertTitle = driver.findElement(By.xpath(
                "//android.widget.TextView[@resource-id='android:id/alertTitle']"));
        Assert.assertTrue(alertTitle.isDisplayed());
        driver.findElement(By.id("android:id/button1")).click();
        Assert.assertTrue(driver.findElement(By.id("io.appium.android.apis:id/two_buttons")).isDisplayed());
        Thread.sleep(2000);
        driver.quit();
    }

    @Test
    public void radioButtonTest() throws URISyntaxException, MalformedURLException, InterruptedException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Jaga Phone");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.findElement(AppiumBy.accessibilityId("App")).click();
        driver.findElement(AppiumBy.accessibilityId("Alert Dialogs")).click();
        driver.findElement(AppiumBy.accessibilityId("Single choice list")).click();
        WebElement modalTitle = driver.findElement(By.id("android:id/alertTitle"));
        Assert.assertTrue(modalTitle.isDisplayed());
        WebElement mapRadio = driver.findElement(By.xpath("//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='Map']"));
        WebElement satelliteRadio = driver.findElement(By.xpath("//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='Satellite']"));
        Assert.assertEquals(mapRadio.getAttribute("checked"), "true");
        Assert.assertEquals(satelliteRadio.getAttribute("checked"), "false");
        satelliteRadio.click();
        Assert.assertEquals(mapRadio.getAttribute("checked"), "false");
        Assert.assertEquals(satelliteRadio.getAttribute("checked"), "true");
        driver.findElement(By.id("android:id/button1")).click();
        Thread.sleep(2000);
        driver.quit();
    }

    @Test
    public void listDialogTest() throws URISyntaxException, MalformedURLException, InterruptedException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Jaga Phone");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.findElement(AppiumBy.accessibilityId("App")).click();
        driver.findElement(AppiumBy.accessibilityId("Alert Dialogs")).click();
        driver.findElement(AppiumBy.accessibilityId("List dialog")).click();
        WebElement modalTitle = driver.findElement(By.id("android:id/alertTitle"));
        Assert.assertTrue(modalTitle.isDisplayed());
        WebElement commandOneOption = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='android:id/text1' and @text='Command one']"));
        commandOneOption.click();
        WebElement message = driver.findElement(By.id("android:id/message"));
        Assert.assertTrue(message.isDisplayed());
        System.out.println(message.getText());
        Thread.sleep(2000);
        driver.quit();
    }

    @Test
    public void textBoxTest() throws URISyntaxException, MalformedURLException, InterruptedException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Jaga Phone");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.findElement(AppiumBy.accessibilityId("App")).click();
        driver.findElement(AppiumBy.accessibilityId("Alert Dialogs")).click();
        driver.findElement(AppiumBy.accessibilityId("Text Entry dialog")).click();
        WebElement modalTitle = driver.findElement(By.id("android:id/alertTitle"));
        Assert.assertTrue(modalTitle.isDisplayed());
        driver.findElement(By.id("io.appium.android.apis:id/username_edit")).sendKeys("Admin");
        driver.findElement(By.id("io.appium.android.apis:id/password_edit")).sendKeys("Admin@123");
        driver.findElement(By.id("android:id/button1")).click();
        Thread.sleep(2000);
        driver.quit();
    }

}
