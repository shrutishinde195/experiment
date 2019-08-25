package com.qa.util;

import com.qa.Constants.Constants;
import com.qa.pages.BasePage;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;

import java.util.List;

public class ElementActions extends BasePage {

    public ElementActions(WebDriver driver) {
        super(driver);
    }
    public Select select;
    public WebDriverWait wait;

    /**
     * This method is used to create the webelement on the basis of given By
     * locator
     * @param locator
     * @return webelement
     */
    public WebElement getElement(By locator) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_EXPLICIT_WAIT_TIMEOUT);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            element = driver.findElement(locator);
        } catch (Exception e) {
            System.out.println("element could not be created");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return element;
    }

    /**
     * this method is used to wait fot the element to be present
     *
     * @param locator
     */
    public void waitForElementPresent(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * @param title
     */
    public void waitForTitlePresent(String title) {
        WebDriverWait wait = new WebDriverWait(driver,Constants.DEFAULT_EXPLICIT_WAIT_TIMEOUT);
        wait.until(ExpectedConditions.titleContains(title));
    }

    /**
     * This method is used to check element is displayed or not
     * @param locator
     * @return
     */
    public boolean elementIsDisplayed(By locator) {
        waitForElementPresent(locator);
        return getElement(locator).isDisplayed();
    }

    /**
     * this method is used to click on an element
     * @param locator
     */
    public void elementClick(By locator) {
        try {
            getElement(locator).click();
        } catch (Exception e) {
            System.out.println("exception occured with locator: " + locator);
        }
    }

    /**
     * this method is used to pass the values
     * @param locator
     * @param value
     */
    public void elementSendKeys(By locator, String value) {
        try {
            getElement(locator).sendKeys(value);
        } catch(Exception e){
            System.out.println("exception occured with locator: " + locator);
        }
    }

    /**
     * @return
     */
    public String getPageTitle() {
        String title = null;
        try {
            title = driver.getTitle();
        } catch (Exception e) {
            System.out.println("some exception occurred while getting the title " + title);
        }
        return title;
    }

    public void switchToFrame() {
        driver.switchTo().frame("");
    }

    public By getCSS(String Selector) {
        return By.cssSelector(Selector);
    }

    public By getXPATH(String Selector) {
        return By.xpath(Selector);
    }


    public By getId(String Selector) {
        return By.id(Selector);
    }

    public By getLinkText(String Selector) {
        return By.linkText(Selector);
    }

    public void navigateToURL(String URL) {
        System.out.println("Navigating to: " + URL);
        System.out.println("Thread id = " + Thread.currentThread().getId());

        try {
            driver.navigate().to(URL);
        } catch (Exception e) {
            System.out.println("URL did not load: " + URL);
            throw new TestException("URL did not load");
        }
    }

    public String getElementText(By selector){
        waitForElementToBeVisible(selector);
        try{
            return StringUtils.trim(driver.findElement(selector).getText());
        }catch (Exception e){
            System.out.println(String.format("Element %s does not exist - proceeding", selector));
        }
        return null;
    }

    public void sendKeys(By selector, String value) {
        WebElement element = getElement(selector);
        clearField(element);
        try {
            element.sendKeys(value);
        } catch (Exception e) {
            throw new TestException(String.format("Error in sending [%s] to the following element: [%s]", value, selector.toString()));
        }
    }

    public void clearField(WebElement element) {
        try {
            element.clear();
        } catch (Exception e) {
            System.out.print(String.format("The following element could not be cleared: [%s]", element.getText()));
        }
    }

    public void click(By selector) {
        WebElement element = getElement(selector);
        waitForElementToBeClickable(selector);
        try {
            element.click();
        } catch (Exception e) {
            throw new TestException(String.format("The following element is not clickable: [%s]", selector));
        }
    }

    public void waitForElementToBeClickable(By selector ) {
        try {
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(selector));
        } catch (Exception e) {
            throw new TestException("The following element is not clickable: " + selector);
        }
    }

    public void waitForElementToBeVisible(By selector) {
        try {
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(selector));
        } catch (Exception e) {
            throw new NoSuchElementException(String.format("The following element was not visible within [%s] seconds: %s ", "10".toString(), selector));
        }
    }

    public void waitForElementToDisappear(By selector) {
        System.out.println("Waiting for element to disappear: " + selector);
        try {
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(selector));
        } catch (Exception e) {
            System.out.println("Exception caught in waitForElementToDisappear:\n" + e.toString());
            throw new TestException(String.format("Element did not disappear: [%s]", selector.toString()));
        }
    }

    public void selectOption(By selector, Object value) {
        WebElement element = getElement(selector);
        List<WebElement> options = element.findElements(getTagName("option"));
        select = new Select(element);
        try {
            for (WebElement option : options) {
                if (option.isDisplayed()) {
                    if (value instanceof String) {
                        select.selectByValue((String) value);
                        break;
                    } else if (value instanceof Integer) {
                        select.selectByIndex((Integer) value);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public By getTagName(String Selector) {
        return By.tagName(Selector);
    }
}
