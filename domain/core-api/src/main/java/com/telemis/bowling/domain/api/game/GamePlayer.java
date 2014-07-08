package com.telemis.bowling.domain.api.game;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.telemis.bowling.domain.api.FrameThrow;
import com.telemis.bowling.domain.api.IsEntityBuilder;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 03/07/14
 */
public class GamePlayer implements Serializable, Comparable<GamePlayer> {

    private static final long serialVersionUID = -2514684255595758147L;

    private static final int MAX_NUMBER_OF_FRAMES = 5;
    private static final int MAX_NUMBER_OF_THROWS_PER_FRAME = 3;

    private Integer playerNumber;
    private String playerName;
    // SortedSet because we want te get the set ordered in the query model
    private SortedSet<PlayerFrame> playerFrames;

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

    public void registerScore(final int numberOfSkittlesDown) {
        final PlayerFrame currentFrame = getCurrentFrame();

        if (playerFrames.size() == MAX_NUMBER_OF_FRAMES
                || currentFrame.getFrameThrows().size() < MAX_NUMBER_OF_THROWS_PER_FRAME) {
            currentFrame.addThrow(numberOfSkittlesDown);
        } else {
            final PlayerFrame newFrame = createAndAddNewFrame(currentFrame);
            newFrame.addThrow(numberOfSkittlesDown);
        }

    }

    private PlayerFrame createAndAddNewFrame(final PlayerFrame currentFrame) {
        final int newFrameNumber = computeNewFrameNumber(currentFrame);
        final PlayerFrame newFrame = new PlayerFrame(newFrameNumber, new TreeSet<FrameThrow>());
        playerFrames.add(newFrame);
        return newFrame;
    }

    private int computeNewFrameNumber(final PlayerFrame currentFrame) {
        return currentFrame.getFrameNumber() + 1;
    }

    private PlayerFrame getCurrentFrame() {
        if (playerFrames.isEmpty()) {
            return createAndAddNewFrameToTheList();
        }
        return getLastFrame();
    }

    private PlayerFrame getLastFrame() {
        final ImmutableList<PlayerFrame> frameList = ImmutableList.copyOf(playerFrames);
        final int lastFrameIndex = frameList.size() - 1;
        return frameList.get(lastFrameIndex);
    }

    private PlayerFrame createAndAddNewFrameToTheList() {
        final PlayerFrame newPlayerFrame = new PlayerFrame(1, new TreeSet<FrameThrow>());
        playerFrames.add(newPlayerFrame);
        return newPlayerFrame;
    }

    public static class Builder implements IsEntityBuilder<GamePlayer> {

        private int playerNumber;
        private String playerName;
        private SortedSet<PlayerFrame> playerFrames;

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

        public Builder withPlayerFrames(final SortedSet<PlayerFrame> playerFrames) {
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

    @Override
    public int compareTo(final GamePlayer objectToCompareTo) {
        if (objectToCompareTo == null) {
            return 1;
        }
        return playerNumber.compareTo(objectToCompareTo.playerNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(playerNumber);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final GamePlayer other = (GamePlayer) obj;
        return Objects.equal(this.playerNumber, other.playerNumber);
    }
}
