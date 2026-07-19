package com.jaga.util.ios;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IOSAction {

    IOSDriver driver;
    public WebDriverWait wait;

    public IOSAction(IOSDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }


    public void waitFor(int time) {
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
