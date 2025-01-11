package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;

public class SetMinBalance implements Action {
    /**
     * Sets the minimum balance of an account
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
            for (User u : Bank.getInstance().getUsers()) {
                if (u.getAccount(input.getAccount()) == null) {
                    continue;
                }
                u.getAccount(input.getAccount()).setMinBalance(input.getAmount());
                return;
            }
            Output JSON = Output.getInstance();
            ObjectNode out = JSON.mapper.createObjectNode();
            out.put("command", "setMinBalance");
            ObjectNode output = JSON.mapper.createObjectNode();
            output.put("description", "Account not found");
            output.put("timestamp", input.getTimestamp());
            out.put("output", output);
            out.put("timestamp", input.getTimestamp());
            JSON.output.add(out);
    }
}
