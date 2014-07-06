package com.telemis.bowling.domain.api;

import java.io.Serializable;

/**
 * All metadata informations that can be linked to a Command.
 */
public final class Metadatas implements Serializable {

    private static final long serialVersionUID = -6761072250482755019L;

    private Metadatas() {
    }

    public static final String USER_ID = "userId";

    public static final String COMMAND_UUID = "commandUuid";

    public static final String COMMAND_NAME = "commandName";
}
