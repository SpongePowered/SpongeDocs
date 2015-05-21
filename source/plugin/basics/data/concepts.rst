========
Concepts
========

The unified Data API aims to provide an easily extensible way to deal with any forms of data across the Sponge API. It is based on the ideas that

* Everything that contains mutable data is a data holder
* Data is accessed not directly via the data holder, but through a data manipulator
* Every data holder knows which data manipulators it can accept
* A data manipulator only ever contains a copy of life data
* In addition to changeable data, data holders can have immutable properties

This basically means that no knowledge about what data a particular data holder contains is needed as it provide methods to check if a data manipulator is compatible.


