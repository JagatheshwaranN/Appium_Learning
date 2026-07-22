package com.jaga.util.appium;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class FileReader {

    // Properties object to store key-value pairs from a properties file
    private static final Properties properties = new Properties();

    public static void loadPropertyFile() {
        try (FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//global-data.properties")) {
            properties.load(fileInputStream);
            System.out.println("The configuration file is loaded!!");
        } catch (FileNotFoundException ex) {
            System.out.println("The configuration file not found on the given path");
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            System.out.println("Error occurred while loading the configuration file"+ ex);
            throw new RuntimeException("Error occurred while loading configuration file", ex);
        }
    }

    public static String getDataFromPropFile(String key) {
        String data = null;
        if (key != null) {
            data = properties.getProperty(key);
            if (data != null) {
                data = data.strip();
                System.out.println("The data fetched from the configuration file" + data);
            } else {
                System.out.println("The key is not present in the configuration file" + key);
                throw new RuntimeException("Key Not Found");
            }
        }
        return data;
    }

}


