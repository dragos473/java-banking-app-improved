package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Account;
import org.poo.main.objects.accounts.Cards.Card;

public class CashWithdrawal implements Action {
    private final CommandInput cardInfo = new CommandInput();

    @Override
    public void execute(final CommandInput input) {
        boolean oneTimeUsed = false;
        User u = null;
        String AccIBAN = null;
        ObjectNode output = Output.getInstance().mapper.createObjectNode()
                .put("timestamp", input.getTimestamp());
        try {
            u = Bank.getInstance().getUser(input.getEmail());
            for (Account a : u.getAccounts()) {
                if (a.getCard(input.getCardNumber()) == null) {
                    continue;
                }
                Card c = a.getCard(input.getCardNumber());
                AccIBAN = a.getIBAN();
                double rate = Bank.getInstance()
                        .getExchange().getExchangeRate("RON", a.getCurrency());
                double amount = input.getAmount() * rate;
                a.pay(amount);
//                try {
//                    c.payed();
//                } catch (UnsupportedOperationException e) {
//                    if (e.getMessage().equals("OneTimeUseCard")) {
//                        cardInfo.setEmail(input.getEmail());
//                        cardInfo.setCardNumber(c.getCardNumber());
//                        cardInfo.setTimestamp(input.getTimestamp());
//                        cardInfo.setAccount(a.getIBAN());
//                        oneTimeUsed = true;
//                    }
//                }
                output.put("description",
                        "Cash withdrawal of " + input.getAmount())
                        .put("amount", input.getAmount());
                u.getTransactions().addTransaction(output, a.getIBAN());
//                if (oneTimeUsed) {
//                    new DeleteCard().execute(cardInfo);
//                    new AddOneTimeCard().execute(cardInfo);
//                }
                return;
            }
            Output JSON = Output.getInstance();
            ObjectNode out = JSON.mapper.createObjectNode();
            out.put("command", "cashWithdrawal");
            ObjectNode outputJSON = JSON.mapper.createObjectNode();
            outputJSON.put("description", "Card not found");
            outputJSON.put("timestamp", input.getTimestamp());
            out.put("output", outputJSON);
            out.put("timestamp", input.getTimestamp());
            JSON.output.add(out);
            return;
        } catch (Exception e) {
            if (e.getMessage().equals("No User Found")) {
                Output JSON = Output.getInstance();
                ObjectNode out = JSON.mapper.createObjectNode();
                out.put("command", "cashWithdrawal");
                ObjectNode outputJSON = JSON.mapper.createObjectNode();
                outputJSON.put("description", "User not found");
                outputJSON.put("timestamp", input.getTimestamp());
                out.put("output", outputJSON);
                out.put("timestamp", input.getTimestamp());
                JSON.output.add(out);
            } else if (e.getMessage().equals("Insufficient funds")) {
                output.put("description", "Insufficient funds");
                u.getTransactions().addTransaction(output, AccIBAN);
                return;
            } else {
                output.put("description", "Cannot perform payment due to a minimum balance being set");
                u.getTransactions().addTransaction(output, AccIBAN);
                return;
            }
        }
    }
}
