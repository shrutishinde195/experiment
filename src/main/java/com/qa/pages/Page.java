package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {

    public WebDriver driver;
    WebDriverWait wait;

    public Page (WebDriver driver){
        this.driver=driver;
        this.wait= new WebDriverWait(this.driver,15);
    }

    //abstract methodes:
    public abstract String getpageTitle();

    public abstract String getPageHeader(By locator);

    public abstract WebElement getElement(By locator);

    public abstract void waitForElementPresent(By locator);

    public abstract void waitForPageTitle(String title);

    public <TPage extends BasePage> TPage getInstance(Class<TPage> pageClass){
        try{
            return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(this.driver);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
