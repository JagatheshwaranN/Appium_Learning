package com.jaga.util.ios;

import com.jaga.util.appium.AppiumUtil;
import io.appium.java_client.ios.IOSDriver;

public class IOSAction extends AppiumUtil {

    IOSDriver driver;

    public IOSAction(IOSDriver driver) {
        this.driver = driver;
    }


    public void waitFor(int time) {
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
