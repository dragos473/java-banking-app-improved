package org.poo.main.objects.accounts.AccountFactories;

import org.poo.main.objects.accounts.Account;
import org.poo.main.objects.accounts.ClassicAccount;

public class ClassicAccountFactory extends AccountFactory {
    /**
     * Create a new ClassicAccount
     * @return ClassicAccount
     */
    @Override
    protected Account createAccount() {
        return new ClassicAccount();
    }
}
