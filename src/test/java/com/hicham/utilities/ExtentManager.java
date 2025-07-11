package com.hicham.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getReportInstance() {
        if (extent == null) {
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/extent-report.html");
            htmlReporter.config().setReportName("SDET Portfolio Report");
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }
        return extent;
    }
}
