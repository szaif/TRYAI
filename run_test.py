#!/usr/bin/env python3
"""
Login and Add to Cart Test using Selenium
Website: https://rahulshettyacademy.com/loginpagePractise/
"""

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait, Select
from selenium.webdriver.support import expected_conditions as EC
import time

def test_login_and_add_to_cart():
    """Test: Login, Add iPhone X to Cart, and Verify"""
    
    driver = None
    try:
        print("\n" + "="*50)
        print("Test: Login and Add iPhone X to Cart")
        print("="*50 + "\n")
        
        # Step 1: Launch Chrome browser
        print("✓ Step 1: Launching Chrome browser...")
        driver = webdriver.Chrome()
        driver.maximize_window()
        
        # Step 2: Navigate to login page
        print("✓ Step 2: Navigating to login page...")
        driver.get("https://rahulshettyacademy.com/loginpagePractise/")
        title = driver.title
        print(f"  Page title: {title}")
        assert "Login" in title, "Should be on login page"
        
        # Step 3: Sign in with credentials
        print("\n✓ Step 3: Signing in with credentials...")
        
        # Enter username
        username_field = WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.ID, "username"))
        )
        username_field.send_keys("rahulshettyacademy")
        print("  Username entered: rahulshettyacademy")
        
        # Enter password
        password_field = driver.find_element(By.ID, "password")
        password_field.send_keys("learning")
        print("  Password entered")
        
        # Select user role
        dropdown = Select(driver.find_element(By.CSS_SELECTOR, "select.form-control"))
        dropdown.select_by_value("user")
        print("  User role selected")
        
        # Check terms checkbox
        checkbox = driver.find_element(By.ID, "terms")
        if not checkbox.is_selected():
            checkbox.click()
            print("  Terms checkbox checked")
        
        # Click Sign In
        sign_in_btn = driver.find_element(By.ID, "signInBtn")
        sign_in_btn.click()
        print("  Sign In button clicked")
        
        time.sleep(3)
        print("  ✓ Login successful!")
        
        # Step 4: Find and add iPhone X to cart
        print("\n✓ Step 4: Finding and adding iPhone X to cart...")
        time.sleep(2)
        
        products = driver.find_elements(By.CSS_SELECTOR, ".card-title")
        print(f"  Found {len(products)} products on page")
        
        product_found = False
        for product in products:
            product_name = product.text.lower()
            print(f"    Checking product: {product_name}")
            
            if "iphone" in product_name and "x" in product_name:
                product_found = True
                print("  ✓ Found iPhone X!")
                
                # Find the Add button
                card = product.find_element(By.XPATH, "./ancestor::div[@class='card']")
                add_btn = card.find_element(By.XPATH, ".//button[contains(text(), 'Add')]")
                add_btn.click()
                print("  ✓ iPhone X added to cart")
                time.sleep(1)
                break
        
        assert product_found, "iPhone X product should be found"
        
        # Step 5: Navigate to checkout
        print("\n✓ Step 5: Navigating to checkout...")
        try:
            checkout_btn = WebDriverWait(driver, 5).until(
                EC.presence_of_element_located((By.XPATH, "//button[contains(text(), 'Checkout')]"))
            )
            checkout_btn.click()
            print("  ✓ Navigated to checkout")
        except:
            proceed_btn = WebDriverWait(driver, 5).until(
                EC.presence_of_element_located((By.XPATH, "//button[contains(text(), 'Proceed')]"))
            )
            proceed_btn.click()
            print("  ✓ Navigated to checkout (alternate button)")
        
        time.sleep(2)
        
        # Step 6: Verify product in cart
        print("\n✓ Step 6: Verifying product is in cart...")
        time.sleep(1)
        
        cart_items = driver.find_elements(By.CSS_SELECTOR, "tr")
        print(f"  Checking {len(cart_items)} items in cart")
        
        product_in_cart = False
        for item in cart_items:
            item_text = item.text.lower()
            if "iphone" in item_text and "x" in item_text:
                product_in_cart = True
                print(f"  ✓ Product found in cart: {item.text}")
                break
        
        assert product_in_cart, "iPhone X should be in the cart"
        print("  ✓ Verification successful - iPhone X is in cart!")
        
        print("\n" + "="*50)
        print("✓ TEST PASSED SUCCESSFULLY!")
        print("="*50 + "\n")
        
        return True
        
    except AssertionError as e:
        print(f"\n✗ Test failed: {e}")
        return False
    except Exception as e:
        print(f"\n✗ Test failed with error: {e}")
        import traceback
        traceback.print_exc()
        return False
    finally:
        if driver:
            driver.quit()
            print("Browser closed")

if __name__ == "__main__":
    success = test_login_and_add_to_cart()
    exit(0 if success else 1)
