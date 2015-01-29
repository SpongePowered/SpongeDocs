#!/usr/bin/env bash
./etc/build.sh
if [[ $TRAVIS_PULL_REQUEST = false && $TRAVIS_BRANCH = master ]]; then
  ./etc/update.sh
fi