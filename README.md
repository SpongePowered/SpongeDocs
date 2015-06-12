# Sponge Documentation

[![Build Status](https://travis-ci.org/SpongePowered/SpongeDocs.svg?branch=master)](https://travis-ci.org/SpongePowered/SpongeDocs)

This is the repository where the Sponge documentation is held.


The latest version may be viewed in full by visiting [Sponge at Read-the-Docs](https://docs.spongepowered.org/)

## Translations

We're eagerly looking for translators! Please visit the [CrowdIn translation page](https://translate.spongepowered.org), sign up, join the team, and start translating.

## Local Environment

You can setup an environment so you can instantly see the changes that make to the docs.

1. [Install Python 2.7 and Sphinx](http://sphinx-doc.org/latest/install.html)
2. [Install pip](https://pip.pypa.io/en/latest/installing.html)
3. [Install node.js](http://nodejs.org/download/)

In terminal or the command line, within the directory containing this README, run the following commands:

	npm install -g gulp gulp-webserver
	npm install
	pip install -r etc/requirements.txt
	gulp

Your browser should open to reveal the docs. When you make a change to the documentation, the docs should refresh in the browser (possibly after a few seconds).
