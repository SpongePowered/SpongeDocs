=================================
Manipulating a Configuration File
=================================

Configuration files allow your plugins to store data, as well as allow server administrators to easily take control over specific portions of your plugin, if you so choose to let them. Sponge uses Configurate to allow you to easily manipulate your configuration files. These pages will explain how to utilize Configurate in order to use configuration files to your full advantage.

.. tip::

    See the official `Configurate wiki <https://github.com/zml2008/configurate/wiki>`_ to gain more in-depth information about working with its components.

.. note::

    Sponge makes use of the HOCON configuration format. The rest of this guide will assume you are as well. As working with the different formats is made relatively similar by the system of Configurate, this should not pose too much of an issue if you so choose to use an alternate format.

.. toctree::
    :maxdepth: 2
    :titlesonly:

    loaders
    nodes
    paths
    values
