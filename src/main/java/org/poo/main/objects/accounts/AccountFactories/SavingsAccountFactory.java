package org.poo.main.objects.accounts.AccountFactories;

import org.poo.main.objects.accounts.Account;
import org.poo.main.objects.accounts.ClassicAccount;
import org.poo.main.objects.accounts.SavingsAccount;

public class SavingsAccountFactory extends AccountFactory {
    /**
     * Create a new SavingsAccount
     * @return SavingsAccount
     */
    @Override
    protected Account createAccount(final String currency, final double interestRate
            , final String owner) {
        SavingsAccount account = new SavingsAccount();
        account.register(currency, interestRate);
        return account;
    }
}
