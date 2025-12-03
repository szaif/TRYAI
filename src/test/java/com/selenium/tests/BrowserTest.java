package com.selenium.tests;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.selenium.base.BaseTest;
import com.selenium.utils.SeleniumUtils;

/**
 * Test class demonstrating basic browser operations
 */
public class BrowserTest extends BaseTest {

    @Before
    public void setUp() {
        initializeDriver("chrome");
    }

    @After
    public void tearDownTest() {
        tearDown();
    }

    /**
     * Test: Launch browser and navigate to Google
     */
    @Test
    public void testLaunchBrowserAndNavigate() {
        System.out.println("Starting test: Launch browser and navigate to Google");

        // Navigate to URL
        SeleniumUtils.navigateToURL(driver, "https://www.google.com");

        // Get page information
        String title = SeleniumUtils.getPageTitle(driver);
        String url = SeleniumUtils.getCurrentURL(driver);

        // Verify
        assertEquals("Page title should be 'Google'", "Google", title);
        assertTrue("URL should be google.com", url.contains("google"));

        System.out.println("✓ Test Passed!");
        System.out.println("  - Page Title: " + title);
        System.out.println("  - Current URL: " + url);
    }

    /**
     * Test: Browser capabilities
     */
    @Test
    public void testBrowserCapabilities() {
        SeleniumUtils.navigateToURL(driver, "https://www.google.com");

        String title = driver.getTitle();
        String url = driver.getCurrentUrl();

        System.out.println("✓ Browser Capabilities:");
        System.out.println("  - Title: " + title);
        System.out.println("  - URL: " + url);
        System.out.println("  - Window Size: " + driver.manage().window().getSize());

        assertNotNull("Title should not be null", title);
        assertNotNull("URL should not be null", url);
    }
}
