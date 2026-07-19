package com.jaga.learning.ios.default_ios_app;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IOSSwipeTest extends IOSDefaultBaseTest {

    @Test
    public void iosSliderTest() throws InterruptedException {

        Map<String, Object> params = new HashMap<>();
        params.put("bundleId", "com.apple.mobileslideshow");
        driver.executeScript("mobile:launchApp", params);
        driver.findElement(AppiumBy.iOSNsPredicateString("label=='All Photos'")).click();
        List<WebElement> allPhotos = driver.findElements(AppiumBy.iOSClassChain("**/XCUIElementTypeCell"));
        System.out.println(allPhotos.size());
        driver.findElement(By.xpath("//XCUIElementTypeCell[1]")).click();
        for (int i = 0; i < allPhotos.size(); i++) {
            System.out.println(driver.findElement(By.xpath("//XCUIElementTypeNavigationBar")).getAttribute("name"));
            Map<String, Object> params1 = new HashMap<>();
            params1.put("direction", "left");
            driver.executeScript("mobile:swipe", params1);
        }
        driver.navigate().back();
        driver.findElement(AppiumBy.accessibilityId("Albums")).click();
        Thread.sleep(2000);

    }

}
