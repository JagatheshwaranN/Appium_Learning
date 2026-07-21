package com.jaga.test.android;


import com.jaga.pageObject.android.CartPage;
import com.jaga.pageObject.android.ProductListPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaceOrderTest extends AndroidAppTest {

    @Test
    public void placeOrderTest() throws InterruptedException {

        homePage.setName("Jessy");
        homePage.setGender("Female");
        homePage.setCountrySelection("Argentina");
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

}
