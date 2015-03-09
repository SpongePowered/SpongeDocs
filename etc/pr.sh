#!/usr/bin/env bash

branch=${TRAVIS_PULL_REQUEST}
echo "Deploying PR #$branch"
branch_exists=$(git show-ref refs/heads/${branch})

# Deploy
cd build/html
git config user.name "Spongy"
git config user.email "spongy@gratimax.net"
git init
git remote add origin https://spongy:${GH_TOKEN}@github.com/Spongy/SpongeDocs-PRs >/dev/null
if [[ -n "$branch_exists" ]]; then
    git fetch origin $branch
    git checkout $branch
else
    git checkout --orphan $branch
fi
git add .
git commit -q -m "Deploy $(date)" &> /dev/null
git push -q -f origin $branch &> /dev/null

cd ../../
python ./etc/comment.py
