package com.jaga.learning.android.mobile_browser;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoAppTest extends MobileBrowserTest {

    @Test
    public void launchChromeTest() throws InterruptedException {
        driver.get("https://www.google.com/");
        System.out.println(driver.getTitle());
        driver.findElement(By.name("q")).sendKeys("Appium");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);
    }

    @Test
    public void demoAppTest() throws InterruptedException {
        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        System.out.println(driver.getTitle());
        driver.findElement(By.xpath("//span[@class='navbar-toggler-icon']")).click();
        driver.findElement(By.cssSelector("a[routerlink*='products']")).click();
        driver.executeScript("window.scrollBy(0, 1000)", "");
        String text = driver.findElement(By.cssSelector("a[href*='products/3']")).getText();
        Assert.assertEquals(text, "Devops");
        Thread.sleep(3000);
    }

}
