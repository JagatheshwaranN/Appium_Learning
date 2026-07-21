package com.jaga.util.android;

import com.google.common.collect.ImmutableMap;
import com.jaga.util.appium.AppiumUtil;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

public class AndroidAction extends AppiumUtil {

    AndroidDriver driver;

    public AndroidAction(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void scrollToText(String text) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"" + text + "\").instance(0));"
        ));
    }

    public void longPress(WebElement element) {
        assert ((RemoteWebElement) element).getId() != null;
        driver.executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(), "duration", 2000));
    }

    public void waitFor(int time) {
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
