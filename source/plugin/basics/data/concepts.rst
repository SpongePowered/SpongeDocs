========
Concepts
========

The unified Data API aims to provide an easily extensible way to deal with any forms of data across the Sponge API. It is based on the ideas that

* Everything that contains data is a ``DataHolder``
* Data is accessed not directly via the data holder, but through a ``DataManipulator``
* Every data holder knows which data manipulators it can accept
* A data manipulater only ever contains a copy of life data
* In addition to changeable data, data holders can have immutable properties


