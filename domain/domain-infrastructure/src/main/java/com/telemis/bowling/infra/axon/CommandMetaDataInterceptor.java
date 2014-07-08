package com.telemis.bowling.infra.axon;

import com.telemis.bowling.domain.api.Metadatas;
import com.google.common.collect.Maps;
import org.axonframework.commandhandling.CommandDispatchInterceptor;
import org.axonframework.commandhandling.CommandMessage;

import java.util.HashMap;
import java.util.UUID;

/**
 * Adds metadata information about the command being executed, so it is possible to track changes back to the user's intention.
 *
 * @author Christophe De Blende
 * @since 13/06/14.
 */
public class CommandMetaDataInterceptor implements CommandDispatchInterceptor {

    @Override
    public CommandMessage<?> handle(CommandMessage<?> commandMessage) {
        HashMap<String, Object> metaDataMap = Maps.newHashMap(commandMessage.getMetaData());
        metaDataMap.put(Metadatas.COMMAND_NAME, commandMessage.getCommandName());
        metaDataMap.put(Metadatas.COMMAND_UUID, UUID.randomUUID().toString());
        return commandMessage.withMetaData(metaDataMap);
    }
}
