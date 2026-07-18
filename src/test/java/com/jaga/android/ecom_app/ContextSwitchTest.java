package com.jaga.android.ecom_app;


import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class ContextSwitchTest extends AppTest {

    @Test
    public void contextSwitchTest() throws InterruptedException {
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Jason");
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        Thread.sleep(1000);
        driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(0).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.androidsample.generalstore:id/productAddCart' and @text='ADD TO CART']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")), "text", "Cart"));
        List<WebElement> priceList = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        int count = priceList.size();
        double totalPrice = 0.0;
        for(int i = 0; i < count; i++) {
            String amount = priceList.get(i).getText();
            double price = Double.parseDouble(amount.substring(1));
            totalPrice = totalPrice + price;
        }
        String cartAmount = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        double cartTotal = Double.parseDouble(cartAmount.substring(1));
        Assert.assertEquals(cartTotal, totalPrice);

        WebElement termsAndCondition = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
        Assert.assertNotNull(((RemoteWebElement) termsAndCondition).getId());
        driver.executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) termsAndCondition).getId(), "duration", 2000));
        driver.findElement(By.id("android:id/button1")).click();
        driver.findElement(By.className("android.widget.CheckBox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(5000);
        Set<String> contexts = driver.getContextHandles();
        for(String context : contexts) {
            System.out.println(context);
        }
        driver.context("WEBVIEW_com.androidsample.generalstore");
        driver.findElement(By.name("q")).sendKeys("Appium");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.context("NATIVE_APP");
        Thread.sleep(3000);
    }

}
