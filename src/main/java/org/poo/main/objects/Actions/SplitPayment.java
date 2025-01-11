package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Account;

public class SplitPayment implements Action {
    private Account a;
    private String poorIBAN;

    /**
     * Splits a payment between multiple accounts
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        double toPay = input.getAmount() / input.getAccounts().size();
        boolean err = false;

        for (String account : input.getAccounts()) {
            for (User u : Bank.getInstance().getUsers()) {
                if (u.getAccount(account) == null) {
                    continue;
                }
                try {
                    double rate = Bank.getInstance().getExchange()
                            .getExchangeRate(input.getCurrency(),
                                    u.getAccount(account).getCurrency());
                    double amount = toPay * rate;
                    if (u.getAccount(account).getBalance() < amount) {
                        err = true;
                        poorIBAN = u.getAccount(account).getIBAN();
                    }
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }

        for (String account : input.getAccounts()) {
            for (User u : Bank.getInstance().getUsers()) {
                if (u.getAccount(account) == null) {
                    continue;
                }
                try {
                    a = u.getAccount(account);
                    double rate = Bank.getInstance().getExchange()
                            .getExchangeRate(input.getCurrency(), a.getCurrency());
                    double amount = toPay * rate;
                    if (!err) {
                        a.pay(amount);
                    }

                }  catch (Exception e) {
                    ObjectNode output = Output.getInstance().mapper.createObjectNode()
                            .put("description", "Insufficient funds")
                            .put("timestamp", input.getTimestamp());
                    u.getTransactions().addTransaction(output, a.getIBAN());
                }
                ArrayNode involvedAccounts = Output.getInstance().mapper.createArrayNode();
                String amount = String.format("%.2f", input.getAmount());
                for (String acc : input.getAccounts()) {
                    involvedAccounts.add(acc);
                }
                ObjectNode output = Output.getInstance().mapper.createObjectNode()
                        .put("amount", toPay)
                        .put("currency", input.getCurrency())
                        .put("description", "Split payment of "
                                                        + amount
                                                        + " "
                                                        + input.getCurrency())
                        .put("timestamp", input.getTimestamp());
                output.put("involvedAccounts", involvedAccounts);
                if (err) {
                    output.put("error", "Account " + poorIBAN
                            + " has insufficient funds for a split payment.");
                }
                u.getTransactions().addTransaction(output, a.getIBAN());
            }
        }
    }
}
