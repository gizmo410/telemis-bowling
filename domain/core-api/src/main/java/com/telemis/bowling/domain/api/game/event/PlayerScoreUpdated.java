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

    // TODO Update this so that we do not need to pass the entire two players

    private String gameIdentifier;
    private GamePlayer player;
    private GamePlayer nextPlayer;

    protected PlayerScoreUpdated() {
    }

    PlayerScoreUpdated(final Builder builder) {
        gameIdentifier = builder.gameIdentifier;
        player = builder.player;
        nextPlayer = builder.nextPlayer;
    }

    public String getGameIdentifier() {
        return gameIdentifier;
    }

    public GamePlayer getPlayer() {
        return player;
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
                .add("player", player)
                .add("nextPlayer", nextPlayer)
                .toString();
    }

    public static class Builder implements IsEntityBuilder<PlayerScoreUpdated> {

        private String gameIdentifier;
        private GamePlayer player;
        private GamePlayer nextPlayer;

        Builder() {
        }

        public Builder withGameIdentifier(final String gameIdentifier) {
            this.gameIdentifier = gameIdentifier;
            return this;
        }

        public Builder withPlayer(final GamePlayer player) {
            this.player = player;
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
