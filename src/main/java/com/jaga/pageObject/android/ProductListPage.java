package com.jaga.pageObject.android;

import com.jaga.util.android.AndroidAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductListPage extends AndroidAction  {

    AndroidDriver driver;

    public ProductListPage(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
    private List<WebElement> addToCartButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.androidsample.generalstore:id/productAddCart' and @text='ADD TO CART']")
    private WebElement nextItemAddToCartButton;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
    private WebElement cartIcon;

    public void addItemsToCart(int index) {
        addToCartButton.get(index).click();
        waitFor(1);
        nextItemAddToCartButton.click();
        waitFor(1);
    }

    public CartPage goToCartPage() {
        cartIcon.click();
        waitFor(1);
        return new CartPage(driver);
    }

}
