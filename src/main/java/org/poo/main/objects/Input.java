package org.poo.main.objects;

import org.poo.fileio.ObjectInput;

public class Input {
    private static Input instance;
    public ObjectInput inputData;

    public Input(final ObjectInput inputData) {
        this.inputData = inputData;
    }
    /**
     * singleton initializer, that only allows one instance of the JSON input format
     * @return
     */
    public static Input getInstance(final ObjectInput inputData) {
        if (instance == null) {
            instance = new Input(inputData);
        }
        return instance;
    }
    /**
     * Method to delete the instance of the Input, preventing tests from overlapping
     */
    public static void deleteInstance() {
        instance = null;
    }
}
