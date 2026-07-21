package com.jaga.pageObject.android;

import com.jaga.util.android.AndroidAction;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends AndroidAction {

    AndroidDriver driver;

    public HomePage(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
    private WebElement nameField;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
    private WebElement femaleRadioButton;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioMale")
    private WebElement maleRadioButton;

    @AndroidFindBy(id = "android:id/text1")
    private WebElement countryDropdown;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    private WebElement letShopButton;

    @AndroidFindBy(xpath = "(//android.widget.Toast)[1]")
    private List<WebElement> toastMessage;


    public void setName(String name) {
        nameField.sendKeys(name);
        driver.hideKeyboard();
    }

    public void setGender(String genderType) {
        if (genderType.contains("Female")) {
            femaleRadioButton.click();
        } else {
            maleRadioButton.click();
        }
    }

    public void setCountrySelection(String country) {
        countryDropdown.click();
        scrollToText(country);
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id='android:id/text1' and @text='" + country + "']")).click();
    }

    public ProductListPage submitForm() {
        letShopButton.click();
        return new ProductListPage(driver);
    }

    public List<WebElement> toastMessage() {
        return toastMessage;
    }

    public String getToastMessageContent() {
        return getElementAttributeValue(toastMessage.get(0), "name");
    }

}
