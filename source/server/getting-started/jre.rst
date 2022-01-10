===============
Installing Java
===============

Java is needed to run Sponge and Minecraft: Java Edition. You most likely already have Java, but you may need to update
it.

SpongeAPI 7 and 8 requires Java 8 (specifically ``1.8.0_20`` or above). SpongeAPI 8 will also run on later versions of
Java, we recommend Java 17 as the latest LTS version.

Installing Java
===============

If you have Windows or macOS for your computer, you can `download builds of OpenJDK from Adoptium here
<https://adoptium.net/>`__. Builds of Java 8, 11 and 17 are all available here.

You may have to configure the path to the JRE/JDK in your 

* launcher using ``Profile`` (``Advanced Options``) -> ``<Your Profile>`` -> ``Java-Executable`` (``.../java.exe``)
* server using either the ``PATH`` variable or using the full path to your java executable

32-bit vs. 64-bit
~~~~~~~~~~~~~~~~~

If your computer supports it, you should use 64-bit versions of Java whenever possible. Most modern computers will be 
running 64-bit operating systems. The Adoptium site linked above should detect what you're running and offer the correct
download.

It is also possible to look this up in the system information window.

* Windows: Press ``<Win>`` + ``<Pause>`` or via ``Systeminformation`` have a look at your ``system type``.
* Linux: Open a terminal and type ``arch`` or ``uname -m``. ``i686`` -> 32-bit. ``x86_64`` -> 64-bit.
* macOS: All supported Macs are 64-bit - however if you are running an Apple Silicon machine, choose arm64 over x64.

If you are sure to about your system's architecture you can
`manually choose <https://adoptium.net/releases.html>`__ the correct installer yourself.
