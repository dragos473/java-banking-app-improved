package org.poo.main.objects;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.main.objects.accounts.Account;

import java.util.ArrayList;
import java.util.List;

public class Transactions {
    private final Output JSON = Output.getInstance();
    private final ArrayNode transactions = JSON.mapper.createArrayNode();
    private final List<String> accounts = new ArrayList<>();

    /**
     * Adds a transaction to the list of transactions
     * @param transaction the transaction to be added
     * @param IBAN the IBAN of the account that made the transaction
     */
    public void addTransaction(final ObjectNode transaction, final String IBAN) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).get("timestamp").asInt() > transaction.get("timestamp").asInt()) {
                transactions.insert(i, transaction);
                accounts.add(i, IBAN);
                return;
            }
        }
        transactions.add(transaction);
        accounts.add(IBAN);
    }

    /**
     * Prints all the transactions of the user
     * @param timestamp the timestamp of the command
     */
    public void printTransactions(final int timestamp) {
        ArrayNode transactionsOut = JSON.mapper.createArrayNode();
        transactionsOut.addAll(transactions);

        ObjectNode out = JSON.mapper.createObjectNode();
        out.put("command", "printTransactions");
        out.put("output", transactionsOut);
        out.put("timestamp", timestamp);
        JSON.output.add(out);
    }

    /**
     * Prints all the transactions of the given account
     * @param timestampStart the start timestamp
     * @param timestampEnd the end timestamp
     * @param account the account to print the transactions
     * @param timestamp the timestamp of the command
     */
    public void report(final int timestampStart, final int timestampEnd,
                       final Account account, final int timestamp) {
        ObjectNode output = JSON.mapper.createObjectNode();
        ArrayNode transactionsOut = JSON.mapper.createArrayNode();
        int lastTimestamp = -1;

        for (int i = 0; i < transactions.size(); i++) {
            ObjectNode transaction = (ObjectNode) transactions.get(i);
            int transactionTimestamp = transaction.get("timestamp").asInt();
            if (transactionTimestamp >= timestampStart && transactionTimestamp <= timestampEnd) {
                if (!accounts.get(i).equals(account.getIBAN())) {
                    continue;
                }

                if (transactionTimestamp != lastTimestamp) {
                    transactionsOut.add(transaction);
                    lastTimestamp = transactionTimestamp;
                }
            }
        }

        output.put("balance", account.getBalance())
                .put("currency", account.getCurrency())
                .put("IBAN", account.getIBAN());
        output.put("transactions", transactionsOut);

        ObjectNode out = JSON.mapper.createObjectNode();
        out.put("command", "report");
        out.put("output", output);
        out.put("timestamp", timestamp);
        JSON.output.add(out);
    }
    /**
     * Prints all the transactions of the given account
     * and the commerciants that the user has spent money on
     * @param timestampStart the start timestamp
     * @param timestampEnd the end timestamp
     * @param account the account to print the transactions
     * @param timestamp the timestamp of the command
     */
    public void spendingReport(final int timestampStart, final int timestampEnd,
                               final Account account, final int timestamp) {
        Commerciants commerciants = new Commerciants();
        ArrayNode transactionsOut = JSON.mapper.createArrayNode();

        for (int i = 0; i < transactions.size(); i++) {
            ObjectNode transaction = (ObjectNode) transactions.get(i);

            if (!transaction.has("commerciant")) {
                continue;
            }

            if (!accounts.get(i).equals(account.getIBAN())) {
                continue;
            }

            if (transaction.get("timestamp").asInt() < timestampStart
                    || transaction.get("timestamp").asInt() > timestampEnd) {
                continue;
            }

            String commerciant = transaction.get("commerciant").asText();
            double amount = transaction.get("amount").asDouble();
            commerciants.addCommerciants(commerciant, amount);
            transactionsOut.add(transaction);
        }

        ObjectNode output = JSON.mapper.createObjectNode();
        ArrayNode arr = JSON.mapper.createArrayNode();
        for (int i = 0; i < commerciants.getMoneySpent().size(); i++) {
            ObjectNode commerciant = JSON.mapper.createObjectNode();
            commerciant.put("commerciant", commerciants.getNames().get(i))
                    .put("total", commerciants.getMoneySpent().get(i));
            arr.add(commerciant);
        }

        output.put("balance", account.getBalance());
        output.put("commerciants", arr);
        output.put("currency", account.getCurrency())
                .put("IBAN", account.getIBAN());
        output.put("transactions", transactionsOut);

        ObjectNode out = JSON.mapper.createObjectNode();
        out.put("command", "spendingsReport");
        out.put("output", output);
        out.put("timestamp", timestamp);

        JSON.output.add(out);
    }
}
