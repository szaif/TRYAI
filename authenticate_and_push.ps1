#!/usr/bin/env pwsh

param(
    [Parameter(Mandatory=$true)]
    [string]$RepoUrl
)

$gitCmd = 'C:\Program Files\Git\cmd\git.exe'
$projectPath = Get-Location

Write-Host ""
Write-Host "===================================================" -ForegroundColor Cyan
Write-Host "  GitHub Authentication & Push Setup" -ForegroundColor Cyan
Write-Host "===================================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "To push to GitHub, you need to authenticate." -ForegroundColor Yellow
Write-Host ""
Write-Host "Choose an authentication method:" -ForegroundColor Cyan
Write-Host "1. GitHub CLI (gh auth login)" -ForegroundColor Gray
Write-Host "2. Git Credential Manager (already installed)" -ForegroundColor Gray
Write-Host "3. Personal Access Token" -ForegroundColor Gray
Write-Host ""

Write-Host "For most users, just run 'gh auth login' if GitHub CLI is installed." -ForegroundColor Yellow
Write-Host ""

Write-Host "Attempting push to: $RepoUrl" -ForegroundColor Cyan
Write-Host ""

# Try to push - this will prompt for authentication if needed
try {
    & $gitCmd push -u origin main
    Write-Host ""
    Write-Host "✅ Successfully pushed to GitHub!" -ForegroundColor Green
    Write-Host "Repository URL: $RepoUrl" -ForegroundColor Green
}
catch {
    Write-Host "❌ Push failed" -ForegroundColor Red
    Write-Host "Error: $_" -ForegroundColor Red
    Write-Host ""
    Write-Host "To set up authentication:" -ForegroundColor Yellow
    Write-Host "1. Visit: https://github.com/settings/tokens" -ForegroundColor Gray
    Write-Host "2. Generate a new token with 'repo' scope" -ForegroundColor Gray
    Write-Host "3. Use the token as password when prompted" -ForegroundColor Gray
}

Write-Host ""
