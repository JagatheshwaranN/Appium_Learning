package com.jaga.test.android;

import com.jaga.pageObject.android.HomePage;
import com.jaga.util.appium.AppiumUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class AndroidAppTest extends AppiumUtil {

    private AppiumDriverLocalService service;
    public AndroidDriver driver;
    public HomePage homePage;

    @BeforeClass
    public void startServer() throws URISyntaxException, IOException {
        //Server Start
        service = new AppiumServiceBuilder().withAppiumJS(new File("C://Users//jagat//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
                .withIPAddress("127.0.0.1").usingPort(4723).build();
        service.start();

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 5");
        options.setChromedriverExecutable(System.getProperty("user.dir") + "//src//test//resources//driver//chromedriver.exe");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//General-Store.apk");
        options.setAppPackage("com.androidsample.generalstore");
        options.setCapability("appium:autoLaunch", false);
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        AppLauncher.launchViaMonkey("emulator-5554", "com.androidsample.generalstore");
        // wait for app to actually be foregrounded rather than a flat sleep
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d ->
                ((AndroidDriver) d).queryAppState("com.androidsample.generalstore") == ApplicationState.RUNNING_IN_FOREGROUND
        );
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePage = new HomePage(driver);
    }

    @AfterClass
    public void stopServer() {
        driver.quit();
        // Server Stop
        service.stop();
    }
}
