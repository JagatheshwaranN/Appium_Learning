package com.jaga.android.api_demo;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;

public class BaseTest {

    private AppiumDriverLocalService service;

    @BeforeTest
    public void startServer() {
        //Server Start
        service = new AppiumServiceBuilder().withAppiumJS(new File("C://Users//jagat//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
                .withIPAddress("127.0.0.1").usingPort(4723).build();
        service.start();
    }

    @AfterTest
    public void stopServer() {
        // Server Stop
        service.stop();
    }
}
