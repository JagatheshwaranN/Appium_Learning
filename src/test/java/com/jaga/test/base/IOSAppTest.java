package com.jaga.test.base;

import com.jaga.pageObject.ios.HomePage;
import com.jaga.util.appium.AppiumUtil;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class IOSAppTest extends AppiumUtil {

    public AppiumDriverLocalService service;
    public IOSDriver driver;
    public HomePage homePage;

    @BeforeTest
    public void startServer() throws URISyntaxException, MalformedURLException {
        //Server Start
        service = startAppiumServer(getDataFromPropFile("ipAddress"), Integer.parseInt(getDataFromPropFile("port")));

        XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName("iPhone 16 Pro");
        options.setApp("/Users/jaga/Desktop/UIKitCatalog.app");
        options.setPlatformVersion("17.5");
        // Appium -> WebDriver Agent -> IOS apps
        options.setWdaLaunchTimeout(Duration.ofSeconds(20));

        driver = new IOSDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePage = new HomePage(driver);
    }

    @AfterTest
    public void stopServer() {
        driver.quit();
        service.stop();
    }

}
