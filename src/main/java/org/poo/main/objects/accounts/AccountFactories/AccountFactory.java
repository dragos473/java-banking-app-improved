package org.poo.main.objects.accounts.AccountFactories;

import org.poo.main.objects.accounts.Account;

public abstract class AccountFactory {
    /**
     * Creates an account with the given currency and interest rate.
     * @param currency the currency of the account
     * @param interestRate the interest rate of the account
     * @return the created account
     */
    public Account create(final String currency, final double interestRate) {
        Account account = createAccount();
        account.register(currency, interestRate);
        return account;
    }
    protected abstract Account createAccount();
}
