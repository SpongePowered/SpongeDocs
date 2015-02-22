#!/usr/bin/env bash

# Attempt to build the docs
sphinx-build -b html -d build/doctrees source build/html 2> errors

# Fail if there are errors
if [[ -n $(cat errors) ]]; then
    python ./etc/reporter.py fail
    exit 1
else
    python ./etc/reporter.py pass
fi

# If we're on the master branch, do deploys
if [[ $TRAVIS_PULL_REQUEST = false && $TRAVIS_BRANCH = master ]]; then

    # Add the api key to the crowdin configuration (because it is stupid)
    echo -e \\napi_key: ${CROWDIN_API_KEY} >> ./crowdin.yaml

    # Build the *.pot files
    sphinx-build -b gettext source build/locale

    # Make them #.po files (because crowdin is stupid)
    sphinx-intl update -p build/locale -l en -d locale-src

    # Upload the sources
    crowdin-cli upload sources

    # Deploy docs
    ./etc/docs.sh

fi

exit 0
