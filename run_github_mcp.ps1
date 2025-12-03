#!/usr/bin/env pwsh
# GitHub MCP Server Runner for Selenium Tests
# This script runs the Selenium tests and pushes to GitHub

param(
    [Parameter(Mandatory=$false)]
    [string]$GitHubRepoUrl,
    
    [Parameter(Mandatory=$false)]
    [switch]$SkipTests
)

function Write-Header {
    Write-Host ""
    Write-Host "===================================================" -ForegroundColor Cyan
    Write-Host "  GitHub MCP Server - Selenium Test Suite" -ForegroundColor Cyan
    Write-Host "===================================================" -ForegroundColor Cyan
    Write-Host ""
}

function Test-NodeJs {
    try {
        $version = node --version 2>$null
        Write-Host "✅ Node.js found: $version" -ForegroundColor Green
        return $true
    }
    catch {
        Write-Host "❌ Node.js is not installed!" -ForegroundColor Red
        Write-Host "📥 Please install from: https://nodejs.org/" -ForegroundColor Yellow
        return $false
    }
}

function Run-SeleniumTests {
    Write-Host "[1/2] Running Selenium Tests..." -ForegroundColor Cyan
    Write-Host ""
    
    try {
        mvn test
        if ($LASTEXITCODE -eq 0) {
            Write-Host ""
            Write-Host "✅ Tests completed successfully!" -ForegroundColor Green
            return $true
        }
        else {
            Write-Host ""
            Write-Host "⚠️  Tests completed with errors (code: $LASTEXITCODE)" -ForegroundColor Yellow
            return $true  # Continue anyway
        }
    }
    catch {
        Write-Host "❌ Error running tests: $_" -ForegroundColor Red
        return $false
    }
}

function Start-GitHubMcpServer {
    param([string]$RepoUrl)
    
    Write-Host ""
    Write-Host "[2/2] Starting GitHub MCP Server..." -ForegroundColor Cyan
    Write-Host ""
    
    if ($RepoUrl) {
        Write-Host "📍 Repository: $RepoUrl" -ForegroundColor Yellow
        & node github-mcp-server.js $RepoUrl
    }
    else {
        Write-Host "⚠️  No GitHub repository URL provided" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "Usage: .\run_github_mcp.ps1 -GitHubRepoUrl 'https://github.com/user/repo.git'" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "Example:" -ForegroundColor Cyan
        Write-Host "  .\run_github_mcp.ps1 -GitHubRepoUrl 'https://github.com/aryen/selenium-tests.git'" -ForegroundColor Gray
        Write-Host ""
        Write-Host "🔗 How to get your repository URL:" -ForegroundColor Yellow
        Write-Host "  1. Go to https://github.com/new" -ForegroundColor Gray
        Write-Host "  2. Create a new repository" -ForegroundColor Gray
        Write-Host "  3. Copy the HTTPS URL (shown after creation)" -ForegroundColor Gray
        Write-Host ""
        & node github-mcp-server.js
    }
}

# Main execution
Write-Header

if (-not (Test-NodeJs)) {
    exit 1
}

if ($SkipTests) {
    Write-Host "⏭️  Skipping test execution (--SkipTests flag)" -ForegroundColor Yellow
}
else {
    if (-not (Run-SeleniumTests)) {
        Write-Host "⚠️  Continuing despite test errors..." -ForegroundColor Yellow
    }
}

Start-GitHubMcpServer -RepoUrl $GitHubRepoUrl

Write-Host ""
Write-Host "===================================================" -ForegroundColor Cyan
Write-Host "  Process Complete" -ForegroundColor Cyan
Write-Host "===================================================" -ForegroundColor Cyan
Write-Host ""
