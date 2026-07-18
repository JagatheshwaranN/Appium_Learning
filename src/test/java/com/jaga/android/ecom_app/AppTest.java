package com.jaga.android.ecom_app;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class AppTest {

    private AppiumDriverLocalService service;
    public AndroidDriver driver;

    @BeforeTest
    public void startServer() throws URISyntaxException, MalformedURLException {
        //Server Start
        service = new AppiumServiceBuilder().withAppiumJS(new File("C://Users//jagat//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
                .withIPAddress("127.0.0.1").usingPort(4723).build();
        service.start();
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 5");
        options.setChromedriverExecutable(System.getProperty("user.dir") + "//src//test//resources//driver//chromedriver.exe");
        options.setApp(System.getProperty("user.dir") + "//src//test//resources//General-Store.apk");
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterTest
    public void stopServer() {
        // Server Stop
        service.stop();
    }
}
