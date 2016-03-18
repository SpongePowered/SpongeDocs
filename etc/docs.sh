#!/usr/bin/env bash

# Obtain the list of crowdin supported languages
# creates langs.json in root directory
curl https://api.crowdin.com/api/supported-languages?json > langs.json

# Build sources for crowdin
# creates /build/locale/ with *.pot files in it
sphinx-build -b gettext source build/locale

# creates /locale-src/en/LC_messages/ with *.po files in it
sphinx-intl update -p build/locale -l en -d locale-src 1>/dev/null

# upload sources to the corresponding Crowdin branch
# if master, then upload to root; else upload to $branchname

if [[ $BRANCHNAME = master ]]; then
     crowdin-cli upload sources
     crowdin-cli download

else
     crowdin-cli upload sources -b $BRANCHNAME
     crowdin-cli download -b $BRANCHNAME
fi

# *.po -> *.mo in  /locale/lang_code/
sphinx-intl build -d locale/ 1>/dev/null

# we need to know which releases are deployed before building the landing page and topbar

# clone old deployed docs
mkdir -p deploy
cd deploy
git config --global user.name "Spongy"
git config --global user.email "spongy@gratimax.net"
git init
# add origin as remote
git remote add origin https://spongy:${GH_TOKEN}@github.com/Spongy/SpongeDocs >/dev/null
git checkout --orphan gh-pages
git pull origin gh-pages
cd ..

# We need the platform for xargs args
platform='unknown'
unamestr=$(uname)

if [[ "$unamestr" == 'FreeBSD' || "$unamestr" == 'Darwin'  ]]; then

    # create topbar and modifiy theme before building the docs
    python ./etc/menubar.py $(find locale -d 1 |
    sed 's/locale\///' |
    tr '\n' ',' |
    sed 's/,$//g')

    # Build the english source
    sphinx-build -b html source dist/$BRANCHNAME/en

    # Build each language
    find locale -d 1 |
    sed 's/locale\///' |
    xargs -L 1 ./etc/sphinx_opts.sh |
    xargs -P 4 -I % sh -c "sphinx-build -b html %"

    # Build the homepage if we're on master branch
    if [[ $TRAVIS_BRANCH = master ]]; then

    python ./etc/home.py $(find locale -d 1 |
    sed 's/locale\///' |
    tr '\n' ',' |
    sed 's/,$//g')

    fi

else

    # create topbar and modifiy theme before building the docs
    python ./etc/menubar.py $(find locale -maxdepth 1 -mindepth 1 |
    sed 's/locale\///' |
    tr '\n' ',' |
    sed 's/,$//g')

    # Build the english source
    sphinx-build -b html source dist/$BRANCHNAME/en

    # Build each language
    find locale -maxdepth 1 -mindepth 1 |
    sed 's/locale\///' |
    xargs -L 1 ./etc/sphinx_opts.sh |
    xargs --max-procs=4 -I % sh -c "sphinx-build -b html %"

    if [[ $TRAVIS_BRANCH = master ]]; then

    # Build the homepage if we're on master branch
    python ./etc/home.py $(find locale -maxdepth 1 -mindepth 1 |
    sed 's/locale\///' |
    tr '\n' ',' |
    sed 's/,$//g')

    fi

fi

# Copy static files
cp -R ./etc/static/. dist/$BRANCHNAME/

# Copy static files to ~/ if we're building master
if [[ $TRAVIS_BRANCH = master ]]; then

cp -R ./etc/static/. dist/
cp -R ./dist/$TRAVIS_BRANCH/en/_static/. dist/_static/

fi

cd deploy

# remove the old build
rm -rf $BRANCHNAME

# copy new build to /deploy/, commit and deploy.
cd ..
cp -R ./dist/. deploy/
cd deploy
git add .
git commit -q -m "Deploy $(date)" &> /dev/null
git push -q -f origin gh-pages &> /dev/null
