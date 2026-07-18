package com.jaga.ios.app_demo;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class IOSPickerTest extends IOSBaseTest {

    @Test
    public void iosScrollTest() throws InterruptedException {

        driver.findElement(AppiumBy.accessibilityId("Picker View")).click();
        driver.findElement(AppiumBy.accessibilityId("Red color component value")).sendKeys("80");
        driver.findElement(AppiumBy.accessibilityId("Green color component value")).sendKeys("220");
        driver.findElement(AppiumBy.iOSNsPredicateString("label=='Blue color component value'")).sendKeys("105");
        String value = driver.findElement(AppiumBy.iOSNsPredicateString("label=='Blue color component value'")).getText();
        Assert.assertEquals(value, "105");
        Thread.sleep(2000);
    }

}
