package com.telemis.bowling.domain.api.game;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.SortedSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 03/07/14
 */
public class PlayerFrame implements Serializable {

    private static final long serialVersionUID = 7536172485912903570L;

    private int frameNumber;
    private SortedSet<Integer> frameThrows;

    protected PlayerFrame() {
    }

    public PlayerFrame(final int frameNumber, final SortedSet<Integer> frameThrows) {
        checkArgument(frameNumber > 0);
        this.frameNumber = frameNumber;
        this.frameThrows = checkNotNull(frameThrows, "throws list cannot be null");
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public SortedSet<Integer> getFrameThrows() {
        return frameThrows;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("frameNumber", frameNumber)
                .add("frameThrows", frameThrows)
                .toString();
    }
}
