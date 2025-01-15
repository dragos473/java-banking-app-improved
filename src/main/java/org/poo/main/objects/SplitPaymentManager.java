package org.poo.main.objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Setter;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.accounts.Account;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SplitPaymentManager {
    private Map<Account, Double> pending = new HashMap<>();
    private Map<Account, Double> accepted = new HashMap<>();
    @Setter
    private CommandInput input;

    public void subscribe(final Account account, final double amount) {
        pending.put(account, amount);
    }

    public void reject(final int timestamp) {
        pending.clear();
        accepted.clear();
//        One user rejected the payment.
        //TODO: Reject payment
    }
    public void accept(final Account account) {
        accepted.put(account, pending.get(account));
        pending.remove(account);
        if (!pending.isEmpty()) {
            return;
        }
        String poorIBAN = null;
        ArrayNode involvedAccounts = Output.getInstance().mapper.createArrayNode();
        ArrayNode amountForUsers = Output.getInstance().mapper.createArrayNode();

        for(int i = 0; i < input.getAccounts().size(); i++) {
            involvedAccounts.add(input.getAccounts().get(i));
            if (input.getSplitPaymentType().equals("custom")) {
                amountForUsers.add(input.getAmountForUsers().get(i));
            }
        }

        for (Account acc : accepted.keySet()) {
            if (acc.getBalance() < accepted.get(acc)) {
                if (input.getAccounts().indexOf(acc.getIBAN()) < input.getAccounts().indexOf(poorIBAN)) {
                    poorIBAN = acc.getIBAN();
                } else if (poorIBAN == null) {
                    poorIBAN = acc.getIBAN();
                }
            }
        }

        for (Account acc : accepted.keySet()) {
            //Mental breakdown
            String amountSaVaPlacaCumArataCaSmrEuNuVaPlaceNimic =
                    String.format("%.2f", input.getAmount());
            ObjectNode output = Output.getInstance().mapper.createObjectNode()
                    .put("currency", input.getCurrency())
                    .put("description", "Split payment of "
                            + amountSaVaPlacaCumArataCaSmrEuNuVaPlaceNimic
                            + " "
                            + input.getCurrency())
                    .put("timestamp", input.getTimestamp());
            output.put("involvedAccounts", involvedAccounts);
            if (input.getSplitPaymentType().equals("custom")) {
                output.put("amountForUsers", amountForUsers);
            } else {
                output.put("amount", input.getAmount()/input.getAccounts().size());
            }
            output.put("splitPaymentType", input.getSplitPaymentType());

            if (poorIBAN != null) {
                for (User u : Bank.getInstance().getUsers()) {
                    if (u.getAccount(acc.getIBAN()) == null) {
                        continue;
                    }
                    output.put("error", "Account " + poorIBAN
                            + " has insufficient funds for a split payment.");
                    u.getTransactions().addTransaction(output, acc.getIBAN());
                    break;
                }

            } else {
                try {
                    double amount = accepted.get(acc);
                    acc.pay(amount);
                    for (User u : Bank.getInstance().getUsers()) {
                        if (u.getAccount(acc.getIBAN()) == null) {
                            continue;
                        }
                        u.getTransactions().addTransaction(output, acc.getIBAN());
                        break;
                    }
                } catch (Exception e) {
                    //idk not like it could happen
                }
            }
        }
    }

    public Boolean hasAccount(Account account) {
        return pending.containsKey(account);
    }
}
