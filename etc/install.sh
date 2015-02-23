#!/usr/bin/env bash

# Install crowdin
gem install crowdin-cli

# Install pip requirements
pip install -r etc/requirements.txt

# Install spongedocs-theme dependencies
cd spongedocs-theme/

# install node modules
npm install

# install bower components
npm install bower
./node_modules/.bin/bower install
