package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.SavingsAccount;

public class ChangeInterestRate implements Action {
    /**
     * Changes the interest rate of the savings account
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
                acc.setInterestRate(input.getInterestRate());
//                            {
//                "description": "Interest rate of the account changed to 0.81",
//                "timestamp": 2
//            },
                ObjectNode output = Output.getInstance().mapper.createObjectNode()
                        .put("description",
                                "Interest rate of the account changed to "
                                + input.getInterestRate())
                        .put("timestamp", input.getTimestamp());
                u.getTransactions().addTransaction(output, acc.getIBAN());
            }
        } catch (Exception e) {
            Output JSON = Output.getInstance();
            ObjectNode err = JSON.mapper.createObjectNode();
            err.put("description", "This is not a savings account")
                    .put("timestamp", input.getTimestamp());
            ObjectNode out = JSON.mapper.createObjectNode();
            out.put("command", "changeInterestRate")
                    .put("timestamp", input.getTimestamp())
                    .put("output", err);
            JSON.output.add(out);
        }
    }
}
