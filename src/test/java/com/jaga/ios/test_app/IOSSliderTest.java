package com.jaga.ios.test_app;

import com.jaga.ios.app_demo.IOSBaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class IOSSliderTest extends IOSBaseTest {

    @Test
    public void iosSliderTest() throws InterruptedException {

        WebElement slider = driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeSlider[`label == 'AppElem'`]"));
        // 0% - start point, 0.5% - middle point, and 1% - Endpoint
        slider.sendKeys("0%");
        System.out.println(slider.getAttribute("value"));
        Thread.sleep(3000);
        slider.sendKeys("1%");
        System.out.println(slider.getAttribute("value"));
    }

}
