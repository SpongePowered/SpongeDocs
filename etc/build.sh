#!/usr/bin/env bash
sphinx-build -b html source build/html 2> errors
if [[ -n $(cat errors) ]]; then
  python ./etc/reporter.py fail
  exit 1
else
  python ./etc/reporter.py pass
  exit 0
fi