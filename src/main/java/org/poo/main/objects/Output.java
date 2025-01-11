package org.poo.main.objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Output {
    private static Output instance;
    public ObjectMapper mapper;
    public ArrayNode output;

    public Output() {
        mapper = new ObjectMapper();
        output = mapper.createArrayNode();
    }
    /**
     * singleton initializer, that only allows one instance of the JSON output format
     * @return
     */
    public static Output getInstance() {
        if (instance == null) {
            instance = new Output();
        }
        return instance;
    }
    /**
     * Method to delete the instance of the Output, preventing tests from overlapping
     */
    public static void deleteInstance() {
        instance = null;

    }
}
