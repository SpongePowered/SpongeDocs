===============
Installing Java
===============

Java is needed to run Sponge and Minecraft: Java Edition. You most likely already have Java, but you may need to update
it.

Sponge requires Java 8 (specifically ``1.8.0_40`` or above) at this time. Older Java versions are deprecated and will
not work with Sponge. The difference between major versions of Java (6, 7, 8) is significant, and older versions cannot
run Sponge properly. Currently Sponge will not run with Java 9.

Installing Java
===============

If you have Windows or macOS for your computer, you can `download Java from the official website
<https://java.com/en/download/manual.jsp>`__.

Linux users can install OpenJDK via their package manager. OpenJDK is the open source version of the Oracle version of
Java, and it should work just as well, if not better. However, it's also possible to `download the Oracle version for
Linux <https://www.oracle.com/technetwork/java/javase/downloads/index.html>`__, but be aware that many Java-dependent
Linux packages will still install OpenJDK anyway.

You may have to configure the path to the JRE/JDK in your 

* launcher using ``Profile`` (``Advanced Options``) -> ``<Your Profile>`` -> ``Java-Executable`` (``.../java.exe``)
* server using either the ``PATH`` variable or using the full path to your java executable

32-bit vs. 64-bit
~~~~~~~~~~~~~~~~~

If your computer supports it, you should use 64-bit versions of Java whenever possible. The Java installers from the
`this website <https://java.com/en/download/>`__ should detect whether your computer is ready for 64-bit.
However the autodetection is sometimes wrong (try different browsers, if you are unsure).

It is also possible to look this up in the system information window.

* Windows: Press ``<Win>`` + ``<Pause>`` or via ``Systeminformation`` have a look at your ``system type``.
* Linux: Open a terminal and type ``arch`` or ``uname -m``. ``i686`` -> 32-bit. ``x86_64`` -> 64-bit.
* Mac: There is only one download anyway...

If you are sure to about your system's architecture you can
`manually choose <https://java.com/en/download/manual.jsp>`__ the correct installer yourself.

Because the 64-bit version of Java runs considerably better, and also lets Java use more than ~3 GB of your RAM
(memory), we always recommend it over 32-bit.

Most modern computers support 64-bit.

JDK vs. JRE
~~~~~~~~~~~

The JRE (Java Runtime Environment) is used to run Java applications. The download page linked above provides the JRE.

The JDK (Java Development Kit) is used to create Java applications, and you do not need it unless you plan to make
Sponge plugins or work on Sponge. However, in some cases, you may need the JDK to diagnose a running Java application
such as Sponge. You can download the JDK `from a different site
<https://www.oracle.com/technetwork/java/javase/downloads/index.html>`__.
