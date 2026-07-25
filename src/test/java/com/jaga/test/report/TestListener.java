package com.jaga.test.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.jaga.util.appium.AppiumUtil;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends AppiumUtil implements ITestListener {

    ExtentReports extentReports = TestReport.getReport();
    ExtentTest extentTest;
    AppiumDriver driver;

    public void onTestStart(ITestResult result) {
        extentTest = extentReports.createTest(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        extentTest.log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult result) {
        extentTest.fail(result.getThrowable());
        try {
            driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        extentTest.addScreenCaptureFromPath(takeScreenshot(driver, result.getMethod().getMethodName()), result.getMethod().getMethodName());
        /*
         * Base64 - CI / CD purpose
         *
         * extentTest.fail(result.getMethod().getMethodName(),
                MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshotAsBase64(driver)).build());
         */
    }

    public void onTestSkipped(ITestResult result) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onTestFailedWithTimeout(ITestResult result) {
        this.onTestFailure(result);
    }

    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
        extentReports.flush();
    }

}
