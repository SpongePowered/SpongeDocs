# Sponge Documentation ![Build Status](https://github.com/spongepowered/spongedocs/actions/workflows/build.yml/badge.svg)

This is the repository where the Sponge documentation is held.


The latest version may be viewed in full by visiting [Sponge Docs](https://docs.spongepowered.org/)

## Contributing

Instructions and Guidelines on how to contribute can be found on [the Docs themselves](https://docs.spongepowered.org/stable/en/contributing/spongedocs.html).

## Licensing

The Sponge Documentation is licensed under the [Creative Commons - Share-Alike license](LICENSE.txt).

You can read more about our licensing situation on the [documentation itself](https://docs.spongepowered.org/stable/en/about/license.html).

## Translations

We're eagerly looking for translators! Please visit the [Crowdin translation page](https://translate.spongepowered.org), sign up, join the team, and start translating.

## Local Environment

You can setup an environment so you can instantly see the changes that have been made to the docs.

### Prerequisites

Install [`mise`](https://mise.jdx.dev/getting-started.html). It manages the pinned Python and Node versions this project needs (see `mise.toml`) and orchestrates the build tasks.

### Run the live-reload dev server

From the directory containing this README:

	mise run dev

On first run, `mise` installs the pinned Python and Node versions, creates a local `venv/`, installs Python and npm dependencies, and then launches the gulp-based dev server. Your browser should open to reveal the docs. When you make a change to the documentation, the docs will refresh in the browser (possibly after a few seconds).

### Other tasks

	mise run build    # one-shot strict build (warnings-as-errors)
	mise run install  # re-run just the dependency install step
	mise run clean    # remove build output
