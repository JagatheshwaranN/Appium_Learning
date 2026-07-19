package com.jaga.learning.ios.app_demo;

import io.appium.java_client.AppiumBy;
import org.testng.annotations.Test;

public class IOSBasicsTest  extends IOSBaseTest {

    @Test
    public void iosBasicsTest() {

        // Locator Strategies - XPath, ClassName, AccessibilityId, Id, IOS, IOSClassChain, IOSPredicateString
        // IOSClassChain is almost same as XPath, but it is faster than XPath on IOS.
        driver.findElement(AppiumBy.accessibilityId("Alert View")).click();

        // XPath
        // driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='Text Entry']")).click();

        // IOSClassChain
        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`label=='Text Entry']")).click();
        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeCell")).sendKeys("Hello!");
        driver.findElement(AppiumBy.accessibilityId("OK")).click();

        // Different ways of using the IOSPredicate String
        driver.findElement(AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND value == 'Confirm / Cancel'"));
        // [c] - Case Sensitive
        // driver.findElement(AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND value BEGINSWITH[c] 'Confirm'"));
        // driver.findElement(AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND value ENDSWITH[c] 'Cancel'"));

        String text = driver.findElement(AppiumBy.iOSNsPredicateString("name BEGINSWITH[c] 'A message'")).getText();
        System.out.println(text);
        driver.findElement(AppiumBy.iOSNsPredicateString("label=='Confirm'")).click();
    }

}
