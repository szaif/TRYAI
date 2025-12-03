@echo off
REM ============================================================
REM Selenium MCP Test Runner - Login and Add to Cart Test
REM ============================================================

cls
echo.
echo ============================================================
echo Selenium Test Automation - Rahul Shetty Academy Login Test
echo ============================================================
echo.
echo This test will:
echo   1. Launch Chrome browser
echo   2. Navigate to https://rahulshettyacademy.com/loginpagePractise/
echo   3. Sign in with credentials (rahulshettyacademy / learning)
echo   4. Find and add iPhone X product to cart
echo   5. Navigate to checkout
echo   6. Verify iPhone X is in cart
echo   7. Display all step-by-step console outputs
echo.

REM Change to project directory
cd /d "C:\Users\Aryen\IdeaProjects\TryAI"

echo Compiling project...
call mvn clean compile test-compile
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo.
echo ============================================================
echo Running Test...
echo ============================================================
echo.

REM Run the test
call mvn test -Dtest=LoginAndCartTest

echo.
echo ============================================================
echo Test Execution Complete
echo ============================================================
echo.
pause
