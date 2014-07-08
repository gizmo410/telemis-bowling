package com.telemis.bowling.domain.api.game.command;

import com.google.common.base.Objects;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 06/07/14
 */
public class RegisterPlayerThrow implements Serializable {

    private static final long serialVersionUID = -5124998863543907835L;

    @TargetAggregateIdentifier
    private String gameId;

    private int numberOfSkittlesDown;

    // Needed by Axon
    protected RegisterPlayerThrow() {
    }

    public RegisterPlayerThrow(final String gameId, final int numberOfSkittlesDown) {
        checkArgument(numberOfSkittlesDown >= 0 && numberOfSkittlesDown < 16, "Number of skittles is between 0 and 15");
        this.gameId = checkNotNull(gameId, "Game identifier cannot be null");
        this.numberOfSkittlesDown = numberOfSkittlesDown;
    }

    public String getGameId() {
        return gameId;
    }

    public int getNumberOfSkittlesDown() {
        return numberOfSkittlesDown;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("gameId", gameId)
                .add("numberOfSkittlesDown", numberOfSkittlesDown)
                .toString();
    }
}
