package com.selenium.utils;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Utility class for common Selenium operations
 */
public class SeleniumUtils {
    private static final Logger logger = Logger.getLogger(SeleniumUtils.class);
    private static final int WAIT_TIME = 10;

    /**
     * Navigate to a URL
     */
    public static void navigateToURL(WebDriver driver, String url) {
        try {
            driver.navigate().to(url);
            logger.info("Navigated to URL: " + url);
        } catch (Exception e) {
            logger.error("Failed to navigate to URL: " + url, e);
            throw e;
        }
    }

    /**
     * Wait for element to be visible
     */
    public static WebElement waitForElement(WebDriver driver, org.openqa.selenium.By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("Element found and visible: " + locator);
            return element;
        } catch (Exception e) {
            logger.error("Timeout waiting for element: " + locator, e);
            throw e;
        }
    }

    /**
     * Click on an element
     */
    public static void clickElement(WebDriver driver, org.openqa.selenium.By locator) {
        try {
            WebElement element = waitForElement(driver, locator);
            element.click();
            logger.info("Clicked on element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to click element: " + locator, e);
            throw e;
        }
    }

    /**
     * Enter text into an input field
     */
    public static void enterText(WebDriver driver, org.openqa.selenium.By locator, String text) {
        try {
            WebElement element = waitForElement(driver, locator);
            element.clear();
            element.sendKeys(text);
            logger.info("Entered text '" + text + "' into element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to enter text: " + text, e);
            throw e;
        }
    }

    /**
     * Get text from an element
     */
    public static String getText(WebDriver driver, org.openqa.selenium.By locator) {
        try {
            WebElement element = waitForElement(driver, locator);
            String text = element.getText();
            logger.info("Retrieved text: " + text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + locator, e);
            throw e;
        }
    }

    /**
     * Get page title
     */
    public static String getPageTitle(WebDriver driver) {
        String title = driver.getTitle();
        logger.info("Page title: " + title);
        return title;
    }

    /**
     * Get current URL
     */
    public static String getCurrentURL(WebDriver driver) {
        String url = driver.getCurrentUrl();
        logger.info("Current URL: " + url);
        return url;
    }
}
