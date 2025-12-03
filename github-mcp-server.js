#!/usr/bin/env node

/**
 * GitHub MCP Server
 * Handles pushing Selenium test project to GitHub
 */

const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

// Use full path to git on Windows
const gitCmd = process.platform === 'win32' 
  ? 'C:\\Program Files\\Git\\cmd\\git.exe'
  : 'git';

class GitHubMCPServer {
  constructor() {
    this.projectPath = process.cwd();
    this.config = this.loadConfig();
  }

  loadConfig() {
    const configPath = path.join(this.projectPath, '.github-mcp-config.json');
    if (fs.existsSync(configPath)) {
      return JSON.parse(fs.readFileSync(configPath, 'utf8'));
    }
    return {};
  }

  async initialize() {
    console.log('🚀 GitHub MCP Server Starting...');
    console.log(`📁 Project Path: ${this.projectPath}`);
    
    // Check if git is initialized
    if (!fs.existsSync(path.join(this.projectPath, '.git'))) {
      console.log('📦 Initializing Git repository...');
      try {
        execSync(`"${gitCmd}" init`, { cwd: this.projectPath, stdio: 'inherit' });
        execSync(`"${gitCmd}" config user.email "aryen@github.com"`, { cwd: this.projectPath });
        execSync(`"${gitCmd}" config user.name "Aryen"`, { cwd: this.projectPath });
        console.log('✅ Git repository initialized');
      } catch (error) {
        console.error('❌ Failed to initialize git:', error.message);
        return false;
      }
    }

    // Add all files
    console.log('📝 Adding files to Git...');
    try {
      execSync(`"${gitCmd}" add .`, { cwd: this.projectPath, stdio: 'inherit' });
      console.log('✅ Files added');
    } catch (error) {
      console.error('❌ Failed to add files:', error.message);
    }

    // Commit
    console.log('💾 Creating commit...');
    try {
      const commitMsg = `Selenium Test Suite - ${new Date().toISOString()}`;
      execSync(`"${gitCmd}" commit -m "${commitMsg}"`, { cwd: this.projectPath, stdio: 'inherit' });
      console.log('✅ Commit created');
    } catch (error) {
      console.log('ℹ️ No changes to commit or commit failed:', error.message);
    }

    return true;
  }

  async pushToGitHub(repoUrl, branch = 'main') {
    console.log(`\n🚀 Pushing to GitHub: ${repoUrl}`);
    
    try {
      // Check if remote exists
      try {
        execSync(`"${gitCmd}" remote get-url origin`, { cwd: this.projectPath, stdio: 'pipe' });
      } catch (e) {
        // Remote doesn't exist, add it
        execSync(`"${gitCmd}" remote add origin ${repoUrl}`, { cwd: this.projectPath, stdio: 'inherit' });
        console.log('✅ Remote added');
      }

      // Check current branch and rename if needed
      try {
        const currentBranch = execSync(`"${gitCmd}" rev-parse --abbrev-ref HEAD`, {
          cwd: this.projectPath,
          encoding: 'utf8'
        }).trim();
        
        if (currentBranch !== branch) {
          console.log(`📝 Renaming branch from '${currentBranch}' to '${branch}'...`);
          execSync(`"${gitCmd}" branch -M ${branch}`, { cwd: this.projectPath, stdio: 'inherit' });
          console.log(`✅ Branch renamed to '${branch}'`);
        }
      } catch (e) {
        console.log('ℹ️ Could not check branch name');
      }

      // Push to remote
      execSync(`"${gitCmd}" push -u origin ${branch}`, { cwd: this.projectPath, stdio: 'inherit' });
      console.log(`✅ Successfully pushed to ${repoUrl}/${branch}`);
      return true;
    } catch (error) {
      console.error('❌ Failed to push to GitHub:', error.message);
      console.log('📌 Make sure you have GitHub credentials configured');
      console.log('📌 Or use: git push -u origin main (with manual authentication)');
      return false;
    }
  }

  getStatus() {
    try {
      const status = execSync(`"${gitCmd}" status --porcelain`, { 
        cwd: this.projectPath,
        encoding: 'utf8'
      });
      return status || 'Repository is clean';
    } catch (error) {
      return 'Not a git repository';
    }
  }

  getRemoteUrl() {
    try {
      const url = execSync(`"${gitCmd}" remote get-url origin`, {
        cwd: this.projectPath,
        encoding: 'utf8'
      });
      return url.trim();
    } catch (error) {
      return 'No remote configured';
    }
  }
}

// Main execution
async function main() {
  const server = new GitHubMCPServer();
  
  // Initialize
  await server.initialize();
  
  console.log('\n📊 Repository Status:');
  console.log(`Status: ${server.getStatus()}`);
  console.log(`Remote: ${server.getRemoteUrl()}`);

  // Get GitHub repo URL from environment or command line
  const repoUrl = process.env.GITHUB_REPO_URL || process.argv[2];
  
  if (repoUrl) {
    console.log('\n🔗 GitHub Repository URL provided');
    await server.pushToGitHub(repoUrl);
  } else {
    console.log('\n⚠️ No GitHub repository URL provided');
    console.log('Usage: node github-mcp-server.js <repo-url>');
    console.log('Or set environment variable: GITHUB_REPO_URL=<repo-url>');
    console.log('\nExample: node github-mcp-server.js https://github.com/user/selenium-tests.git');
  }

  console.log('\n✨ GitHub MCP Server Ready');
}

main().catch(console.error);
