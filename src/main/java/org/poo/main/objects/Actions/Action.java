package org.poo.main.objects.Actions;

import org.poo.fileio.CommandInput;

public interface Action {
    /**
     * Executes the action at hand
     * @param input the input needed for the action
     */
    void execute(final CommandInput input);
}
