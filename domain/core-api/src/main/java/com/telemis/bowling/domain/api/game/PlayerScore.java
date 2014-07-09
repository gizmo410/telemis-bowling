package com.telemis.bowling.domain.api.game;

import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 09/07/14
 */
public class PlayerScore implements Serializable, Comparable<PlayerScore> {

    private static final long serialVersionUID = -5563539340084511951L;

    private String playerName;
    private Integer playerScore;

    protected PlayerScore() {
    }

    public PlayerScore(final String playerName, final Integer playerScore) {
        this.playerName = checkNotNull(playerName, "player name cannot be null");
        this.playerScore = checkNotNull(playerScore, "player score cannot be null");
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    @Override
    public int compareTo(final PlayerScore objectToCompareTo) {
        if (objectToCompareTo == null) {
            return 1;
        }
        return playerScore.compareTo(objectToCompareTo.getPlayerScore());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("playerName", playerName)
                .add("playerScore", playerScore)
                .toString();
    }
}
