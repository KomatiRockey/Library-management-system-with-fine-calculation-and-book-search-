@echo off
echo ===== Setting execute permission for mvnw =====
git update-index --chmod=+x mvnw
if %errorlevel% neq 0 (
    echo ERROR: Make sure you are in your project folder and Git is installed.
    pause
    exit /b
)

echo ===== Committing the change =====
git commit -m "Make mvnw executable"
if %errorlevel% neq 0 (
    echo ERROR: Nothing to commit or Git error.
    pause
    exit /b
)

echo ===== Pushing to GitHub =====
git push origin main
if %errorlevel% neq 0 (
    echo ERROR: Push failed. Check your branch name or internet connection.
    pause
    exit /b
)

echo ===== Done! You can now redeploy on Render =====
pause
