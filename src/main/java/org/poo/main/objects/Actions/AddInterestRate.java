package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.SavingsAccount;

public class AddInterestRate implements Action {
    /**
     * Adds the interest rate to the balance of the savings account
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        try {
            for (User u : Bank.getInstance().getUsers()) {
                if (u.getAccount(input.getAccount()) == null) {
                    continue;
                }
                SavingsAccount acc = (SavingsAccount) u.getAccount(input.getAccount());
                double amount = acc.getBalance() * acc.getInterestRate();
                acc.deposit(amount);

                ObjectNode output = Output.getInstance().mapper.createObjectNode()
                        .put("timestamp", input.getTimestamp())
                        .put("amount", amount)
                        .put("currency", acc.getCurrency())
                        .put("description", "Interest rate income");
                u.getTransactions().addTransaction(output, acc.getIBAN());
            }
        } catch (Exception e) {
            Output JSON = Output.getInstance();
            ObjectNode err = JSON.mapper.createObjectNode();
            err.put("description", "This is not a savings account")
                    .put("timestamp", input.getTimestamp());
            ObjectNode out = JSON.mapper.createObjectNode();
            out.put("command", "addInterest")
                    .put("timestamp", input.getTimestamp())
                    .put("output", err);
            JSON.output.add(out);
        }
    }
}
