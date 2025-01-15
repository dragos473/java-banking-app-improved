package org.poo.main.objects.Actions;

import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.SplitPaymentManager;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Account;

public class AcceptSplitPayment implements Action {
    @Override
    public void execute(CommandInput input) {
        try {
            Bank bank = Bank.getInstance();
            User u = bank.getUser(input.getEmail());
            for (SplitPaymentManager spm : bank.getPendingPayments()) {
                for (Account acc : u.getAccounts()) {
                    if (spm.hasAccount(acc)) {
                        spm.accept(acc);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            //invalid user
        }
    }
}
