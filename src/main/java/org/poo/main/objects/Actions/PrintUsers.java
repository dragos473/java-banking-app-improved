package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Cards.Card;


public class PrintUsers implements Action {
    private final Bank bank = Bank.getInstance();
    private final Output JSON = Output.getInstance();

    private ArrayNode printAccount(final User user) {
        ArrayNode acc = JSON.mapper.createArrayNode();
            for (int i = 0; i < user.getAccounts().size(); i++) {
                ObjectNode account = JSON.mapper.createObjectNode();
                account.put("IBAN", user.getAccounts().get(i).getIBAN());
                account.put("balance", user.getAccounts().get(i).getBalance());
                account.put("currency", user.getAccounts().get(i).getCurrency());
                if (user.getAccounts().get(i).getClass().getTypeName().contains("Classic")) {
                    account.put("type", "classic");
                } else {
                    account.put("type", "savings");
                }
                ArrayNode cardArr = JSON.mapper.createArrayNode();
                for (Card card : user.getAccounts().get(i).getCards()) {
                    ObjectNode cardNode = JSON.mapper.createObjectNode();
                    cardNode.put("cardNumber", card.getCardNumber());
                    if (card.isAvailable()) {
                        cardNode.put("status", "active");
                    } else {
                        cardNode.put("status", "frozen");
                    }

                    cardArr.add(cardNode);
                }
                account.put("cards", cardArr);
                acc.add(account);
            }
        return acc;
    }
    /**
     * Prints all the users in the bank, along with the accounts and cards they have
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        ObjectNode out = JSON.mapper.createObjectNode();
        out.put("command", "printUsers");
        ArrayNode arr = JSON.mapper.createArrayNode();
        for (int i = 0; i < bank.getUsers().size(); i++) {
            ObjectNode node = JSON.mapper.createObjectNode();
            node.put("firstName", bank.getUsers().get(i).getFirstName());
            node.put("lastName", bank.getUsers().get(i).getLastName());
            node.put("email", bank.getUsers().get(i).getEmail());
            node.put("accounts", printAccount(bank.getUsers().get(i)));
            arr.add(node);
        }
        out.put("output", arr);
        out.put("timestamp", input.getTimestamp());
        JSON.output.add(out);
    }

}
