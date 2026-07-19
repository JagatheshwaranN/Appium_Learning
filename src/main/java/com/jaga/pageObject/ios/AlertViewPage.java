package com.jaga.pageObject.ios;

import com.jaga.util.ios.IOSAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AlertViewPage extends IOSAction {

    IOSDriver driver;

    public AlertViewPage(IOSDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`label=='Text Entry']")
    private WebElement textEntryMenu;

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell")
    private WebElement textBox;

    @iOSXCUITFindBy(accessibility = "OK")
    private WebElement okButton;

    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' AND value == 'Confirm / Cancel'")
    private WebElement confirmMenu;

    @iOSXCUITFindBy(iOSNsPredicate = "name BEGINSWITH[c] 'A message'")
    private  WebElement confirmationMessage;

    @iOSXCUITFindBy(iOSNsPredicate = "label=='Confirm'")
    private WebElement submit;

    public void fillTextBox(String value) {
        textEntryMenu.click();
        textBox.sendKeys(value);
        okButton.click();
    }

    public String getConfirmationMessage() {
        confirmMenu.click();
        return confirmationMessage.getText();
    }

}
