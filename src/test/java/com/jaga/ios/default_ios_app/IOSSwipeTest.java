package com.jaga.ios.default_ios_app;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class IOSSwipeTest extends IOSDefaultBaseTest {

    @Test
    public void iosSliderTest() throws InterruptedException {

        Map<String, Object> params = new HashMap<>();
        params.put("bundleId", "com.apple.mobileslideshow");
        driver.executeScript("mobile:launchApp", params);
        Thread.sleep(2000);
    }

}
