============
Trade-Offers
============

.. javadoc-import::
    org.spongepowered.api.GameRegistry
    org.spongepowered.api.data.manipulator.mutable.entity.TradeOfferData
    org.spongepowered.api.entity.living.Villager
    org.spongepowered.api.item.inventory.ItemStackSnapshot
    org.spongepowered.api.item.merchant.Merchant
    org.spongepowered.api.item.merchant.TradeOffer
    org.spongepowered.api.item.merchant.TradeOfferGenerator
    org.spongepowered.api.item.merchant.TradeOfferListMutator
    org.spongepowered.api.item.merchant.VillagerRegistry

This topic covers the ingame :javadoc:`TradeOffer`\s offered by :javadoc:`Villager`\s/:javadoc:`Merchant`\s.

Merchant
========

A ``Merchant`` is a container for ``TradeOffers``. Its most common variant is the ``Villager``.
The ``Merchant`` interface can be used to open trading windows.

``TradeOffers`` can be added to and removed from ``Merchants`` using :javadoc:`TradeOfferData`.

TradeOffer
==========

A trade offer consists of

* an primary/first buying :javadoc:`ItemStackSnapshot`
* an optional secondary buying ``ItemStackSnapshot``
* a selling ``ItemStackSnapshot``
* already used uses
* maximal available uses
* a flag to indicate whether experience orbs are spawned on trade

``TradeOffers`` can be created using a  ``Builder`` or a :javadoc:`TradeOfferGenerator`.

TradeOfferBuilder
~~~~~~~~~~~~~~~~~

``TradeOfferBuilders`` are mainly used if you want to dynamically create ``TradeOffers`` on the fly.

The following code block builds a new ``TradeOffer`` that allows ``Players`` to trade five ``DIRT`` into three ``GRASS``
block items. This trade offer has four initial uses, after that the ``Merchant`` might need some time to offer this
``TradeOffer`` again.

.. code-block:: java

     TradeOffer offer = TradeOffer.builder()
         .firstBuyingItem(ItemStack.of(ItemTypes.DIRT, 5))
         .sellingItem(ItemStack.of(ItemTypes.GRASS, 3))
         .uses(0)
         .maxUses(4)
         .canGrantExperience(false)
         .build();

TradeOfferListMutator
~~~~~~~~~~~~~~~~~~~~~

A :javadoc:`TradeOfferListMutator` is an interface that is invoked during ``Villager`` level ups.
It can be used to replace existing ``TradeOffers`` (ex higher tier) and add new ``TradeOffers``.
Its simplist and only API provided variant is the ``TradeOfferGenerator``.
The different ``TradeOfferListMutators`` for each level and ``Career`` can be configured in the :javadoc:`VillagerRegistry`.

.. note::

    The ``TradeOffer`` list in ``Villagers`` might be generated lazily when the trading inventory is opened for the
    first time.

TradeOfferGenerator
~~~~~~~~~~~~~~~~~~~

``TradeOfferGenerators`` are simple templates for new ``TradeOffers``. They are a variant of the
``TradeOfferListMutator`` that will only add new entries and does not alter or remove any existing ``TradeOffers`` on
that villager.


.. code-block:: java

    TradeOfferGenerator tradeOfferGenerator = TradeOfferGenerator.builder()
            .setPrimaryItemGenerator(random -> ItemStack.of(ItemTypes.DIRT, random.nextInt(3) + 5))
            .setSellingGenerator(random -> ItemStack.of(ItemTypes.GRASS, 5))
            .startingUses(VariableAmount.baseWithVariance(2, 1))
            .maxUses(VariableAmount.fixed(5))
            .experienceChance(0.5)
            .build();

This ``TradeOfferGenerator`` will randomly generate ``TradeOffers`` that will 

* buy 5-8 ``DIRT`` 
* sell 5 ``GRASS``
* has 2-4 remaining initial uses 
* 5 max uses

The chance that the generated ``TradeOffer`` will grant experience is 50%.

.. note::

    You can use ``ItemStackGenerators`` to dynamically apply enchantments or other custom data to the buying and
    selling items.

VillagerRegistry
================

The ``VillagerRegistry`` can be obtained from the :javadoc:`GameRegistry`. It will be used to configure the
``TradeOfferListMutator`` that will be applied on a ``Villager``'s level-up .

.. code-block:: java

    VillagerRegistry villagerRegistry = this.game.getRegistry().getVillagerRegistry();
    List<TradeOfferListMutator> generators = new ArrayList<>(villagerRegistry.getMutatorsForCareer(Careers.FARMER, 1));
    generators.addAll(additionalFarmerLevel1TradeOffers());
    villagerRegistry.setMutators(Careers.FARMER, 1, generators);

The lowest specifiable level-up mutator is level 1. This is equivalent to a newly spawned ``Villager``.

.. note::

    Changes to the ``VillagerRegistry`` will be lost on server restart and won't have any impact on the ``TradeOffers``
    from ``Villagers`` that have levelled up in the past.