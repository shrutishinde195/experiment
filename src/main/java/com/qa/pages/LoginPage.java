package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    //Page locators:
    private By emailid = By.id("username");
    private By password =By.id("password");
    private By loginButton =By.id("loginBtn");
    private By header=By.xpath("//i18n-string[@data-key='login.signupLink.text']");

    public WebElement getHeader() {
        return getElement(header);
    }

    public WebElement getEmailid() {
        return getElement(emailid);

    }

    public WebElement getPassword() {
        return getElement(password);
    }

    public WebElement getLoginButton() {
        return getElement(loginButton);
    }

    public String getLoginPageTitle(){
        return getpageTitle();
    }

    public void getLoginPageHeader(){
        getPageHeader(header);
    }

    public HomePage doLogin(String username,String pwd){
        getEmailid().sendKeys(username);
        getPassword().sendKeys(pwd);
        getLoginButton().click();

        return getInstance(HomePage.class);
    }

    public HomePage doLogin(){
        getEmailid().sendKeys("");
        getPassword().sendKeys("");
        getLoginButton().click();

        return getInstance(HomePage.class);
    }
    public HomePage doLogin(String userCred){
        getEmailid().sendKeys(username);
        getPassword().sendKeys(pwd);
        getLoginButton().click();

        return getInstance(HomePage.class);
    }
}

