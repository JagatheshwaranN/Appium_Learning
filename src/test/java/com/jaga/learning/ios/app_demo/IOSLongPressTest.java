package com.jaga.learning.ios.app_demo;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class IOSLongPressTest extends IOSBaseTest {

    @Test
    public void iosLongPressTest() {

        driver.findElement(AppiumBy.accessibilityId("Steppers")).click();
        WebElement element = driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`label == 'Increment'`][3]"));
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) element).getId());
        params.put("duration", 5);
        driver.executeScript("mobile:touchAndHold", params);
    }

}
