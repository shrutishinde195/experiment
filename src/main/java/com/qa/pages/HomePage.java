package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    //Page locators:
    private By emailid = By.className("private-page__title");

    public WebElement getLoginButton() {
        return getElement(loginButton);
    }

    public String getLoginPageTitle(){
        return getpageTitle();
    }

    public void getLoginPageHeader(){
        getPageHeader(header);
    }
}
