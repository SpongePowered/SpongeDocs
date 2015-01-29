#!/usr/bin/env bash

sphinx-build -b html source build/html 2> errors
if [[ -n $(cat errors) ]]; then
  python ./etc/reporter.py fail
  exit 1
else
  python ./etc/reporter.py pass
fi

if [[ $TRAVIS_PULL_REQUEST = false && $TRAVIS_BRANCH = master ]]; then

  LANGUAGES=$(curl --user $TX_USER:$TX_PASS http://transifex.com/api/2/project/sponge-docs/languages/ | jq -r '.[] | .language_code | "-l " + .' | xargs echo)

  echo languages: $LANGUAGES

  echo | tx init --user=$TX_USER --pass=$TX_PASS

  sphinx-build -b gettext source build/locale
  sphinx-intl update -p build/locale $LANGUAGES -d locale/ > /dev/null

  sphinx-intl update-txconfig-resources -p build/locale --transifex-project-name sponge-docs -d locale/

  tx push -s

fi

exit 0