package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.accounts.Account;
import org.poo.main.objects.accounts.AccountFactories.AccountFactory;
import org.poo.main.objects.accounts.AccountFactories.BusinessAccountFactory;
import org.poo.main.objects.accounts.AccountFactories.ClassicAccountFactory;
import org.poo.main.objects.accounts.AccountFactories.SavingsAccountFactory;

public class AddAccount  implements Action {

    /**
     * Creates a new account for the user
     * @param input the input command
     */
    @Override
    public void execute(final CommandInput input) {
        AccountFactory factory;
        Account account = switch (input.getAccountType()) {
            case "business" -> {
                factory = new BusinessAccountFactory();
                yield factory.create(input.getCurrency(), 0, input.getEmail());
            }
            case "savings" -> {
                factory = new SavingsAccountFactory();
                yield factory.create(input.getCurrency(), input.getInterestRate(), "");
            }
            default -> {
                factory = new ClassicAccountFactory();
                yield factory.create(input.getCurrency(), 0, "");
            }
        };

        try {
            account.setPlan(Bank.getInstance()
                    .getUser(input.getEmail()).getPlan());
            Bank.getInstance().getCashback().subscribe(account);
            Bank.getInstance().getUser(input.getEmail()).getAccounts().add(account);
            ObjectNode out = Output.getInstance().mapper.createObjectNode()
                    .put("description", "New account created")
                    .put("timestamp", input.getTimestamp());
            Bank.getInstance().getUser(input.getEmail()).
                    getTransactions().addTransaction(out, account.getIBAN());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
