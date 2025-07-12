package com.hicham.utilities;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    @BeforeSuite
    public void setUpReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/extent-report.html");
        spark.config().setReportName("SDET Portfolio Report");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeMethod
    public void setUp(Method method) {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu"); // Extra for headless stability
        options.addArguments("--remote-allow-origins=*"); // Fix for ChromeDriver security

        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        test = extent.createTest(method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "✅ Test Passed");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "❌ Test Failed: " + result.getThrowable());

            // Capture screenshot on failure
            String screenshotPath = captureScreenshot(result.getName());
            if (screenshotPath != null) {
                test.addScreenCaptureFromPath(screenshotPath);
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "⚠️ Test Skipped: " + result.getThrowable());
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    // Utility to capture screenshot
    private String captureScreenshot(String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destPath = "test-output/screenshots/" + testName + "_" + timestamp + ".png";

            File destFile = new File(destPath);
            destFile.getParentFile().mkdirs();
            Files.copy(source.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return destPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
