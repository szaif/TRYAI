@echo off
REM GitHub MCP Server Runner for Selenium Tests
REM This script runs the Selenium tests and pushes to GitHub

echo.
echo ===================================================
echo   GitHub MCP Server - Selenium Test Suite
echo ===================================================
echo.

REM Check if Node.js is installed
where node >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Node.js is not installed!
    echo Please install Node.js from https://nodejs.org/
    exit /b 1
)

REM Run tests first
echo [1/2] Running Selenium Tests...
echo.
call mvn test
echo.

if %ERRORLEVEL% NEQ 0 (
    echo WARNING: Tests completed with errors
) else (
    echo Tests completed successfully!
)

REM Start GitHub MCP Server
echo.
echo [2/2] Starting GitHub MCP Server...
echo.

if "%1"=="" (
    echo Usage: run_github_mcp.bat "https://github.com/user/repo.git"
    echo.
    echo Running in demo mode...
    node github-mcp-server.js
) else (
    node github-mcp-server.js "%1"
)

echo.
echo ===================================================
echo   Process Complete
echo ===================================================
echo.
pause
