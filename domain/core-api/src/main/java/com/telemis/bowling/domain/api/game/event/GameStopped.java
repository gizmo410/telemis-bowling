package com.telemis.bowling.domain.api.game.event;

import com.google.common.base.Objects;
import com.telemis.bowling.domain.api.game.GameStatus;
import com.telemis.bowling.domain.api.game.PlayerScore;

import java.io.Serializable;
import java.util.List;

/**
 * @since 08/07/14
 */
public class GameStopped implements Serializable {

    private static final long serialVersionUID = 6933438225223588263L;

    private String gameIdentifier;
    private GameStatus gameStatus;
    private List<PlayerScore> playerScores;

    protected GameStopped() {
    }

    public GameStopped(final String gameIdentifier, final GameStatus gameStatus, final List<PlayerScore> playerScores) {
        this.gameIdentifier = gameIdentifier;
        this.gameStatus = gameStatus;
        this.playerScores = playerScores;
    }

    public String getGameIdentifier() {
        return gameIdentifier;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public List<PlayerScore> getPlayerScores() {
        return playerScores;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("gameIdentifier", gameIdentifier)
                .add("gameStatus", gameStatus)
                .add("playerScores", playerScores)
                .toString();
    }
}
