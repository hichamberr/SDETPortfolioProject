package com.hicham.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

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
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // 🔄 Add this line for global wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Create a new test log in the report
        test = extent.createTest(method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Log test result
        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "✅ Test Passed");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "❌ Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "⚠️ Test Skipped: " + result.getThrowable());
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }
}
