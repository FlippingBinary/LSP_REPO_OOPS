#!/usr/bin/env bash
set -euo pipefail

# Set the name of the branch that will receive the files
TARGET_BRANCH="main"
# Get the commit hash
DEV_SHA=$(git rev-parse --short dev)
# Create a safe temporary directory
TEMP_DIR=$(mktemp -d)

echo "=== Syncing $DEV_SHA from dev ==="

# Store Java files in temp location
find src/org/howard/edu/lsp -name "*.java" -exec cp --parents {} "$TEMP_DIR" \;

# Try to checkout existing orphan branch, or create new one
if git ls-remote --heads origin $TARGET_BRANCH | grep -q $TARGET_BRANCH; then
  git checkout $TARGET_BRANCH
else
  git checkout --orphan $TARGET_BRANCH
  git rm -rf .
fi

# Copy Java files back
cp -r "$TEMP_DIR"/. .

# Configure git user (same as git-auto-commit-action)
git config user.name "$COMMIT_USER_NAME"
git config user.email "$COMMIT_USER_EMAIL"

# Commit with author info (same as git-auto-commit-action)
git add -A
git diff --cached --quiet || git commit --author="$COMMIT_AUTHOR" -m "Sync from dev: $DEV_SHA"
git push origin $TARGET_BRANCH

# Clean up
rm -rf "$TEMP_DIR"

echo "=== Sync complete ==="
