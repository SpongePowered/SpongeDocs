#!/usr/bin/env bash

# Attempt to build the docs
# creates english docs in /build/html and doctree in /build/doctree/
sphinx-build -b html -d build/doctrees source build/html 2> errors

# Fail if there are errors
if [[ -n $(cat errors) ]]; then
    python ./etc/reporter.py fail

    # If we're on a pull request, build the PR
    if ! [[ $TRAVIS_PULL_REQUEST = false ]]; then

        ./etc/pr.sh

    fi

    exit 1
else
    python ./etc/reporter.py pass
fi

# If we're on a pull request, build the PR
if ! [[ $TRAVIS_PULL_REQUEST = false ]]; then

    ./etc/pr.sh

fi

exit 0
