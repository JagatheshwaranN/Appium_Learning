package com.jaga.test.android;


import com.jaga.pageObject.android.CartPage;
import com.jaga.pageObject.android.ProductListPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class PlaceOrderTest extends AndroidAppTest {

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

    @Test(dataProvider = "provideData")
    public void placeOrderTest(HashMap<String, String> data) throws InterruptedException {

        homePage.setName(data.get("name"));
        homePage.setGender(data.get("gender"));
        homePage.setCountrySelection(data.get("country"));
        ProductListPage productListPage = homePage.submitForm();
        productListPage.addItemsToCart(0);
        CartPage cartPage = productListPage.goToCartPage();
        cartPage.verifyCarTitle("Cart");
        double actualTotal = cartPage.calculateCartTotal();
        double expectedTotal = cartPage.getCartTotalOnPage();
        Assert.assertEquals(actualTotal, expectedTotal);
        cartPage.acceptTermsAndCondition();
        cartPage.placeOrder();
        Thread.sleep(3000);
    }

    @DataProvider
    public Object[][] provideData() {
        List<HashMap<String, String>> data = fetchJsonData(System.getProperty("user.dir") + "//src//test//resources//testData//generalstore.json");
        return new Object[][]{
                {data.get(0)},
                {data.get(1)}
        };
    }

}
