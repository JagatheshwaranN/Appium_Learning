package com.jaga.test.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class TestReport {

    public static ExtentReports extentReports;

    public static ExtentReports getReport() {
        String path = System.getProperty("user.dir") + "//report//extent-report.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("General Store - Android Test");
        reporter.config().setDocumentTitle("Mobile Test Automation Report");
        extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("QE", "Jaga");
        return extentReports;
    }

}
