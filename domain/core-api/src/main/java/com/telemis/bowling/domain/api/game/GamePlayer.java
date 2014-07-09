package com.telemis.bowling.domain.api.game;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.telemis.bowling.domain.api.FrameThrow;
import com.telemis.bowling.domain.api.IsEntityBuilder;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 03/07/14
 */
public class GamePlayer implements Serializable, Comparable<GamePlayer> {

    private static final long serialVersionUID = -2514684255595758147L;

    private static final int MAX_NUMBER_OF_FRAMES = 5;
    private static final int MAX_NUMBER_OF_THROWS_PER_FRAME = 3;

    private Integer number;
    private String name;
    private List<PlayerFrame> frames;

    // Needed by Axon
    protected GamePlayer() {
    }

    public GamePlayer(final Builder builder) {
        this.number = checkNotNull(builder.number, "Player number cannot be null");
        this.name = checkNotNull(builder.name, "Player name cannot be null");
        this.frames = builder.frames;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public List<PlayerFrame> getFrames() {
        return frames;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void registerScore(final int numberOfSkittlesDown) {
        final PlayerFrame currentFrame = getCurrentFrame();

        if (frames.size() == MAX_NUMBER_OF_FRAMES
                || currentFrame.getFrameThrows().size() < MAX_NUMBER_OF_THROWS_PER_FRAME) {
            currentFrame.addThrow(numberOfSkittlesDown);
        } else {
            final PlayerFrame newFrame = createAndAddNewFrame(currentFrame);
            newFrame.addThrow(numberOfSkittlesDown);
        }

    }

    private PlayerFrame createAndAddNewFrame(final PlayerFrame currentFrame) {
        final int newFrameNumber = computeNewFrameNumber(currentFrame);
        final PlayerFrame newFrame = new PlayerFrame(newFrameNumber, new LinkedList<FrameThrow>());
        frames.add(newFrame);
        return newFrame;
    }

    private int computeNewFrameNumber(final PlayerFrame currentFrame) {
        return currentFrame.getNumber() + 1;
    }

    private PlayerFrame getCurrentFrame() {
        if (frames.isEmpty()) {
            return createAndAddNewFrameToTheList();
        }
        return getLastFrame();
    }

    private PlayerFrame getLastFrame() {
        final ImmutableList<PlayerFrame> frameList = ImmutableList.copyOf(frames);
        final int lastFrameIndex = frameList.size() - 1;
        return frameList.get(lastFrameIndex);
    }

    private PlayerFrame createAndAddNewFrameToTheList() {
        final PlayerFrame newPlayerFrame = new PlayerFrame(1, new LinkedList<FrameThrow>());
        frames.add(newPlayerFrame);
        return newPlayerFrame;
    }

    public int computeTotalScore() {
        int totalScore = 0;
        for (PlayerFrame playerFrame : frames) {
            totalScore += computeFrameScore(playerFrame);
        }
        return totalScore;
    }

    private int computeFrameScore(final PlayerFrame frame) {
        int totalFrameScore = 0;
        for (FrameThrow frameThrow : frame.getFrameThrows()) {
            totalFrameScore += computeThrowScore(frameThrow);
        }

        return totalFrameScore;
    }

    private int computeThrowScore(final FrameThrow frameThrow) {
        if (frameThrow == null) {
            return 0;
        }
        final PlayerFrame frame = findFrameForThrow(frameThrow);
        if (frameThrow.isStrike()) {
            return computeScoreForStrike(frame, frameThrow);
        } else if (frameThrow.isSpare()) {
            return computeScoreForSpare(frame, frameThrow);
        } else {
            return frameThrow.getScore();
        }
    }

    private PlayerFrame findFrameForThrow(final FrameThrow frameThrow) {
        for (PlayerFrame frame : frames) {
            if (frame.getFrameThrows().contains(frameThrow)) {
                return frame;
            }
        }
        return null;
    }

    private int computeScoreForStrike(final PlayerFrame frame, @Nullable final FrameThrow frameThrow) {

        if (frameThrow == null) {
            return 0;
        }
        int throwScore = frameThrow.getScore();

        final FrameThrow secondFrameThrow = getThrow(frame, 1);
        final FrameThrow thirdFrameThrow = getThrow(frame, 2);
        final FrameThrow firstThrowOfNextFrame = getThrow(frame, 3);

        return throwScore
                + computeThrowScore(secondFrameThrow)
                + computeThrowScore(thirdFrameThrow)
                + computeThrowScore(firstThrowOfNextFrame);
    }

    private FrameThrow getThrow(final PlayerFrame frame, final int throwIndex) {
        FrameThrow frameThrow = getThrowIfExists(frame, throwIndex);
        if (frameThrow == null) {
            final int frameSize = frame.getFrameThrows().size();
            final PlayerFrame nextFrame = getNextFrameIfExists(frame);
            frameThrow = getThrowIfExists(nextFrame, throwIndex - frameSize);

        }
        return frameThrow;
    }

    private PlayerFrame getNextFrameIfExists(final PlayerFrame frame) {
        final int frameIndex = frames.indexOf(frame);
        final int frameIndexSizes = frames.size() - 1;
        return (frameIndexSizes > frameIndex) ? frames.get(frameIndex + 1) : null;
    }

    private FrameThrow getThrowIfExists(@Nullable final PlayerFrame frame, final int throwIndex) {
        if (frame == null) {
            return null;
        }
        final int throwsIndexSizes = frame.getFrameThrows().size() - 1;
        return (throwsIndexSizes >= throwIndex) ? frame.getFrameThrows().get(throwIndex) : null;
    }

    private int computeScoreForSpare(final PlayerFrame frame, FrameThrow frameThrow) {
        if (frameThrow == null) {
            return 0;
        }
        int throwScore = frameThrow.getScore();

        final int throwIndex = frame.getFrameThrows().indexOf(frameThrow);
        final FrameThrow secondFrameThrow = getThrow(frame, throwIndex + 1);
        final FrameThrow thirdFrameThrow = getThrow(frame, throwIndex + 2);

        return throwScore
                + computeThrowScore(secondFrameThrow)
                + computeThrowScore(thirdFrameThrow);
    }

    public static class Builder implements IsEntityBuilder<GamePlayer> {

        private int number;
        private String name;
        private List<PlayerFrame> frames;

        Builder() {

        }

        public Builder withNumber(final int playerNumber) {
            this.number = playerNumber;
            return this;
        }

        public Builder withName(final String playerName) {
            this.name = playerName;
            return this;
        }

        public Builder withFrames(final List<PlayerFrame> playerFrames) {
            this.frames = playerFrames;
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
                .add("number", number)
                .add("name", name)
                .add("frames", frames)
                .toString();
    }

    @Override
    public int compareTo(final GamePlayer objectToCompareTo) {
        if (objectToCompareTo == null) {
            return 1;
        }
        return number.compareTo(objectToCompareTo.number);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
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
        return Objects.equal(this.number, other.number);
    }
}
