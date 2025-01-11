package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Account;
import org.poo.main.objects.accounts.Cards.Card;


public class DeleteCard implements  Action {
    /**
     * Deletes a card from the user's account
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {

        try {
            User user = Bank.getInstance().getUser(input.getEmail());
            System.out.println(user.getAccounts());

            for (Account a : user.getAccounts()) {
                for (Card c : a.getCards()) {
                    System.out.println(c.getCardNumber());
                }
                if (a.getCard(input.getCardNumber()) == null) {
                    continue;
                }
                a.removeCard(input.getCardNumber());

                ObjectNode output = Output.getInstance().mapper.createObjectNode();
                output.put("account", a.getIBAN());
                output.put("card", input.getCardNumber());
                output.put("cardHolder", user.getEmail());
                output.put("description", "The card has been destroyed");
                output.put("timestamp", input.getTimestamp());
                user.getTransactions().addTransaction(output, a.getIBAN());
                return;
            }
        } catch (Exception e) {
            System.out.println("Card not found");
        }

    }
}
