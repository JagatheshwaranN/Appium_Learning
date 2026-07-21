package com.jaga.pageObject.android;

import com.jaga.util.android.AndroidAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AndroidAction  {

    AndroidDriver driver;

    public CartPage(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
    private WebElement cartTitle;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
    private List<WebElement> productPriceList;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
    private WebElement cartAmount;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
    private WebElement termsAndCondition;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement tacCloseButton;

    @AndroidFindBy(className = "android.widget.CheckBox")
    private WebElement promotionsCheckBox;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
    private WebElement completeOrder;


    public void verifyCarTitle(String title) {
       waitForElementAttributeCheck(cartTitle, title);
    }

    public double calculateCartTotal() {
        int count = productPriceList.size();
        double totalPrice = 0.0;
        for(int i = 0; i < count; i++) {
            String amount = productPriceList.get(i).getText();
            double price = Double.parseDouble(amount.substring(1));
            totalPrice = totalPrice + price;
        }
        return totalPrice;
    }

    public double getCartTotalOnPage() {
        return getFormatAmountValue(cartAmount.getText());
    }

    public void acceptTermsAndCondition() {
        longPress(termsAndCondition);
        tacCloseButton.click();
    }

    public void placeOrder() {
        promotionsCheckBox.click();
        completeOrder.click();
    }

}
