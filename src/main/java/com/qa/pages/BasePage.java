package com.qa.pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;

public class BasePage extends Page {
    public BasePage(WebDriver driver) {
        super(driver);
    }

    public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();

    public String getpageTitle() {
        String title = null;
        try {
            title = driver.getTitle();
        } catch (Exception e) {
            System.out.println("Some exception occured while getting the title");
        }
        return title;
    }
    public String getPageHeader(By locator) {

        return getElement(locator).getText();
    }

    public WebElement getElement(By locator) {
        waitForElementPresent(locator);
        WebElement element= null;
        try{
            element= driver.findElement(locator);
            return element;
        }
        catch(Exception e){
            System.out.println("Error occurred while creating element"+locator.toString());
            e.printStackTrace();
        }
        return element;
    }

    public void waitForElementPresent(By locator) {
        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        }catch(Exception e){
            System.out.println("Error occurred while waiting for element"+locator.toString());
        }
    }

    public void waitForPageTitle(String title) {
        try{
            wait.until(ExpectedConditions.titleContains(title));
        }catch(Exception e){
            System.out.println("Error occurred while waiting for element"+title);
        }
    }

    /**
     * take screenshot
     */
    public String getScreenshot() {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
        File destination = new File(path);
        try {
            FileUtils.copyFile(src, destination);
        } catch (IOException e) {
            System.out.println("Capture Failed " + e.getMessage());
        }
        return path;
    }
}
