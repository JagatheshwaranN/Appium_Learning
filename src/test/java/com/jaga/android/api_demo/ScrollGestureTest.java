package com.jaga.android.api_demo;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class ScrollGestureTest extends BaseTest {

    @Test
    private void scrollGestureApproach1() throws InterruptedException, URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Jaga Phone");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"WebView\"));"
        ));

        // Alternate Option
//        driver.findElement(AppiumBy.androidUIAutomator(
//                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"WebView\").instance(0));"
//        ));
        Thread.sleep(3000);
        driver.quit();
    }

    @Test
    private void scrollGestureApproach2() throws InterruptedException, URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Jaga Phone");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        boolean canScrollMore;
        do {
            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", "down",
                    "percent", 3.0
            ));
        }while (canScrollMore);
        Thread.sleep(3000);
        driver.quit();
    }

}
