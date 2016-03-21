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

# strip the branchname and make it a variable
if [[ $TRAVIS_BRANCH =~ release\/[0-9]*\.[0-9]*\.[0-9]* ]]; then

  export BRANCHNAME=`sed 's/release\///' <<<$TRAVIS_BRANCH`

else

  export BRANCHNAME=$TRAVIS_BRANCH

fi

echo "$TRAVIS_BRANCH"
echo "$BRANCHNAME"

# If we're on master or a release/* branch, deploy

if [[ $TRAVIS_PULL_REQUEST = false && $TRAVIS_BRANCH = master ]]; then

    # Add the api key to the crowdin configuration (because it is stupid)
    echo -e \\napi_key: ${CROWDIN_API_KEY} >> ./crowdin.yaml

    # Deploy docs
    ./etc/docs.sh

  elif [[ $TRAVIS_PULL_REQUEST = false && $BRANCHNAME =~ [0-9]*\.[0-9]*\.[0-9]* ]]; then

    # Add the api key to the crowdin configuration (because it is stupid)
    echo -e \\napi_key: ${CROWDIN_API_KEY} >> ./crowdin.yaml

    # Deploy docs
    ./etc/docs.sh

fi

# If we're on a pull request, build the PR
if ! [[ $TRAVIS_PULL_REQUEST = false ]]; then

    ./etc/pr.sh

fi

exit 0
