package com.restassured.util;
import com.restassured.service.ExtentReportService;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Listener implements ITestListener{
    private static final String timestamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
    private final ExtentReportService extentReportService = ExtentReportService.getInstance();

    @Override
    public void onStart(ITestContext iTestContext) {
        extentReportService.initializeExtentReporter(timestamp);
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        extentReportService.startTest(iTestResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        extentReportService.updateExtentReportOnTestSuccess(iTestResult);
        extentReportService.endTest();
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        extentReportService.updateExtentReportOnTestFailure(iTestResult);
        extentReportService.endTest();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        extentReportService.updateExtentReportOnTestSkipped(iTestResult);
        extentReportService.endTest();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        extentReportService.flushExtentReport();
    }
}
