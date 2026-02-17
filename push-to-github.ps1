# GitHub Push Script
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  iMoney Banking App - GitHub Push" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if browser opened
Write-Host "STEP 1: Create GitHub Repository" -ForegroundColor Yellow
Write-Host "A browser window should have opened to https://github.com/new" -ForegroundColor White
Write-Host ""
Write-Host "In GitHub:" -ForegroundColor Green
Write-Host "  1. Repository name: iMoney-Banking-App" -ForegroundColor White
Write-Host "  2. Description: Personal finance management system demonstrating OOP principles in Java" -ForegroundColor White
Write-Host "  3. Choose Public or Private" -ForegroundColor White
Write-Host "  4. DO NOT check: Initialize with README, .gitignore, or license" -ForegroundColor Red
Write-Host "  5. Click 'Create repository'" -ForegroundColor White
Write-Host ""
Read-Host "Press Enter once you've created the repository"

Write-Host ""
Write-Host "STEP 2: Enter Your GitHub Details" -ForegroundColor Yellow
$username = Read-Host "Enter your GitHub username"

if ([string]::IsNullOrWhiteSpace($username)) {
    Write-Host "Error: Username cannot be empty!" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""
Write-Host "STEP 3: Connecting to GitHub..." -ForegroundColor Yellow
$repoUrl = "https://github.com/$username/iMoney-Banking-App.git"

try {
    # Add remote
    Write-Host "Adding remote repository..." -ForegroundColor White
    git remote add origin $repoUrl 2>$null
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Remote already exists, updating..." -ForegroundColor Yellow
        git remote set-url origin $repoUrl
    }
    
    # Rename branch to main
    Write-Host "Renaming branch to main..." -ForegroundColor White
    git branch -M main
    
    # Push to GitHub
    Write-Host ""
    Write-Host "STEP 4: Pushing to GitHub..." -ForegroundColor Yellow
    Write-Host "You may be prompted for credentials:" -ForegroundColor Cyan
    Write-Host "  Username: $username" -ForegroundColor White
    Write-Host "  Password: Use a Personal Access Token (NOT your GitHub password!)" -ForegroundColor Red
    Write-Host "  Get token at: https://github.com/settings/tokens" -ForegroundColor White
    Write-Host ""
    
    git push -u origin main
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Green
        Write-Host "SUCCESS! Repository pushed to GitHub!" -ForegroundColor Green
        Write-Host "========================================" -ForegroundColor Green
        Write-Host ""
        Write-Host "View your repository at:" -ForegroundColor Cyan
        Write-Host "https://github.com/$username/iMoney-Banking-App" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "Next steps:" -ForegroundColor White
        Write-Host "  1. Add repository topics: java, oop, banking-application, finance" -ForegroundColor White
        Write-Host "  2. Star your own repo!" -ForegroundColor White
        Write-Host "  3. Share the link!" -ForegroundColor White
    } else {
        Write-Host ""
        Write-Host "Push failed. Common issues:" -ForegroundColor Red
        Write-Host "  1. Wrong username" -ForegroundColor White
        Write-Host "  2. Need Personal Access Token (not password)" -ForegroundColor White
        Write-Host "  3. Repository wasn't created on GitHub yet" -ForegroundColor White
        Write-Host ""
        Write-Host "Create token at: https://github.com/settings/tokens" -ForegroundColor Yellow
        Write-Host "Then run this script again." -ForegroundColor White
    }
    
} catch {
    Write-Host ""
    Write-Host "Error: $_" -ForegroundColor Red
}

Write-Host ""
Read-Host "Press Enter to exit"
