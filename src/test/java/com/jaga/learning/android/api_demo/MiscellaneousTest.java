package com.jaga.learning.android.api_demo;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class MiscellaneousTest extends BaseTest {

    @Test
    public void miscellaneousTest() throws URISyntaxException, InterruptedException, MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Jaga Phone");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//ApiDemos-debug.apk");
        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        driver.findElement(AppiumBy.accessibilityId("Preference")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='3. Preference dependencies']")).click();
        driver.findElement(By.id("android:id/checkbox")).click();

        // Turn into Landscape mode
        DeviceRotation rotation = new DeviceRotation(0, 0, 90);
        driver.rotate(rotation);

        driver.findElement(By.xpath(
                "(//android.widget.RelativeLayout)[2]")).click();
        String title = driver.findElement(By.id("android:id/alertTitle")).getText();
        Assert.assertEquals(title, "WiFi settings");

        // Set Clipboard Text
        driver.setClipboardText("Jaga Wifi");
        driver.findElement(By.id("android:id/edit")).sendKeys(driver.getClipboardText());

        // Keyboard Events
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        Thread.sleep(2000);
        driver.findElements(AppiumBy.className("android.widget.Button")).get(1).click();
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        Thread.sleep(2000);
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        Thread.sleep(1000);
        driver.quit();
    }

// Clipboard Action - Fix
//    @BeforeTest
//    public void setupClipboardIME() throws IOException, InterruptedException {
//        Process p1 = Runtime.getRuntime().exec("adb shell ime enable io.appium.settings/.UnicodeIME");
//        p1.waitFor();
//        Process p2 = Runtime.getRuntime().exec("adb shell ime set io.appium.settings/.UnicodeIME");
//        p2.waitFor();
//    }

//    @AfterTest
//    public void teardownClipboardIME() throws IOException, InterruptedException {
//        Process p1 = Runtime.getRuntime().exec("adb shell ime set com.google.android.inputmethod.latin/com.android.inputmethod.latin.LatinIME");
//        p1.waitFor();
//    }

}
