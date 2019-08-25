package com.qa.testCases;

import com.qa.Constants.Constants;
import com.qa.pages.BasePage;
import com.qa.pages.Page;
import com.qa.util.WebEventListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public WebDriver driver;
    public Page page;
    public Properties prop;
    public static EventFiringWebDriver e_driver;
    public static WebEventListener eventListener;

    @BeforeTest
    public void beforeTest(){
        System.out.println("in before test");
    }

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("in before suite");
    }


    @AfterMethod
    public void tearDown(){
        if(!(driver==null)){
            driver.quit();
        }
    }

    /**
     * This method is used to initialize the properties and it will return
     * properties reference
     *
     * @return prop
     */
    public Properties initialize_Properties() {

        prop = new Properties();
        try {
            FileInputStream ip = new FileInputStream("./src/main/java/com/qa/hubspot/configuration/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * This method is used to initialize the webdriver
     * @return driver
     */
    @BeforeMethod
    @Parameters(value={"browser"})
    public void setUpTest(String browser) {
        String browserName = prop.getProperty("browser");
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            if (prop.getProperty("headless").equals("yes")) {
                ChromeOptions co = new ChromeOptions();
                co.addArguments("--headless");
                driver = new ChromeDriver(co);
            } else {
                driver = new ChromeDriver();
            }
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            if (prop.getProperty("headless").equals("yes")) {
                FirefoxBinary fb = new FirefoxBinary();
                fb.addCommandLineOptions("--headless");
                FirefoxOptions fo = new FirefoxOptions();
                fo.setBinary(fb);
                driver = new FirefoxDriver(fo);
            } else {
                driver = new FirefoxDriver();
            }
        } else {
            System.out.println("Browser" + browserName
                    + "is not defined in properties file, please give the correct browser name");
        }
        e_driver = new EventFiringWebDriver(driver);
        // Now create object of EventListerHandler to register it with EventFiringWebDriver
        eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        /*OR
        if (prop.getProperty("fullscreenmode").equals("yes")) {
            getDriver().manage().window().fullscreen();
        }*/
        driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(prop.getProperty("url"));
        page= new BasePage(driver);
    }
}
