#!/usr/bin/env bash
locale=$(echo $1 | sed s/_/-/)
crowdin_code=$(cat langs.json | jq  -r --arg lo "$locale" 'map(select(.locale == $lo)) | .[:1] | .[] | .crowdin_code')
echo "-D language=$1 source dist/$BRANCHNAME/$crowdin_code"
