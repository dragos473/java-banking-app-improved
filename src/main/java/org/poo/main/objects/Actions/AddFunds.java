package org.poo.main.objects.Actions;

import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Account;

public class AddFunds implements Action {
    /**
     * Adds funds to the balance of the account
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        for (User user : Bank.getInstance().getUsers()) {
                for (Account account : user.getAccounts()) {
                    if (account.getIBAN().equals(input.getAccount())) {
                        account.setBalance(account.getBalance() + input.getAmount());
                    }
                }
        }
    }
}
