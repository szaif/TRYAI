# GitHub MCP Server Setup Guide

## Overview
This guide helps you set up and run the GitHub MCP Server to push your Selenium tests to GitHub.

## Prerequisites
- Node.js installed
- GitHub account
- GitHub Personal Access Token (PAT) or SSH key configured

## Step 1: Create GitHub Repository

1. Go to [GitHub.com](https://github.com)
2. Click "New" to create a new repository
3. Name it (e.g., `selenium-tests`)
4. Choose public or private
5. Copy the repository URL

## Step 2: Configure Git Credentials

### Option A: GitHub CLI (Recommended)
```bash
gh auth login
```

### Option B: Personal Access Token (HTTPS)
1. Go to Settings → Developer settings → Personal access tokens
2. Click "Generate new token"
3. Select scopes: `repo`, `write:packages`
4. Copy the token

### Option C: SSH Key
```bash
ssh-keygen -t ed25519 -C "your-email@example.com"
cat ~/.ssh/id_ed25519.pub
```
Add the key to your GitHub SSH keys.

## Step 3: Run the Server

### Option 1: Using Batch File (Windows)
```bash
run_github_mcp.bat "https://github.com/your-username/selenium-tests.git"
```

### Option 2: Using Node.js Directly
```bash
node github-mcp-server.js "https://github.com/your-username/selenium-tests.git"
```

### Option 3: Set Environment Variable
```powershell
$env:GITHUB_REPO_URL="https://github.com/your-username/selenium-tests.git"
node github-mcp-server.js
```

## Step 4: Verify Push

Go to your GitHub repository URL and verify the code is there.

## Features

✅ **Automated Test Execution** - Runs all Selenium tests before pushing
✅ **Git Repository Initialization** - Auto-initializes if needed
✅ **File Management** - Adds and commits all project files
✅ **GitHub Integration** - Pushes to your remote repository
✅ **Status Reports** - Shows repository status and remote URL
✅ **Error Handling** - Clear error messages and guidance

## Troubleshooting

### "Git is not recognized"
- Install Git from https://git-scm.com/
- Or use GitHub Desktop (includes Git)

### "Permission denied (publickey)"
- Configure SSH keys: `ssh-keygen -t ed25519`
- Add public key to GitHub SSH keys

### "Authentication failed"
- Check Personal Access Token expiration
- Use `git credential-osxkeychain` (macOS) or `git credential-manager` (Windows)

### "Remote repository not found"
- Verify the GitHub repository URL is correct
- Ensure repository exists and is accessible

## Configuration Files

### .github-mcp-config.json
Main configuration file with settings like:
- Auto commit and sync
- Default branch
- Excluded patterns

### claude_desktop_config.json
Claude desktop configuration for MCP servers

## Server Endpoints

The server provides these operations:
- `initialize()` - Initialize Git repository
- `pushToGitHub(repoUrl, branch)` - Push to GitHub
- `getStatus()` - Get repository status
- `getRemoteUrl()` - Get remote URL

## Next Steps

1. Set up your GitHub credentials
2. Create a new repository on GitHub
3. Run the server with your repository URL
4. Monitor the test execution and push process
5. Verify the code on GitHub

## Support

For issues or questions, check:
- GitHub Documentation: https://docs.github.com/
- MCP Documentation: https://modelcontextprotocol.io/
- Selenium Documentation: https://www.selenium.dev/documentation/

