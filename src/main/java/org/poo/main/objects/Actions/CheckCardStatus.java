package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Account;

public class CheckCardStatus implements Action {
    /**
     * Checks the status of a card.
     * If the balance is under the minimum, the card will be frozen
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        for (User u : Bank.getInstance().getUsers()) {
            for (Account acc : u.getAccounts()) {
                if (acc.getCard(input.getCardNumber()) == null) {
                    continue;
                }
                if (!acc.getCard(input.getCardNumber()).isAvailable()) {
                    return;
                }
                if (acc.getBalance() > acc.getMinBalance()) {
                    return;
                }
                acc.getCard(input.getCardNumber()).freeze();

                ObjectNode output = Output.getInstance().mapper.createObjectNode()
                        .put("description",
                                "You have reached the minimum amount of funds, " +
                                        "the card will be frozen")
                        .put("timestamp", input.getTimestamp());
                u.getTransactions().addTransaction(output, acc.getIBAN());
                return;
            }
        }
        Output JSON = Output.getInstance();
        ObjectNode out = JSON.mapper.createObjectNode();
        out.put("command", "checkCardStatus");
        ObjectNode output = JSON.mapper.createObjectNode();
        output.put("description", "Card not found");
        output.put("timestamp", input.getTimestamp());
        out.put("output", output);
        out.put("timestamp", input.getTimestamp());
        JSON.output.add(out);
    }
}
