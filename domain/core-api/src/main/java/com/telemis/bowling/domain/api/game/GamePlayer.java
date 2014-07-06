package com.telemis.bowling.domain.api.game;

import com.google.common.base.Objects;
import com.telemis.bowling.domain.api.IsEntityBuilder;

import java.io.Serializable;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 03/07/14
 */
public class GamePlayer implements Serializable {

    private static final long serialVersionUID = -2514684255595758147L;

    private int playerNumber;
    private String playerName;
    private Set<PlayerFrame> playerFrames;

    // Needed by Axon
    protected GamePlayer() {
    }

    public GamePlayer(final Builder builder) {
        this.playerNumber = checkNotNull(builder.playerNumber, "Player number cannot be null");
        this.playerName = checkNotNull(builder.playerName, "Player name cannot be null");
        this.playerFrames = builder.playerFrames;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Set<PlayerFrame> getPlayerFrames() {
        return playerFrames;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder implements IsEntityBuilder<GamePlayer> {

        private int playerNumber;
        private String playerName;
        private Set<PlayerFrame> playerFrames;

        Builder() {

        }

        public Builder withPlayerNumber(final int playerNumber) {
            this.playerNumber = playerNumber;
            return this;
        }

        public Builder withPlayerName(final String playerName) {
            this.playerName = playerName;
            return this;
        }

        public Builder withPlayerFrames(final Set<PlayerFrame> playerFrames) {
            this.playerFrames = playerFrames;
            return this;
        }

        @Override
        public GamePlayer build() {
            return new GamePlayer(this);
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("playerNumber", playerNumber)
                .add("playerName", playerName)
                .add("playerFrames", playerFrames)
                .toString();
    }
}
