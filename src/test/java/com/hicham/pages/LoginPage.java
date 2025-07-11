package com.hicham.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "Signup / Login")
    private WebElement loginLink;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[normalize-space()='Login']")
    private WebElement loginButton;

    public void goToLoginPage() {
        loginLink.click();
    }

    public void enterEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

}
