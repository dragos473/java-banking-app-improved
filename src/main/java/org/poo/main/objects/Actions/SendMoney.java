package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Account;

public class SendMoney implements Action {
    private User user;
    /**
     * Transfers money from one account to another
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        if (!input.getAccount().contains("RO")) {
            return;
        }
        try {
            user = Bank.getInstance().getUser(input.getEmail());
            Account payer = user.getAccount(input.getAccount());
            for (User u : Bank.getInstance().getUsers()) {
                if (u.getAccount(input.getReceiver()) != null) {
                    Account payee  = u.getAccount(input.getReceiver());
                    payer.pay(input.getAmount());
                    double rate = Bank.getInstance()
                            .getExchange().getExchangeRate
                                    (payer.getCurrency(), payee.getCurrency());
                    double amount = input.getAmount() * rate;
                    payee.deposit(amount);
                    ObjectNode payerOut = Output.getInstance().mapper.createObjectNode()
                            .put("amount", input.getAmount() + " " + payer.getCurrency())
                            .put("description", input.getDescription())
                            .put("receiverIBAN", payee.getIBAN())
                            .put("senderIBAN", payer.getIBAN())
                            .put("timestamp", input.getTimestamp())
                            .put("transferType", "sent");
                    Bank.getInstance().getUser(input.getEmail())
                            .getTransactions().addTransaction(payerOut, payer.getIBAN());
                    ObjectNode payeeOut = Output.getInstance().mapper.createObjectNode()
                            .put("amount", amount + " " + payee.getCurrency())
                            .put("description", input.getDescription())
                            .put("receiverIBAN", payee.getIBAN())
                            .put("senderIBAN", payer.getIBAN())
                            .put("timestamp", input.getTimestamp())
                            .put("transferType", "received");
                    u.getTransactions().addTransaction(payeeOut, payee.getIBAN());
                    break;
                }
            }

        } catch (Exception e) {
            if (e.getMessage().equals("Insufficient funds")) {
                ObjectNode output = Output.getInstance().mapper.createObjectNode()
                        .put("description", "Insufficient funds")
                        .put("timestamp", input.getTimestamp());
                user.getTransactions().addTransaction(output, input.getAccount());
            }
        }
    }
}
