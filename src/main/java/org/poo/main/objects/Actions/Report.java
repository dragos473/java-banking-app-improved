package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.ClassicAccount;

public class Report implements Action {
    /**
     * Prints a report for a given account
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        boolean found = false;
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
                    out.put("command", "report")
                            .put("timestamp", input.getTimestamp())
                            .put("output", err);
                    Output.getInstance().output.add(out);
                    return;
                }
                u.getTransactions().report(input.getStartTimestamp(), input.getEndTimestamp(),
                        u.getAccount(input.getAccount()), input.getTimestamp());
                found = true;

            }
            if (!found) {
                throw new Exception("Account not found");
            }
        } catch (Exception e) {
            Output JSON = Output.getInstance();
            ObjectNode err = JSON.mapper.createObjectNode();
            err.put("description", e.getLocalizedMessage())
                    .put("timestamp", input.getTimestamp());
            ObjectNode out = JSON.mapper.createObjectNode();
            out.put("command", "report")
                    .put("timestamp", input.getTimestamp())
                    .put("output", err);
            JSON.output.add(out);
        }
    }
}
