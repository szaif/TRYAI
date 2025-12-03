package com.selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.selenium.base.BaseTest;
import com.selenium.utils.SeleniumUtils;

/**
 * Test class for Login, Add to Cart, and Checkout workflow
 * Website: https://rahulshettyacademy.com/loginpagePractise/
 */
public class LoginAndCartTest extends BaseTest {

    private static final String BASE_URL = "https://rahulshettyacademy.com/loginpagePractise/";
    private static final String USERNAME = "rahulshettyacademy";
    private static final String PASSWORD = "learning";

    @BeforeMethod
    public void setUp() {
        initializeDriver("chrome");
    }

    @AfterMethod
    public void tearDownTest() {
        tearDown();
    }

    /**
     * Test: Login, Add iPhone X to Cart, Checkout and Verify
     */
    @Test
    public void testLoginAddIPhoneToCartAndCheckout() throws InterruptedException {
        System.out.println("\n========================================");
        System.out.println("Starting test: Login and Add to Cart");
        System.out.println("========================================\n");

        // Step 1: Navigate to login page
        System.out.println("Step 1: Navigating to login page...");
        SeleniumUtils.navigateToURL(driver, BASE_URL);
        String pageTitle = SeleniumUtils.getPageTitle(driver);
        System.out.println("✓ Navigated to: " + pageTitle);
        Assert.assertTrue(pageTitle.contains("Login"), "Should be on login page");

        // Step 2: Login
        System.out.println("\nStep 2: Logging in...");
        loginToApplication(USERNAME, PASSWORD);
        Thread.sleep(2000);

        // Step 3: Search and Add iPhone X to cart
        System.out.println("\nStep 3: Adding iPhone X to cart...");
        addProductToCart("iphone X");
        Thread.sleep(2000);

        // Step 4: Navigate to checkout
        System.out.println("\nStep 4: Navigating to checkout...");
        navigateToCheckout();
        Thread.sleep(2000);

        // Step 5: Verify product in cart
        System.out.println("\nStep 5: Verifying product in cart...");
        verifyProductInCart("iPhone X");

        System.out.println("\n========================================");
        System.out.println("✓ Test Passed Successfully!");
        System.out.println("========================================\n");
    }

    /**
     * Helper method: Login to application
     */
    private void loginToApplication(String username, String password) throws InterruptedException {
        // Enter username
        By usernameLocator = By.id("username");
        SeleniumUtils.enterText(driver, usernameLocator, username);
        System.out.println("  ✓ Username entered: " + username);

        // Enter password
        By passwordLocator = By.id("password");
        SeleniumUtils.enterText(driver, passwordLocator, password);
        System.out.println("  ✓ Password entered");

        // Handle dropdown - select User role
        By dropdownLocator = By.cssSelector("select.form-control");
        WebElement dropdown = SeleniumUtils.waitForElement(driver, dropdownLocator);
        Select select = new Select(dropdown);
        select.selectByValue("user");
        System.out.println("  ✓ User role selected");

        // Check Remember me checkbox if not already checked
        By rememberCheckbox = By.id("terms");
        WebElement checkbox = driver.findElement(rememberCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
            System.out.println("  ✓ Terms checkbox checked");
        }

        // Click Sign In button
        By signInButton = By.id("signInBtn");
        SeleniumUtils.clickElement(driver, signInButton);
        System.out.println("  ✓ Sign In button clicked");

        Thread.sleep(3000);
        System.out.println("  ✓ Login successful!");
    }

    /**
     * Helper method: Add product to cart
     */
    private void addProductToCart(String productName) throws InterruptedException {
        // Wait for products to load
        Thread.sleep(2000);

        // Find all product cards
        java.util.List<WebElement> products = driver.findElements(By.cssSelector(".card-title"));
        System.out.println("  - Found " + products.size() + " products on page");

        boolean productFound = false;

        // Iterate through products to find iPhone X
        for (WebElement product : products) {
            String title = product.getText().toLowerCase();
            System.out.println("    Checking product: " + title);

            if (title.contains("iphone") && title.contains("x")) {
                productFound = true;
                System.out.println("  ✓ Found iPhone X!");

                // Find the parent card element
                WebElement card = product.findElement(By.xpath("./ancestor::div[@class='card']"));

                // Click Add button for this product
                WebElement addButton = card.findElement(By.xpath(".//button[contains(text(), 'Add')]"));
                addButton.click();
                System.out.println("  ✓ iPhone X added to cart");

                Thread.sleep(1000);
                break;
            }
        }

        Assert.assertTrue(productFound, "iPhone X product should be found");
    }

    /**
     * Helper method: Navigate to checkout
     */
    private void navigateToCheckout() throws InterruptedException {
        // Look for cart button or proceed to checkout
        By checkoutButton = By.xpath("//button[contains(text(), 'Checkout')]");

        // Try to find the button with different locators
        try {
            WebElement checkout = SeleniumUtils.waitForElement(driver, checkoutButton);
            checkout.click();
            System.out.println("  ✓ Navigated to checkout");
            Thread.sleep(2000);
        } catch (Exception e) {
            // Try alternate button text
            By proceedButton = By.xpath("//button[contains(text(), 'Proceed')]");
            WebElement proceed = SeleniumUtils.waitForElement(driver, proceedButton);
            proceed.click();
            System.out.println("  ✓ Navigated to checkout (alternate button)");
            Thread.sleep(2000);
        }
    }

    /**
     * Helper method: Verify product in cart
     */
    private void verifyProductInCart(String productName) throws InterruptedException {
        // Wait for cart table/list to load
        Thread.sleep(1000);

        // Find all items in cart
        java.util.List<WebElement> cartItems = driver.findElements(By.cssSelector("tr"));
        System.out.println("  - Checking " + cartItems.size() + " items in cart");

        boolean productFound = false;
        String foundProduct = "";

        for (WebElement item : cartItems) {
            String itemText = item.getText().toLowerCase();
            if (itemText.contains("iphone") && itemText.contains("x")) {
                productFound = true;
                foundProduct = item.getText();
                System.out.println("  ✓ Product found in cart: " + foundProduct);
                break;
            }
        }

        Assert.assertTrue(productFound, "iPhone X should be in the cart");
        System.out.println("  ✓ Verification successful - iPhone X is in cart!");
    }
}
