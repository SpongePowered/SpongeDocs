#!/usr/bin/env bash

sphinx-build -b html source build/html 2> errors
if [[ -n $(cat errors) ]]; then
  python ./etc/reporter.py fail
  exit 1
else
  python ./etc/reporter.py pass
fi

if [[ $TRAVIS_PULL_REQUEST = false && $TRAVIS_BRANCH = master ]]; then

  echo -e \\napi_key: ${CROWDIN_API_KEY} >> ./crowdin.yaml

  sphinx-build -b gettext source build/locale
  sphinx-intl update -p build/locale -l en -d locale-src

  crowdin-cli upload sources

fi

exit 0