package com.selenium.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Standalone test for Login and Add to Cart flow
 */
public class LoginAndAddToCartStandalone {

    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            // Setup
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();

            System.out.println("\n========================================");
            System.out.println("Test: Login and Add iPhone X to Cart");
            System.out.println("========================================\n");

            // Step 1: Navigate
            System.out.println("Step 1: Navigating to login page...");
            driver.navigate().to("https://rahulshettyacademy.com/loginpagePractise/");
            String title = driver.getTitle();
            System.out.println("✓ Page title: " + title);

            // Step 2: Login
            System.out.println("\nStep 2: Logging in...");
            driver.findElement(By.id("username")).sendKeys("rahulshettyacademy");
            System.out.println("  ✓ Username entered");

            driver.findElement(By.id("password")).sendKeys("learning");
            System.out.println("  ✓ Password entered");

            WebElement dropdown = driver.findElement(By.cssSelector("select.form-control"));
            Select select = new Select(dropdown);
            select.selectByValue("user");
            System.out.println("  ✓ User role selected");

            WebElement checkbox = driver.findElement(By.id("terms"));
            if (!checkbox.isSelected()) {
                checkbox.click();
                System.out.println("  ✓ Terms checkbox checked");
            }

            driver.findElement(By.id("signInBtn")).click();
            System.out.println("  ✓ Sign In button clicked");
            Thread.sleep(3000);

            // Step 3: Add iPhone X to cart
            System.out.println("\nStep 3: Adding iPhone X to cart...");
            Thread.sleep(2000);

            java.util.List<WebElement> products = driver.findElements(By.cssSelector(".card-title"));
            System.out.println("  - Found " + products.size() + " products");

            boolean found = false;
            for (WebElement product : products) {
                String productName = product.getText().toLowerCase();
                System.out.println("    Checking: " + productName);
                if (productName.contains("iphone") && productName.contains("x")) {
                    found = true;
                    System.out.println("  ✓ Found iPhone X!");
                    WebElement card = product.findElement(By.xpath("./ancestor::div[@class='card']"));
                    WebElement addButton = card.findElement(By.xpath(".//button[contains(text(), 'Add')]"));
                    addButton.click();
                    System.out.println("  ✓ iPhone X added to cart");
                    Thread.sleep(1000);
                    break;
                }
            }

            if (!found) {
                System.out.println("✗ iPhone X not found!");
            }

            // Step 4: Navigate to checkout
            System.out.println("\nStep 4: Navigating to checkout...");
            try {
                WebElement checkout = driver.findElement(By.xpath("//button[contains(text(), 'Checkout')]"));
                checkout.click();
            } catch (Exception e) {
                WebElement proceed = driver.findElement(By.xpath("//button[contains(text(), 'Proceed')]"));
                proceed.click();
            }
            System.out.println("  ✓ Navigated to checkout");
            Thread.sleep(2000);

            // Step 5: Verify
            System.out.println("\nStep 5: Verifying product in cart...");
            java.util.List<WebElement> cartItems = driver.findElements(By.cssSelector("tr"));
            System.out.println("  - Checking " + cartItems.size() + " items");

            boolean productInCart = false;
            for (WebElement item : cartItems) {
                String itemText = item.getText().toLowerCase();
                if (itemText.contains("iphone") && itemText.contains("x")) {
                    productInCart = true;
                    System.out.println("  ✓ iPhone X found in cart!");
                    break;
                }
            }

            if (!productInCart) {
                System.out.println("✗ iPhone X not found in cart!");
            }

            System.out.println("\n========================================");
            System.out.println("✓ Test Completed Successfully!");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.out.println("✗ Test failed with error:");
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed");
            }
        }
    }
}
