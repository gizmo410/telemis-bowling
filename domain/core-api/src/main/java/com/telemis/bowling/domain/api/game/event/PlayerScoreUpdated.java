package com.telemis.bowling.domain.api.game.event;

import com.google.common.base.Objects;
import com.telemis.bowling.domain.api.IsEntityBuilder;
import com.telemis.bowling.domain.api.game.GamePlayer;

import java.io.Serializable;

/**
 * @since 07/07/14
 */
public class PlayerScoreUpdated implements Serializable {

    private static final long serialVersionUID = -4015341457937846387L;

    private String gameIdentifier;
    private GamePlayer previousPlayer;
    private GamePlayer nextPlayer;

    protected PlayerScoreUpdated() {
    }

    PlayerScoreUpdated(final Builder builder) {
        gameIdentifier = builder.gameIdentifier;
        previousPlayer = builder.previousPlayer;
        nextPlayer = builder.nextPlayer;
    }

    public String getGameIdentifier() {
        return gameIdentifier;
    }

    public GamePlayer getPreviousPlayer() {
        return previousPlayer;
    }

    public GamePlayer getNextPlayer() {
        return nextPlayer;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("gameIdentifier", gameIdentifier)
                .add("previousPlayer", previousPlayer)
                .add("nextPlayer", nextPlayer)
                .toString();
    }

    public static class Builder implements IsEntityBuilder<PlayerScoreUpdated> {

        private String gameIdentifier;
        private GamePlayer previousPlayer;
        private GamePlayer nextPlayer;

        Builder() {
        }

        public Builder withGameIdentifier(final String gameIdentifier) {
            this.gameIdentifier = gameIdentifier;
            return this;
        }

        public Builder withPreviousPlayers(final GamePlayer previousPlayer) {
            this.previousPlayer = previousPlayer;
            return this;
        }

        public Builder withNextPlayer(final GamePlayer nextPlayer) {
            this.nextPlayer = nextPlayer;
            return this;
        }

        @Override
        public PlayerScoreUpdated build() {
            return new PlayerScoreUpdated(this);
        }
    }
}
