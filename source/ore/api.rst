===========
Ore Web API
===========

Ore offers a JSON RESTful API to interact with mods and plugins indexed by Ore. The following is an ongoing
specification of that API.

Data Types
~~~~~~~~~~

There are a few integer-based enum-style data types that are used throughout the API. The following is a list of
these current types.

Category IDs:
`````````````

+----+---------------------+
| 0  | Administrator Tools |
+----+---------------------+
| 1  | Chat Tools          |
+----+---------------------+
| 2  | Developer Tools     |
+----+---------------------+
| 3  | Economy             |
+----+---------------------+
| 4  | Gameplay            |
+----+---------------------+
| 5  | Games               |
+----+---------------------+
| 6  | Protection          |
+----+---------------------+
| 7  | Role Playing        |
+----+---------------------+
| 8  | World Management    |
+----+---------------------+
| 9  | Miscellaneous       |
+----+---------------------+
| 10 | Undefined           |
+----+---------------------+

Sorting Method IDs:
```````````````````

+---+------------------+
| 0 | Most stars       |
+---+------------------+
| 1 | Most downloads   |
+---+------------------+
| 2 | Most views       |
+---+------------------+
| 3 | Newest           |
+---+------------------+
| 4 | Recently updated |
+---+------------------+

Routes
~~~~~~

Below is a list of the following routes that are currently available within the API.

.. toctree::
    :maxdepth: 2
    :titlesonly:

    routes/list-projects
    routes/project
    routes/list-versions
    routes/project-version
    routes/list-users
    routes/user
    routes/download
