package com.jaga.test.ios;

import com.jaga.pageObject.ios.AlertViewPage;
import com.jaga.test.base.IOSAppTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IOSBasicsTest  extends IOSAppTest {

    @Test
    public void iosBasicsTest() {

        AlertViewPage alertViewPage = homePage.selectAlertViews();
        alertViewPage.fillTextBox("Jason");
        String actualMessage = alertViewPage.getConfirmationMessage();
        Assert.assertEquals(actualMessage, "A message should be a short, complete sentence.");
    }

}
