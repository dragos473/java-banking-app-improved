package org.poo.main.objects;

import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;
import org.poo.main.objects.Actions.ActionMap;

public class Workflow {
    /**
     * This method executes all of the commands
     */
    public void runBank() {
        CommandInput[] input = Input.getInstance(new ObjectInput()).inputData.getCommands();
        ActionMap actionMap = new ActionMap();

        for (CommandInput commandInput : input) {
            try {
                actionMap.execute(commandInput);
            } catch (NoSuchMethodException e) {
                System.out.println("No such method: " + commandInput.getCommand());
            }
        }
    }

}
