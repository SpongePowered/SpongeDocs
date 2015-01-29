#!/usr/bin/env bash
red="\e[1;31m"
green="\e[1;32m"
bold=""
reset="\e[0m"
sphinx-build -b html source build/html 2> warnings
if [[ -n $(cat warnings) ]]; then
  echo -e ${red}
  echo "WARNINGS (These are causing your build to fail):"
  echo "================================================"
  cat warnings
  echo "END WARNINGS"
  echo "============"
  echo -e ${reset}
  exit 1
else
  echo -e ${green}
  echo "NO WARNINGS (Good job!)"
  echo "======================="
  echo -e ${reset}
  exit 0
fi