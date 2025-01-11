package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.ClassicAccount;

public class SpendingReport implements Action {
    /**
     * Prints a spending report for a given account
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        try {
            for (User u : Bank.getInstance().getUsers()) {
                if (u.getAccount(input.getAccount()) == null) {
                    continue;
                }
                try {
                    ClassicAccount acc = (ClassicAccount) u.getAccount(input.getAccount());
                } catch (ClassCastException e) {
                    ObjectNode err = Output.getInstance().mapper.createObjectNode();
                    err.put("error", "This kind of report is not supported for a saving account");
                    ObjectNode out = Output.getInstance().mapper.createObjectNode();
                    out.put("command", "spendingsReport")
                            .put("output", err);
                    out.put("timestamp", input.getTimestamp());
                    Output.getInstance().output.add(out);
                    return;
                }
                u.getTransactions().spendingReport(input.getStartTimestamp(),
                        input.getEndTimestamp(),
                        u.getAccount(input.getAccount()), input.getTimestamp());
                return;
            }
            throw new Exception("Account not found");
        } catch (Exception e) {
            Output JSON = Output.getInstance();
            ObjectNode err = JSON.mapper.createObjectNode();
            err.put("description", e.getLocalizedMessage())
                    .put("timestamp", input.getTimestamp());
            ObjectNode out = JSON.mapper.createObjectNode();
            out.put("command", "spendingsReport")
                    .put("timestamp", input.getTimestamp())
                    .put("output", err);
            JSON.output.add(out);
        }
    }
}
