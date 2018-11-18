======================
Publishing Your Plugin
======================

Sponge's official plugin / mod repository, `Ore <https://ore.spongepowered.org>`_, is a free and open-source
project that anyone may publish their Sponge plugins or Forge mods on.

Packaging Your Plugin
~~~~~~~~~~~~~~~~~~~~~

Ore requires any projects to be packaged with a ``mcmod.info`` descriptor file in the top-level of your JAR file. This
file is used to automatically infer some important details about your project to make the upload process easier. Ore
will reject your plugin if this file is missing from the JAR. Luckily, SpongeAPI has a built-in annotation processor
that creates this file for you automatically, on compile, using the ``@Plugin`` annotation that you have likely
already created in your plugin's main class.

.. note::

    For more information on creating and compiling your first plugin, see :doc:`/plugin/plugin-class`

For reference, here is a sample ``mcmod.info`` file:

.. code-block:: json

    [
        {
            "modid": "my-plugin",
            "name": "MyPlugin",
            "version": "1.0.0",
            "description": "My first plugin!",
            "url": "https://spongepowered.org",
            "authorList": [
                "windy",
                "Zidane",
                "gabizou"
            ],
            "requiredMods": [
                "bookotd@1.0.0",
                "ore-test@1.0.0",
                "worldedit@1.0.0"
            ],
            "dependencies": [
                "bookotd@1.0.0",
                "ore-test@1.0.0",
                "worldedit@1.0.0"
            ]
        }
    ]

At the very least, each Ore project *must* have the ``modid``, ``name`` and, ``version`` fields completed.

Signing your Plugin
~~~~~~~~~~~~~~~~~~~

Due to security concerns, Ore requires each plugin file (ZIP or JAR) to be uploaded with a detached
`Pretty Good Privacy (PGP) <https://en.wikipedia.org/wiki/Pretty_Good_Privacy>`__ signature for a public key that is
associated with the email that you signed up to Sponge with. This digital signature ensures that any file uploaded by a
user was intended to be uploaded by the actual account holder.

.. note::

    A digital signature certifies and timestamps a document. If the document is subsequently modified in any way, a
    verification of the signature will fail. A digital signature can serve the same purpose as a hand-written signature
    with the additional benefit of being tamper-resistant. The GnuPG source distribution, for example, is signed so
    that users can verify that the source code has not been modified since it was packaged.

In order to sign your plugin, you must first download a PGP compliant software program, such as
`GnuPG (GPG) <https://www.gnupg.org/>`__. GPG is available for all major platforms and the binaries can be downloaded
`here <https://www.gnupg.org/download/index.html#sec-1-2>`__.

Once you have installed GPG and have generated your first public key, you will need to add the key to your account.
To do this, you can navigate to your profile and click the key symbol next to your avatar.

.. image:: /images/ore/help_1.png
    :align: center
    :alt: PGP public key 1

You will then be prompted to enter your key into a text box. You must be sure to enter the key in its entirety or
signature validation will fail.

.. image:: /images/ore/help_2.png
    :align: center
    :alt: PGP public key 2

Finally, you must sign your built plugin with your *private* key of the public key you uploaded. You only need to sign
the file that you are uploading: for instance, if you are uploading a ZIP with your plugin JAR included, only the ZIP
needs to (and should only be) signed. The easiest way to sign the file you intend to upload is with the following GPG
command.

.. code-block:: bash

    gpg --output myplugin-1.0.0.jar.sig --detach-sig myplugin-1.0.0.jar

.. note::

    If you're using a build manager such as Gradle or Maven, there are plugins you can apply to your build script that
    can sign built artifacts and automate this laborious process for you. For more information, please refer to
    `Gradle's documentation <https://docs.gradle.org/current/userguide/signing_plugin.html>`__ or
    `Maven's documentation <https://maven.apache.org/plugins/maven-gpg-plugin/>`__.

Uploading Your Plugin
~~~~~~~~~~~~~~~~~~~~~

Once your plugin's JAR file is packaged with an ``mcmod.info`` descriptor file in the top-level and signed with your
PGP private key, your plugin is ready for uploading! To create a project on Ore, you must have an active Sponge
account. Hitting the "Sign up" button in the top-right corner will take you to the appropriate page to create one. If
you already have an account, just hit the "Log in" button in the top-right corner to log into Ore.

Once logged in, navigate to your avatar and select the "New" option in the drop-down menu that appears, or just press
the "C" key.

While uploading, the creation wizard will first ask for your main upload file and then for your detached signature.

Setting Up Your Project on Ore
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

After uploading your plugin, you will be presented with some settings to configure your project however you like. Don't
worry, all of these can be changed later in the settings panel. After setting up your project you will be able to invite
Sponge users to be a part of your project. Any user that is registered on the forums can be invited to a project even if
they have never logged onto Ore. Upon creation of your project, the users you have invited will receive a notification
and will be able to either accept or decline your invitation. Project members will not be visible to the public until
the invitation has been accepted.

There are currently three groups you may assign to project members: `Developer`, `Editor`, and `Support`. The user
who created the project will be assigned the `Owner` which has unrestricted access to the project. Here is a quick
rundown of what each group can do within your project.

Owner
-----

There is a maximum of one owner to a project, is non-transferable (for now), and is the only group that may assign roles
to other members.

Developers
----------

Developers may create/edit release channels, pages and create/edit versions.

Editor
------

Editors may edit pages.

Support
-------

Support cannot do anything, merely a way of showing that the member is a part of the project.


Documenting Your Project With Pages
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

To document your plugin Ore offers the creation of pages. By default, you get a 'Home' page when you create your new
Project. To add new pages, you can click on the '+' icon in the Pages bar on the right of the screen; doing so will open
a pop-up where you  can choose a name and the parent page (adding a page on the Home page is not possible). If you
choose the '<None>' option the page will become a root page where you can later add child pages on. If you choose to add
it to another page the page will only show when the parent page is expanded. To document your pages, you can use
CommonMark.

Linking Pages
-------------

If you have the following tree structure in your pages:

- Home
- Commands
    - User
    - Admin
- Setup
    - Config
    - Permissions

And you want to add a link from the home page to the Config page in Setup you can use `WikiLinks`.
To add a WikiLink use the format `[[Link]]` so on the home page you can add `[[Setup/Config]]` and this will get be
converted to a link that is guaranteed to work.

.. note::
    You can also specify a title for the link and separate it with a Pipe symbol so `[[Title|Link]]` will become a link
    with the title

.. note::
    WikiLinks are only supported in Ore so if you use them on the Home-page they will not be displayed correctly on the
    forums. However, we still recommend using WikiLinks instead of normal links to guarantee the future working of your
    wiki.

Project States
~~~~~~~~~~~~~~

When creating a new project, a banner will appear telling you your project is in a 'new' state.
The new state is intended to indicated to users and admins you are still working on the setup of your project. If you
have finished documenting your plugin to make it comply with the guidelines, click the 'publish' button to complete the
process. However, if you don't publish it yourself then the project will be published automatically 24 hours after its
creation.

Needs Changes
-------------

Ore staff can hide your project and request changes; the changes in question will be listed in the banner. This state
will hide your project from the public; if you are done you can send your project for approval. 

Needs Approval
--------------

In this state your project is still hidden from the public while waiting for a staff member to review and validate the
changes that were requested.  Please be patient, the staff members might be busy with other tasks. If the project gets
approved it will automatically become visible again. Please make sure to actually fix the points that require changes.
