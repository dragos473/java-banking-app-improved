package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.SplitPaymentManager;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Account;

public class SplitPayment implements Action {
    private Account a;
    private String poorIBAN;

    /**
     * Splits a payment between multiple accounts
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        SplitPaymentManager spm = new SplitPaymentManager();
        spm.setInput(input);
        Bank bank = Bank.getInstance();
        bank.getPendingPayments().add(spm);

        for (String account : input.getAccounts()) {
            boolean found = false;
            for (User u : bank.getUsers()) {
                if (u.getAccount(account) == null) {
                    continue;
                }
                found = true;
                double rate = bank.getExchange()
                        .getExchangeRate(input.getCurrency(),
                                u.getAccount(account).getCurrency());
                double amount;

                switch (input.getSplitPaymentType()) {
                    case "equal":
                        amount = (input.getAmount()/
                                        input.getAccounts().size()) * rate;
                        spm.subscribe(u.getAccount(account), amount);
                        break;
                    case "custom":
                        amount =
                                input.getAmountForUsers()
                                        .get(input.getAccounts().indexOf(account))
                                        * rate;
                        spm.subscribe(u.getAccount(account), amount);
                        break;
                }
                break;
            }
            if (!found) {
                ObjectNode output = Output.getInstance().mapper.createObjectNode()
                        .put("description", "User not found")
                        .put("timestamp", input.getTimestamp());
            }
        }
    }
}
