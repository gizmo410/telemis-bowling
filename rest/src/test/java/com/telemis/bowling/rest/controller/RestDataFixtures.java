package com.telemis.bowling.rest.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Throwables;
import com.telemis.bowling.domain.api.game.command.CreateGame;

public class RestDataFixtures {

    // jackson json (de)serialization ? https://github.com/FasterXML/jackson-databind/
    static ObjectMapper mapper = new ObjectMapper();

    private RestDataFixtures() {
    }

    // Game
    //===========================================

    public static final int NUMBER_OF_PLAYERS = 1;
    public static final CreateGame CREATE_GAME = new CreateGame(NUMBER_OF_PLAYERS);


    //  JSON MAPPING CONFIGURATION + FIXTURES
    //=======================================

    public static String standardCreateGameJSON;

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // to prevent exception when encountering unknown property:
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // to allow coercion of JSON empty String ("") to null Object value:

        try {
            standardCreateGameJSON = mapper.writeValueAsString(CREATE_GAME);
        } catch (Exception e) {
            Throwables.propagate(e);
        }
    }


}
