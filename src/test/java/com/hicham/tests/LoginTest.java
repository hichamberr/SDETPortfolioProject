package com.hicham.tests;

import com.hicham.pages.HomePage;
import com.hicham.pages.LoginPage;
import com.hicham.utilities.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testUserCanLogin() {
        driver.get("https://automationexercise.com");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.goToLoginPage();
        loginPage.enterEmail("hichamberr480@gmail.com");
        loginPage.enterPassword("tecpef-xavjum-2xeJho");
        loginPage.clickLogin();

        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isUserLoggedIn(), "❌ Login failed!");
        test.pass("✅ Login successful! Logged in as: " + homePage.getLoggedInText());
    }

}
