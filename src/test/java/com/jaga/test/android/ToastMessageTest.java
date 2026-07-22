package com.jaga.test.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class ToastMessageTest extends AndroidAppTest {

    @BeforeMethod
    public void preConstruct() throws IOException, InterruptedException {
        // force-stop to clear whatever screen the previous test left the app on
        driver.terminateApp("com.androidsample.generalstore");

        // relaunch via monkey (implicit intent) — avoids the explicit component
        // start that throws the "not exported" SecurityException
        AppLauncher.launchViaMonkey("emulator-5554", "com.androidsample.generalstore");

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d ->
                ((AndroidDriver) d).queryAppState("com.androidsample.generalstore") == ApplicationState.RUNNING_IN_FOREGROUND
        );
    }

    @Test
    public void toastMessageTestPositive() throws InterruptedException {
        homePage.setGender("Female");
        homePage.submitForm();
        String toastMessage = homePage.getToastMessageContent();
        Assert.assertEquals(toastMessage, "Please enter your name");
        Thread.sleep(1000);
    }

    @Test
    public void toastMessageTestNegative() throws InterruptedException {
        homePage.setName("Jessy");
        homePage.setGender("Female");
        homePage.submitForm();
        Assert.assertTrue(homePage.isErrorToastNotDisplayed());
        Thread.sleep(1000);
    }

}
