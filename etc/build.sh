#!/usr/bin/env bash
red=`tput setaf 1`
bold=`tput bold`
reset=`tput sgr0`
sphinx-build -b html source build/html 2> warnings
if [[ -n $(cat warnings) ]]; then
  echo ${red}${bold}
  echo "WARNINGS (These are causing your build to fail):"
  echo "================================================"
  cat warnings
  exit 1
fi