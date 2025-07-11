package com.hicham.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),'Logged in as')]")
    private WebElement loggedInUserText;

    public boolean isUserLoggedIn() {
        return loggedInUserText.isDisplayed();
    }

    public String getLoggedInText() {
        return loggedInUserText.getText();
    }
}
