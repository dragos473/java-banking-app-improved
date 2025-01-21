package org.poo.main.objects.Actions;

import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.BusinessAccount;

import java.util.ArrayList;
import java.util.List;

public class ChangeSpendingLimit implements Action{
    @Override
    public void execute(CommandInput input) {
        for (User u : Bank.getInstance().getUsers()) {
            if (u.getAccount(input.getAccount()) == null){
                continue;
            }
           // BusinessAccount account = (BusinessAccount) u.getAccount(input.getAccount());
//            if (!account.isAssociated(input.getEmail())) {
//                //err not associated
//                return;
//            }
//            double rate = Bank.getInstance().getExchange()
//                    .getExchangeRate("RON", account.getCurrency());
            //account.getAssociate(input.getEmail()).set(0, input.getAmount() * rate);
            //success output
        }
    }
}
