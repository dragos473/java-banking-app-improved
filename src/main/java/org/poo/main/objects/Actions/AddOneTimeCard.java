package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Cards.Card;
import org.poo.main.objects.accounts.Cards.CardFactory.CardFactory;
import org.poo.main.objects.accounts.Cards.CardFactory.OneTimeUseCardFactory;

public class AddOneTimeCard implements Action {
    /**
     * Creates a new one time use card for the user in the given account
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        CardFactory factory;
        Card card;
        factory = new OneTimeUseCardFactory();
        try {
            User user = Bank.getInstance().getUser(input.getEmail());
            card = factory.create(user.getAccount(input.getAccount()).getBalance());
            user.getAccount(input.getAccount()).addCard(card);

            ObjectNode output = Output.getInstance().mapper.createObjectNode();
            output.put("account", input.getAccount());
            output.put("card", card.getCardNumber());
            output.put("cardHolder", user.getEmail());
            output.put("description", "New card created");
            output.put("timestamp", input.getTimestamp());
            user.getTransactions()
                    .addTransaction(output, user.getAccount(input.getAccount()).getIBAN());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
