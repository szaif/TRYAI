package com.selenium.tests;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import com.selenium.base.BaseTest;
import com.selenium.utils.SeleniumUtils;

/**
 * Test class for Google website automation
 */
public class GoogleTest extends BaseTest {

    @Before
    public void setUp() {
        initializeDriver("chrome");
    }

    @After
    public void tearDownTest() {
        tearDown();
    }

    /**
     * Test 1: Navigate to Google and verify page title
     */
    @Test
    public void testNavigateToGoogle() {
        SeleniumUtils.navigateToURL(driver, "https://www.google.com");
        String title = SeleniumUtils.getPageTitle(driver);
        assertEquals("Page title should be 'Google'", "Google", title);
        System.out.println("✓ Test Passed: Successfully navigated to Google");
    }

    /**
     * Test 2: Verify Google search box is visible
     */
    @Test
    public void testGoogleSearchBoxVisible() {
        SeleniumUtils.navigateToURL(driver, "https://www.google.com");

        // Wait for search box to be visible
        try {
            SeleniumUtils.waitForElement(driver, By.name("q"));
            System.out.println("✓ Test Passed: Google search box is visible");
        } catch (Exception e) {
            fail("Search box not found");
        }
    }

    /**
     * Test 3: Perform a Google search
     */
    @Test
    public void testPerformGoogleSearch() {
        SeleniumUtils.navigateToURL(driver, "https://www.google.com");

        // Find and enter search query
        SeleniumUtils.enterText(driver, By.name("q"), "Selenium WebDriver");

        // Find search button and click
        SeleniumUtils.clickElement(driver, By.name("btnK"));

        // Wait for results page to load
        try {
            Thread.sleep(3000); // Wait for page to load
            String currentUrl = SeleniumUtils.getCurrentURL(driver);
            assertTrue("URL should contain 'search'", currentUrl.contains("search"));
            System.out.println("✓ Test Passed: Google search performed successfully");
        } catch (InterruptedException e) {
            fail("Interrupted while waiting for results");
        }
    }

    /**
     * Test 4: Verify Google page URL
     */
    @Test
    public void testGoogleURL() {
        SeleniumUtils.navigateToURL(driver, "https://www.google.com");
        String currentUrl = SeleniumUtils.getCurrentURL(driver);
        assertTrue("URL should be Google", currentUrl.contains("google"));
        System.out.println("✓ Test Passed: Google URL verified - " + currentUrl);
    }
}
