package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;

public class DeleteAccount implements Action {
    /**
     * Deletes an account from a user
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        try {
            if (Bank.getInstance().getUser(input.getEmail()).getAccount(input.getAccount())
                    .getBalance() != 0) {
                Output JSON = Output.getInstance();

                ObjectNode out = JSON.mapper.createObjectNode();
                out.put("command", "deleteAccount");
                ObjectNode output = JSON.mapper.createObjectNode();
                output.put("error",
                                "Account couldn't be deleted - see org.poo.transactions for details")
                        .put("timestamp", input.getTimestamp());
                out.put("output", output);
                out.put("timestamp", input.getTimestamp());
                JSON.output.add(out);

                ObjectNode errOut = JSON.mapper.createObjectNode()
                        .put("description",
                                "Account couldn't be deleted - there are funds remaining")
                        .put("timestamp", input.getTimestamp());
                Bank.getInstance().getUser(input.getEmail())
                        .getTransactions().addTransaction(errOut, input.getAccount());
                return;
            }

            Bank.getInstance().getUser(input.getEmail()).removeAccount(input.getAccount());

        } catch (Exception e) {
            e.printStackTrace();
        }

        Output JSON = Output.getInstance();
        ObjectNode out = JSON.mapper.createObjectNode();
        out.put("command", "deleteAccount");
        ObjectNode card = JSON.mapper.createObjectNode();
        card.put("success", "Account deleted");
        card.put("timestamp", input.getTimestamp());
        out.put("output", card);
        out.put("timestamp", input.getTimestamp());
        JSON.output.add(out);
    }
}
