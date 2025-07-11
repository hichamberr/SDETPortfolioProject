package com.hicham.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    public static void waitForVisibility(WebDriver driver, WebElement element, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForClickable(WebDriver driver, WebElement element, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForTitleContains(WebDriver driver, String titlePartial, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.titleContains(titlePartial));
    }

}
