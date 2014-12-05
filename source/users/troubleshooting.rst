===============
Troubleshooting
===============

You're probably here because something went wrong with your Sponge
server. Let's see if we can figure out what it was, and what to do about
it. If you haven't done so already, we recommend reading :doc:`../general/bukkit-compatibility`.

Java is not installed on your Computer
--------------------------------------

**Solution**: Download and install the latest version of the  Java Runtime
Environment (JRE) available for your operating system. Sponge (and Minecraft)
are built on Java 6, however you are recommended and encouraged to use Java 7.
Java 8 does not currently have widespread usage so installing it is not recommended.
Oracle Java is a good option for most Operating Systems.

Network connection failure (or DDoS attack)
-------------------------------------------

**Symptoms**: Network connection is very laggy, drops in and out, or
absent.

**Solutions**: Check your connection to the modem or router. See if your
browser has similar troubles. You can use a free service like
speedtest.net to check your connection speed. Other services running on
your computer or local network may the cause. A DDoS attack will
probably completely kill your connectivity, and you should contact your
ISP if you believe this to be the case.

Not enough free memory
----------------------

**Symptoms**: Server crashes, often accompanied with "Out of Memory"
messages.

**Solutions**: Expand the maximum Perm memory size with the startup
argument ``-XX:MaxPermSize=128``. Expand your server heap memory (if
possible) with startup arguments eg. ``-Xms1024M`` (1GB starting memory) and
``-Xmx2048M`` (2GB maximum). Monitor your free memory on the computer and
see if there is some locked up in other processes. You may need to kill
frozen java processes, or restart your machine. Memory leaks sometimes
occur with bugs in plugins, which can take time to isolate.

Malformed config file (eg. bad editing)
---------------------------------------

**Symptom**: One (or more) plugins refuse to load, or behave in
unexpected ways. The server log files will contain messages about
unreadable files on startup. The server may crash, and data may be
corrupted.

**Solution**: Stop the server, and check your edited files. Load backup
files of any corrupted data. You may need to delete a config file
entirely and allow it to regenerate server startup.

A plugin (or mod) has malfunctioned
-----------------------------------

**Symptom**: This could be almost anything - whatever your plugins do,
plus the X-factor. Commonly the server crashes with a train of error
messages in the server log files.

**Solution**: Stop the server, and check to see nothing has been
corrupted. Be sure to check that it isn't from an incorrectly edited
config file (above). Remove suspect plugins and add them again one by
one, restarting the server each time. The problem may originate from one
Plugin that is out of date - check for updates. Plugin conflict may also
be the cause, having two incompatible plugins.

Operating System is unstable (eg. Virus infection)
--------------------------------------------------

**Symptom**: The server keeps crashing or timing out, and other parts of
your operating system are also having problems.

**Solution**: Stop everything. Thoroughly check your system and storage
devices for malware and viruses. Good tools for this include AdwCleaner,
Junkware Removal Tool, MalwareBytes, and most antivirus ware. Check your
server files for corruption after a clean restart of your system.
Examine the hardware for damage too if the problems persist - eg. a
faulty power supply.

Corrupted Data
--------------

**Symptom**: World files fail to load or cause server to crash when
players enter certain chunks. Database corruption.

**Solution**: Load backup files of corrupted data. Software for
repairing damaged worlds is available, and missing regions may be
regenerated. Investigate the cause of corruption - was it a malformed
plugin, database driver, power failure or something else? Always make
sure you make regular backups of important data onto a secure device.

Problem between keyboard and chair
----------------------------------

**Symptom**: Everything was working fine yesterday. It went strange
today after I did XYX ...

**Solution**: This wiki is not large enough to encompass the things
people may do that will cause software to fail in unpredictable ways. It
is always worth thinking long and hard about what you may have done
recently that could have affected the smooth running of your server. A
memory card may be loose after dusting, a shortcut may be broken...

There is a bug in Sponge
------------------------

**Symptom**: None of the above apply, and it still doesn't work as it
should.

**Solution**: Time to get out the big guns. File a report on the Sponge
issue tracker, remembering to include details of the version of Forge
and Sponge you are using, and a link to the relevant server log file.

There is something wrong with the Universe
------------------------------------------

We can't help you with this one. You're on your own.
