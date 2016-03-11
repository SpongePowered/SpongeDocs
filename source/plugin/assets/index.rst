=============
The Asset API
=============

The ``AssetManager`` allows developers to retrieve resources from a plugin JAR.

By default, a plugin's assets resides in a directory named ``assets/com/example/myplugin`` where
``com.example.myplugin`` is the plugin ID. In most cases this default directory should be used but if you *need* to
change it, you may do so by setting the ``assets`` attribute in your ``@Plugin`` annotation or ``mcmod.info`` file.

Once properly configured you can retrieve a resource for your (or any) plugin using the following code:

.. code-block:: java

    import org.spongepowered.api.asset.Asset;

    Asset asset = plugin.getAsset("myfile.txt");

Alternatively, you can retrieve assets through the ``AssetManager`` class:

.. code-block:: java

    import org.spongepowered.api.Sponge;

    Asset asset = Sponge.getAssetManager().getAsset(plugin, "myfile.txt");

.. tip::

    The ``AssetManager`` can be used to retrieve assets defined in the Sponge implementation itself simply by omitting
    the plugin parameter.

Working with Assets
===================

The ``Asset`` class is essentially just a wrapper around a ``URL`` with some common I/O operations built in. The use
cases of ``Assets``\s is essentially unbounded but one common use case is to generate a default configuration file if
your plugin's configuration file is not found. You can achieve this using a `PluginContainer` with the following code:

.. code-block:: java

    import java.nio.file.Files;

    if (Files.notExists(configPath)) {
        plugin.getAsset("default.conf").copyToFile(configPath);
    }

.. note::

    Developers coming from Bukkit or some other Java background might be familiar with the ``getResource`` and
    ``getResourceAsStream`` methods in ``Class``\es and ``ClassLoader``\s. **These methods should generally be avoided**
    within the SpongeAPI environment in favor of the ``AssetManager`` in order to provide a more confluent way of
    retrieving resources not only within your own plugin, but for other plugins as well.