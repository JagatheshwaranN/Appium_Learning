package com.jaga.learning.android.api_demo;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class PinchGestureTest extends BaseTest {

    @Test
    public void pinchGestureTest() throws URISyntaxException, InterruptedException, MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 4");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Start Activity
        driver.executeScript("mobile: startActivity", ImmutableMap.of(
                "intent", "com.google.android.apps.maps/com.google.android.maps.MapsActivity"));
        Thread.sleep(3000);
        //driver.findElement(By.xpath("//android.widget.Button[@text='SKIP']")).click();
        Thread.sleep(3000);
        driver.executeScript("mobile:pinchOpenGesture", ImmutableMap.of(
                "left", 200,
                "top", 470,
                "width", 600,
                "height", 600,
                "percent", 0.75
        ));
        Thread.sleep(3000);
        driver.executeScript("mobile:pinchCloseGesture", ImmutableMap.of(
                "left", 200,
                "top", 470,
                "width", 600,
                "height", 600,
                "percent", 0.75
        ));
        driver.quit();
    }



}
