package org.poo.main.objects.Actions;

import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.BusinessAccount;

import java.util.ArrayList;
import java.util.List;

public class AddNewBusinessAssociate implements Action {
    @Override
    public void execute(CommandInput input) {
        for (User u : Bank.getInstance().getUsers()) {
            if (u.getAccount(input.getAccount()) == null){
                continue;
            }
            BusinessAccount account = (BusinessAccount) u.getAccount(input.getAccount());
            if (account.isAssociated(input.getEmail())) {
                //err already associated
                return;
            }
            double rate = Bank.getInstance().getExchange()
                    .getExchangeRate("RON", account.getCurrency());
            List<Double> limits= new ArrayList<>();
            limits.add(500.0 * rate);
            limits.add(500.0 * rate);
            switch (input.getRole()) {
                case "manager":
                    account.getManagers().put(input.getEmail(), limits);
                    break;
                case "employee":
                    account.getEmployees().put(input.getEmail(), limits);
                    break;
                default:
                    //err invalid role
                    return;
            }
            //success output
        }
    }
}
