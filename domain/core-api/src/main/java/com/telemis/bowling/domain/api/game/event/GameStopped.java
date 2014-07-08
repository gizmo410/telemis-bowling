package com.telemis.bowling.domain.api.game.event;

import com.google.common.base.Objects;
import com.telemis.bowling.domain.api.game.GameStatus;

import java.io.Serializable;

/**
 * @since 08/07/14
 */
public class GameStopped implements Serializable {

    private static final long serialVersionUID = 6933438225223588263L;

    private String gameIdentifier;
    private GameStatus gameStatus;

    protected GameStopped() {
    }

    public GameStopped(final String gameIdentifier, final GameStatus gameStatus) {
        this.gameIdentifier = gameIdentifier;
        this.gameStatus = gameStatus;
    }

    public String getGameIdentifier() {
        return gameIdentifier;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("gameIdentifier", gameIdentifier)
                .add("gameStatus", gameStatus)
                .toString();
    }
}
