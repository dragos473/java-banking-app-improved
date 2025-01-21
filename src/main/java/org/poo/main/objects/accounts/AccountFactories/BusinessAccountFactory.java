package org.poo.main.objects.accounts.AccountFactories;

import org.poo.main.objects.accounts.Account;
import org.poo.main.objects.accounts.BusinessAccount;

public class BusinessAccountFactory extends AccountFactory {
    /**
     * Create a new BusinessAccount
     * @return BusinessAccount
     */
    @Override
    protected Account createAccount(final String currency, final double interestRate
            , final String owner) {
        BusinessAccount account = new BusinessAccount();
        account.register(currency, owner);
        return account;
    }
}
