package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Commerciant;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Account;
import org.poo.main.objects.accounts.Cards.Card;

public class PayOnline implements Action {
    private User user;
    private final CommandInput cardInfo = new CommandInput();
    /**
     * Pays online with a card
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        boolean oneTimeUsed = false;
        try {
            user = Bank.getInstance().getUser(input.getEmail());
            boolean found = false;

            for (Account a : user.getAccounts()) {
                Card c = a.getCard(input.getCardNumber());

                if (c == null) {
                    continue;
                }
                if (!c.isAvailable()) {
                    ObjectNode output = Output.getInstance().mapper.createObjectNode()
                            .put("description", "The card is frozen")
                            .put("timestamp", input.getTimestamp());
                    user.getTransactions().addTransaction(output, a.getIBAN());
                    return;
                }

                double rate = Bank.getInstance()
                        .getExchange().getExchangeRate(input.getCurrency(), a.getCurrency());
                double amount = input.getAmount() * rate;

                a.pay(amount);
                Commerciant commerciant = Bank.getInstance().getCommerciant(input.getCommerciant());
                if(commerciant.getCashbackStrategy().equals("nrOfTransactions")) {
                    a.Cashback(amount, commerciant);
                    Bank.getInstance().getCashback().notify(a, commerciant, amount);

                } else {
                    Bank.getInstance().getCashback().notify(a, commerciant, amount);
                    a.Cashback(amount, commerciant);
                }
                found = true;

                try {
                    c.payed();
                } catch (UnsupportedOperationException e) {
                    if (e.getMessage().equals("OneTimeUseCard")) {
                        cardInfo.setEmail(input.getEmail());
                        cardInfo.setCardNumber(c.getCardNumber());
                        cardInfo.setTimestamp(input.getTimestamp());
                        cardInfo.setAccount(a.getIBAN());
                        oneTimeUsed = true;
                    }
                }

                    ObjectNode output = Output.getInstance().mapper.createObjectNode()
                            .put("amount", amount)
                            .put("commerciant", input.getCommerciant())
                            .put("description", "Card payment")
                            .put("timestamp", input.getTimestamp());
                    user.getTransactions().addTransaction(output, a.getIBAN());
                    if (oneTimeUsed) {
                        new DeleteCard().execute(cardInfo);
                        new AddOneTimeCard().execute(cardInfo);
                    }
                    break;
                }

                if (!found) {
                    Output JSON = Output.getInstance();
                    ObjectNode out = JSON.mapper.createObjectNode();
                    out.put("command", "payOnline");
                    ObjectNode output = JSON.mapper.createObjectNode();
                    output.put("description", "Card not found");
                    output.put("timestamp", input.getTimestamp());
                    out.put("output", output);
                    out.put("timestamp", input.getTimestamp());
                    JSON.output.add(out);
                }

            } catch (Exception e) {
                    ObjectNode output = Output.getInstance().mapper.createObjectNode()
                            .put("description", "Insufficient funds")
                            .put("timestamp", input.getTimestamp());
                    user.getTransactions().addTransaction(output, input.getAccount());
            }
    }
}
