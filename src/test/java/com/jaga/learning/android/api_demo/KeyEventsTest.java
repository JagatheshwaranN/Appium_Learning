package com.jaga.learning.android.api_demo;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class KeyEventsTest extends BaseTest {

    @Test
    public void keyEventsTest() throws URISyntaxException, InterruptedException, MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 4");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"TextFields\"));"
        ));
        driver.findElement(AppiumBy.accessibilityId("TextFields")).click();
        driver.findElement(By.id("io.appium.android.apis:id/edit")).click();
        Thread.sleep(2000);
        driver.pressKey(new KeyEvent().withKey(AndroidKey.Q));
        driver.pressKey(new KeyEvent().withKey(AndroidKey.W));
        driver.pressKey(new KeyEvent().withKey(AndroidKey.E));
        driver.pressKey(new KeyEvent().withKey(AndroidKey.R));
        driver.pressKey(new KeyEvent().withKey(AndroidKey.T));
        driver.pressKey(new KeyEvent().withKey(AndroidKey.Y));
        Thread.sleep(2000);
        openSystemsUsingKeyEvents(driver);
        driver.quit();
    }

    private void openSystemsUsingKeyEvents(AndroidDriver driver) throws InterruptedException {
        driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
        Thread.sleep(2000);
        driver.pressKey(new KeyEvent().withKey(AndroidKey.CALENDAR));
        Thread.sleep(5000);
    }

}
