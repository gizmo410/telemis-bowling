package be.milieuinfo.midas.infra.axon;

import com.google.common.collect.Maps;
import org.axonframework.auditing.AuditDataProvider;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.domain.MetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @since 16/06/14
 */
public class CommandMetadataAuditDataProvider implements AuditDataProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger("__AUDIT__");

    @Override
    public Map<String, Object> provideAuditDataFor(CommandMessage<?> command) {
        final MetaData commandMetaData = command.getMetaData();
        LOGGER.debug("commandMessage.metadata = {}", commandMetaData);
        final Map<String, Object> metadata = Maps.newHashMap();
        metadata.putAll(commandMetaData);
        return metadata;
    }

}
