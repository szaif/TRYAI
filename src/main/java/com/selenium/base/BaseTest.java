package com.selenium.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Base class for all test classes - handles WebDriver initialization
 */
public class BaseTest {
    protected WebDriver driver;
    private static final int IMPLICIT_WAIT = 10;

    /**
     * Initialize WebDriver with Chrome browser (default)
     */
    public void initializeDriver() {
        initializeDriver("chrome");
    }

    /**
     * Initialize WebDriver with specified browser
     * 
     * @param browser Browser type: chrome, firefox, edge
     */
    public void initializeDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));

        // Maximize browser window
        driver.manage().window().maximize();
    }

    /**
     * Close the WebDriver
     */
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
