package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Account;

public class UpgradePlan implements Action {
    @Override
    public void execute(final CommandInput input) {
        ObjectNode output = Output.getInstance().mapper.createObjectNode()
                .put("timestamp", input.getTimestamp());
        for (User u : Bank.getInstance().getUsers()) {
            if (u.getAccount(input.getAccount()) == null) {
                continue;
            }
            Account acc = u.getAccount(input.getAccount());
            double rate = Bank.getInstance().getExchange().getExchangeRate("RON", acc.getCurrency());
            double toPay = 0;
            if (u.getPlan().equals(input.getNewPlanType())) {
                output.put("description",
                        "The user already has the " + u.getPlan() + " plan.");
                u.getTransactions().addTransaction(output,
                        u.getAccount(input.getAccount()).getIBAN());
                return;
            }
            if (u.getPlan().contains("st")) {
                if(input.getNewPlanType().equals("silver")) {
                    toPay = 100 * rate;
                    }
                else if(input.getNewPlanType().equals("gold")) {
                    toPay = 350 * rate;
                }
            } else if (u.getPlan().equals("silver")) {
                if(input.getNewPlanType().equals("gold")) {
                    toPay = 250 * rate;
                }
            } else {
                output.put("description",
                        "You cannot downgrade your plan.");
                u.getTransactions().addTransaction(output,
                        u.getAccount(input.getAccount()).getIBAN());
                return;
            }
//                acc.pay(toPay);
            if (acc.getBalance() < toPay) {
                output.put("description", "Insufficient funds");
                u.getTransactions().addTransaction(output,
                        u.getAccount(input.getAccount()).getIBAN());
                return;

            }
            acc.setBalance(acc.getBalance() - toPay);
            u.setPlan(input.getNewPlanType());
            for (Account a : u.getAccounts()) {
                a.setPlan(input.getNewPlanType());
            }

            output.put("description", "Upgrade plan")
                    .put("newPlanType", input.getNewPlanType())
                    .put("accountIBAN", acc.getIBAN());
            u.getTransactions().addTransaction(output,
                    u.getAccount(input.getAccount()).getIBAN());
            return;
        }
        output.put("description", "Account not found");
        Output.getInstance().output.add(output);
    }
}
