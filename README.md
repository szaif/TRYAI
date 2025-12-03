# MCP Selenium Java Project

A Selenium WebDriver project with Java integration for browser automation using the MCP (Model Context Protocol) framework.

## Project Structure

```
TryAI/
├── pom.xml                              # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/com/selenium/
│   │   │   ├── base/
│   │   │   │   └── BaseTest.java       # Base class for all tests
│   │   │   └── utils/
│   │   │       └── SeleniumUtils.java  # Utility methods
│   │   └── resources/
│   │       └── log4j.properties         # Logging configuration
│   └── test/
│       └── java/com/selenium/tests/
│           ├── GoogleTest.java          # Google search tests
│           └── BrowserTest.java         # Browser automation tests
├── logs/                                # Test execution logs
└── README.md                            # This file
```

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Chrome/Firefox/Edge browser installed

## Setup

1. **Install Java and Maven**
   ```bash
   # Check Java version
   java -version
   
   # Check Maven version
   mvn -v
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run all tests**
   ```bash
   mvn test
   ```

4. **Run specific test class**
   ```bash
   mvn test -Dtest=GoogleTest
   mvn test -Dtest=BrowserTest
   ```

## Test Classes

### GoogleTest
- `testNavigateToGoogle()` - Navigate to Google and verify page title
- `testGoogleSearchBoxVisible()` - Verify Google search box is visible
- `testPerformGoogleSearch()` - Perform a Google search
- `testGoogleURL()` - Verify Google page URL

### BrowserTest
- `testLaunchBrowserAndNavigate()` - Launch browser and navigate to Google
- `testBrowserCapabilities()` - Test browser capabilities

## Key Features

✓ **BaseTest Class** - Handles WebDriver initialization and cleanup
✓ **SeleniumUtils** - Common operations (navigate, click, enter text, etc.)
✓ **WebDriverManager** - Automatic driver management (no manual driver downloads)
✓ **Logging** - Log4j integration for test execution logging
✓ **JUnit & TestNG** - Support for both testing frameworks
✓ **Implicit Waits** - 10-second implicit wait configured globally

## Supported Browsers

- Chrome (default)
- Firefox
- Edge

## Example Usage

```java
// Initialize driver
initializeDriver("chrome");

// Navigate to URL
SeleniumUtils.navigateToURL(driver, "https://www.google.com");

// Find and interact with elements
SeleniumUtils.enterText(driver, By.name("q"), "Selenium");
SeleniumUtils.clickElement(driver, By.name("btnK"));

// Clean up
tearDown();
```

## MCP Integration

This project is designed to work with MCP Selenium server. Configure it in your `claude_desktop_config.json`:

```json
{
  "mcpServers": {
    "selenium": {
      "command": "npx",
      "args": ["-y", "@angiejones/mcp-selenium"]
    }
  }
}
```

## Logging

Logs are written to:
- Console (INFO level and above)
- `logs/selenium.log` (DEBUG level for Selenium package)

Configure logging in `src/main/resources/log4j.properties`

## Dependencies

- Selenium WebDriver 4.38.0
- WebDriverManager 5.9.1
- JUnit 4.13.2
- TestNG 7.10.2
- Log4j 1.2.17

## Troubleshooting

**Error: "Chrome driver not found"**
- WebDriverManager should handle this automatically
- Ensure Chrome browser is installed

**Error: "Element not found"**
- Increase wait time in SeleniumUtils
- Verify locator strategy is correct

**Error: "Maven build fails"**
- Run `mvn clean` to clear cache
- Check Java version compatibility

## License

MIT License

## Author

Selenium Java MCP Project
