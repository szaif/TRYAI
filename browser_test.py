from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time

# Launch browser
driver = webdriver.Chrome()

try:
    # Navigate to Google
    print("Navigating to https://www.google.com...")
    driver.get("https://www.google.com")
    
    # Wait for page to load
    WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.NAME, "q"))
    )
    
    print("✓ Browser launched successfully")
    print(f"✓ Page title: {driver.title}")
    print(f"✓ Current URL: {driver.current_url}")
    
    # Keep browser open for 5 seconds
    time.sleep(5)
    
except Exception as e:
    print(f"✗ Error: {e}")
finally:
    driver.quit()
    print("Browser closed")
