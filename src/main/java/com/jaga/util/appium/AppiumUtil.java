package com.jaga.util.appium;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AppiumUtil {


    public AppiumDriver driver;
    public WebDriverWait wait;

    public AppiumUtil(AppiumDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public double getFormatAmountValue(String amount) {
        return Double.parseDouble(amount.substring(1));
    }

    public void waitForElementAttributeCheck(WebElement element, String value) {
        wait.until(ExpectedConditions.attributeContains(element, "text", value));
    }

    public String getElementAttributeValue(WebElement element, String attribute) {
            return element.getAttribute(attribute);
    }


}
