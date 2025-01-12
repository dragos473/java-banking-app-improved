package org.poo.main.objects.Actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;
import org.poo.main.objects.Output;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Account;
import org.poo.main.objects.accounts.ClassicAccount;
import org.poo.main.objects.accounts.SavingsAccount;

import java.awt.image.PackedColorModel;

public class WithdrawSavings implements Action {
    @Override
    public void execute(final CommandInput input) {
        ObjectNode output = Output.getInstance().mapper.createObjectNode()
                .put("timestamp", input.getTimestamp());
        for (User u : Bank.getInstance().getUsers()) {
            if (u.getAccount(input.getAccount()) == null) {
                continue;
            }
            if(u.getAge() < 21) {
                output.put("description",
                                "You don't have the minimum age required.");
                u.getTransactions().addTransaction(output,
                        u.getAccount(input.getAccount()).getIBAN());
                return;
            }
            try {
                SavingsAccount acc = (SavingsAccount) u.getAccount(input.getAccount());
                double rate = Bank.getInstance()
                        .getExchange().getExchangeRate(input.getCurrency(), acc.getCurrency());
                double amount = input.getAmount() * rate;
                acc.pay(amount);
            } catch (Exception e) {
                if (e.getMessage().equals("Insufficient funds")) {
                    output.put("description", "Insufficient funds");
                    u.getTransactions().addTransaction(output,
                            u.getAccount(input.getAccount()).getIBAN());
                    return;
                }
                output.put("description", "Account is not of type savings.");
                u.getTransactions().addTransaction(output,
                        u.getAccount(input.getAccount()).getIBAN());
                return;
            }
            for (Account a : u.getAccounts()) {
                try {
                    ClassicAccount acc = (ClassicAccount) a;
                    if(!acc.getCurrency().equals(input.getCurrency())) {
                        continue;
                    }
                    acc.deposit(input.getAmount());
                    return;

                } catch (Exception e) {
                    continue;
                }
            }
            output.put("description", "You do not have a classic account.");
            u.getTransactions().addTransaction(output,
                    u.getAccount(input.getAccount()).getIBAN());
            return;
        }
        output.put("description", "Account not found");
        Output.getInstance().output.add(output);
    }

}
