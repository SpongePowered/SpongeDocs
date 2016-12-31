========
Security
========

Spongie takes security very seriously. For this reason, all uploaded plugins are PGP signed to ensure you’re getting
what the plugin author intended to upload and are immediately queued to go through a meticulous vetting process.

.. note::

    Ore will warn you if you attempt to download a plugin that has not been cleared by our reviewing process.
    We disclaim all responsibility for any harm to your server or system should you choose not to heed the warning.

Ore requires all plugin submissions to be signed with
`Pretty Good Privacy (PGP) <https://en.wikipedia.org/wiki/Pretty_Good_Privacy>`_ for security purposes. This helps us
verify that the identity of the uploader is indeed the same identity of the account holder. This means that if your
account were to become compromised, the attacker would need your private key to upload plugins to Ore. This alone
dramatically reduces the chances of having malicious code uploaded to and associated with your account.

You can read about how to *upload a public key to your account* and how to *sign your plugins with your private key*
:doc:`/ore/publish`.

Additionally, while not required, we highly recommend you enable two-factor authentication on your account to prevent it
from being compromised. You can enable two-factor authentication in your general Sponge account settings
`here <https://auth.spongepowered.org/settings>`_.
