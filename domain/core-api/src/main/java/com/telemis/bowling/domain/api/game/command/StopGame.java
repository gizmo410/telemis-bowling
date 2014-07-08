package com.telemis.bowling.domain.api.game.command;

import com.google.common.base.Objects;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 08/07/14
 */
public class StopGame implements Serializable {

    private static final long serialVersionUID = -3537229669789576804L;

    @TargetAggregateIdentifier
    private String gameId;

    // Needed by Axon
    protected StopGame() {
    }

    public StopGame(final String gameId) {
        this.gameId = checkNotNull(gameId, "Game identifier cannot be null");
    }

    public String getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("gameId", gameId)
                .toString();
    }
}
