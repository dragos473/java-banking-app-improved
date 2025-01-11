package org.poo.main.objects.Actions;

import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;

public class PrintTransactions implements Action {
    /**
     * Prints the transactions of a user
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        try {
            Bank.getInstance().getUser(input.getEmail())
                    .getTransactions().printTransactions(input.getTimestamp());
        } catch (Exception e) {
            e.printStackTrace();
    }
}
}
