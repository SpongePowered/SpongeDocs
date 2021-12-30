==================
Metrics Collection
==================

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs>`__

.. javadoc-import::
    org.spongepowered.api.Sponge
    org.spongepowered.api.plugin.PluginContainer
    org.spongepowered.api.util.metric.MetricsConfigManager

Collection of metrics from servers that use your plugin can be an incredibly useful tool for knowing what direction
to take your plugin in. However, the :doc:`/ore/guidelines` (under External Connections) state that plugins may only
collect metrics if informed consent has been granted. Such consent must be **opt-in**, metrics collection must be off
by default.

To simplify gathering consent, Sponge provides an API that allow plugins to check for this consent in a central way.

Metrics collection consent is on a per-plugin basis. Plugins **must not** assume that consent for one plugin means
consent for all.

Obtaining Consent
=================

In order to gain consent from server owners, plugins may request that server owners enable metrics. This can be
of the form of a message on startup instructing the players that they can enable metrics by updating the configuration
option in configuration files. SpongeForge and SpongeVanilla also provide the ``/sponge metrics <pluginid> enable``
command.

Plugins **may** prompt server owners to run the command or allow for a "one click" enable in game.

.. warning::
    Plugins **may not** run the ``/sponge metrics <pluginid> enable`` command without a server owner's informed
    consent. Doing so will cause a plugin to be rejected from the Ore platform.

.. note::
    The ``/sponge metrics`` command is only guaranteed to exist on the ``SpongeForge`` and ``SpongeVanilla`` server
    implementations. Other implementations, such as ``Lantern``, may not include this command.

Checking for Consent
====================

The :javadoc:`MetricsConfigManager` allows you to determine if your plugin has gained consent to send metrics. This
can either be injected into your plugin class, or obtained via :javadoc:`Sponge#getMetricsConfigManager()` object
on demand.

**Every time** your plugin wishes to send metrics, you must check the
:javadoc:`MetricsConfigManager#getCollectionState(PluginContainer)`, supplying the :javadoc:`PluginContainer` of
your plugin. Metrics must only be sent if this returns ``Tristate#TRUE`` for your plugin.

The following example shows how to use field injections to get the ``MetricsConfigManager`` and the
``PluginContainer`` for your plugin, and uses those to determine whether consent to send metrics has been obtained.

**Example**

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.plugin.PluginContainer;
    import org.spongepowered.api.util.metric.MetricsConfigManager;

    @Inject
    private PluginContainer container;

    @Inject
    private MetricsConfigManager metricsConfigManager;

    public boolean hasConsent() {
        return this.metricsConfigManager.getCollectionState(this.container).asBoolean();
    }
