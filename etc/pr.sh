#!/usr/bin/env bash

branch=${TRAVIS_PULL_REQUEST}
echo "Deploying PR #$branch"

# Deploy
mkdir dist/
cd dist/
git init
git config user.name "Spongy"
git config user.email "spongy@gratimax.net"
git remote add origin https://spongy:${GH_TOKEN}@github.com/Spongy/SpongeDocs-PRs >/dev/null
if git ls-remote origin | grep -sw "$branch" &> /dev/null; then
    git fetch origin $branch
    git checkout $branch
else
    git checkout --orphan $branch
fi
cp -R ../build/html/. .
git add .
git commit -q -m "Deploy $(date)" &> /dev/null
git push -q -f origin $branch &> /dev/null

cd ../

# Ensure that the github API is updated by the time we make requests
sleep 5

python ./etc/comment.py
