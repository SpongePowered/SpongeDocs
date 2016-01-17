=========================
Packaging Default Configs
=========================

Example: Loading a default config from the plugin jar file
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import java.net.URL;

    URL jarConfigFile = this.getClass().getResource("defaultConfig.conf");
    ConfigurationLoader<CommentedConfigurationNode> loader =
      HoconConfigurationLoader.builder().setURL(jarConfigFile).build();

For this example it is important to note that the ``getResource(...)`` method works relative to the location of the
class it is called on. So if in the above example the class lies in the package ``me.username.myplugin``, the
``defaultConfig.conf`` file must not lie in the jar file root, but instead in the directory ``me/username/myplugin``.
This is usually accomplished by placing the ``defaultConfig.conf`` in a resources file in your project, be careful when
creating the package, as it sometimes creates a folder **named** ``me.username.myplugin`` instead of a series of nested
folders like a package is supposed to be.

.. warning::

    Since all Sponge plugins share a single namespace, all resources available to the ``getResource()`` method are
    visible to all other plugins as well. Therefore, resources placed in the root of a jar may overwrite (or be
    overwritten by) identically named resources in another jar. Placing those resources in unique folder structures
    similar to your java packages will mitigate the danger of accidentally having a resource file overwritten by
    another plugin.

.. tip::

    If you are having troubles getting your resource while in an IntelliJ dev environment, and you are not using 
    ForgeGradle the resource files in your plugin may not be available on the classpath unless you enable the following
    options either in your build.gradle or your IntelliJ project structure.
.. code-block:: groovy

        idea { module { inheritOutputDirs = true } }

.. figure:: /images/InheritProjectCompileOutputPath.png
    :alt: a screenshot of IntelliJ Project Structure options.

    ``File`` > ``Project Structure`` > ``Modules`` > ``ExamplePlugin`` > ``Paths`` > ``Inherit Project Compile Output Path``

