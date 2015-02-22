#!/usr/bin/env bash

# Obtain the list of crowdin supported languages
curl https://api.crowdin.com/api/supported-languages?json > langs.json

# Build sources for crowdin
sphinx-build -b gettext source build/locale
sphinx-intl update -p build/locale -l en -d locale-src 1>/dev/null

# Download translations
crowdin-cli download

# Build translated .po files
sphinx-intl build -d locale/ 1>/dev/null

# Build the english source
sphinx-build -b html source dist/en

# We need the platform for xargs args
platform='unknown'
unamestr=$(uname)

if [[ "$unamestr" == 'FreeBSD' || "$unamestr" == 'Darwin'  ]]; then
    # Build each language
    find locale -d 1 |
    sed 's/locale\///' |
    xargs -L 1 ./etc/sphinx_opts.sh |
    xargs -P 4 -I % sh -c "sphinx-build -b html %"

    # Build the homepage
    python ./etc/home.py $(find locale -d 1 |
    sed 's/locale\///' |
    tr '\n' ',' |
    sed 's/,$//g')
else
    # Build each language
    find locale -maxdepth 1 -mindepth 1 |
    sed 's/locale\///' |
    xargs -L 1 ./etc/sphinx_opts.sh |
    xargs --max-procs=4 -I % sh -c "sphinx-build -b html %"

    # Build the homepage
    python ./etc/home.py $(find locale -maxdepth 1 -mindepth 1 |
    sed 's/locale\///' |
    tr '\n' ',' |
    sed 's/,$//g')
fi

# No jekyll!!!
touch dist/.nojekyll

# Add a cname
echo "docs.spongepowered.org" > dist/CNAME

# Deploy
cd dist
git config --global user.name "Spongy"
git config --global user.email "spongy@gratimax.net"
git init
git remote add origin https://spongy:${GH_TOKEN}@github.com/Spongy/SpongeDocs >/dev/null
git checkout -b gh-pages
git add .
git commit -m "Deploy $(date)" >/dev/null
git push -f origin gh-pages >/dev/null
