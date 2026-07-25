package com.jaga.test.base;

import com.jaga.pageObject.android.HomePage;
import com.jaga.test.android.AppLauncher;
import com.jaga.util.appium.AppiumUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;

public class AndroidAppTest extends AppiumUtil {

    private AppiumDriverLocalService service;
    public AndroidDriver driver;
    public HomePage homePage;

    @BeforeClass(alwaysRun = true)
    public void startServer() throws URISyntaxException, IOException {
        loadPropertyFile();
        //Server Start
        String ipAddress = System.getProperty("ipAddress") != null
                ? System.getProperty("ipAddress") :
                getDataFromPropFile("ipAddress");

        service = startAppiumServer(ipAddress, Integer.parseInt(getDataFromPropFile("port")));

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(getDataFromPropFile("androidDeviceName"));
        options.setChromedriverExecutable(System.getProperty("user.dir") + "//src//test//resources//driver//chromedriver.exe");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//General-Store.apk");
        options.setAppPackage("com.androidsample.generalstore");
        options.setCapability("appium:autoLaunch", false);
        options.setCapability("appium:ignoreHiddenApiPolicyError", true);
        driver = new AndroidDriver(service.getUrl(), options);
        AppLauncher.launchViaMonkey("emulator-5554", "com.androidsample.generalstore");
        // wait for app to actually be foregrounded rather than a flat sleep
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d ->
                ((AndroidDriver) d).queryAppState("com.androidsample.generalstore") == ApplicationState.RUNNING_IN_FOREGROUND
        );
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePage = new HomePage(driver);
    }

    @AfterClass(alwaysRun = true)
    public void stopServer() {
        driver.quit();
        // Server Stop
        service.stop();
    }
}
