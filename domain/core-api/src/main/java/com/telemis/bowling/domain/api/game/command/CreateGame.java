package com.telemis.bowling.domain.api.game.command;

import com.google.common.base.Objects;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.axonframework.domain.IdentifierFactory;

import java.io.Serializable;

/**
 * @since 03/07/14
 */
public class CreateGame implements Serializable {

    private static final long serialVersionUID = 1353352370529653561L;

    @TargetAggregateIdentifier
    private String identifier = IdentifierFactory.getInstance().generateIdentifier();
    private int numberOfPlayers;

    // Needed by Axon
    protected CreateGame() {
    }

    public CreateGame(final String identifier, final int numberOfPlayers) {
        this.identifier = identifier;
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("identifier", identifier)
                .add("numberOfPlayers", numberOfPlayers)
                .toString();
    }
}
