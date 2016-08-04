==========================
Economy API Best Practices
==========================

.. javadoc-import::
    org.spongepowered.api.service.economy.transaction.ResultType
    org.spongepowered.api.service.economy.transaction.TransactionResult

The Economy API tries to be abstract enough to give economy plugins flexibility in how they operate.
In order to give economy plugins as much control as possible, plugins consuming the Economy API should
follow some guidelines when working with it:

Withdrawing money
=================

Plugins should *not* check if an account has enough money before attempting to withdraw it. While this may
sound counter-intuitive, it allows economy plugins to fully control how they handle negative balances.

By checking yourself if the account has enough money, you prevent the economy plugin from (potentially)
allowing a negative balance. For example, one economy plugin might want to allow negative balances to admins,
or players with a certain permission. By performing the check yourself, you take this power away from the economy plugin.

This code illustrates what **not** to do:

.. code-block:: java
    
    import java.math.BigDecimal;
    
    import org.spongepowered.api.event.cause.Cause;
    import org.spongepowered.api.service.economy.EconomyService;
    import org.spongepowered.api.service.economy.account.Account;
        
    EconomyService service = ...;
    Account account = ...;
    BigDecimal requiredAmount = BigDecimal.valueOf(20);

    // BAD: Don't perform this check
    if (account.getBalance(service.getDefaultCurrency()).compareTo(requiredAmount) < 0) {
        // You don't have enough money!
    } else {
        // The account has enough, let's withdraw some cash!
        account.withdraw(service.getDefaultCurrency(), requiredAmount,
            Cause.source(this).build());
    }


Instead of this, the best thing to do is simply withdraw the amount you need, and check the :javadoc:`ResultType` of
the returned :javadoc:`TransactionResult`. An economy plugin which doesn't want to allow negative balances will simply
return :javadoc:`ResultType#ACCOUNT_NO_FUNDS`, or :javadoc:`ResultType#FAILED` in this case.

Here's how you **should** withdraw money:

.. code-block:: java
    
    import org.spongepowered.api.service.economy.transaction.ResultType;
    import org.spongepowered.api.service.economy.transaction.TransactionResult;
    
    EconomyService service = ...
    Account account = ...
    BigDecimal requiredAmount = BigDecimal.valueOf(20);

    TransactionResult result = account.withdraw(service.getDefaultCurrency(),
        requiredAmount, Cause.source(this).build());
    if (result.getResult() == ResultType.SUCCESS)) {
        // Success!
    } else if (result.getResult() == ResultType.FAILED || result.getResult() == ResultType.ACCOUNT_NO_FUNDS) {
        // Something went wrong!
    } else {
        // Handle other conditions
    }
