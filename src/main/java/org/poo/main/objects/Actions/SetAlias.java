package org.poo.main.objects.Actions;

import org.poo.fileio.CommandInput;
import org.poo.main.objects.Bank;

public class SetAlias implements Action {
    /**
     * Sets the alias of an account
     * @param input the input needed for the action
     */
    @Override
    public void execute(final CommandInput input) {
        try {
            Bank.getInstance().getUser(input.getEmail())
                    .getAccount(input.getAccount())
                    .setAlias(input.getAlias());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
