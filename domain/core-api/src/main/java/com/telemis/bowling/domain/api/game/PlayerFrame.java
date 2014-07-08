package com.telemis.bowling.domain.api.game;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.telemis.bowling.domain.api.FrameThrow;

import java.io.Serializable;
import java.util.SortedSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 03/07/14
 */
public class PlayerFrame implements Serializable, Comparable<PlayerFrame> {

    private static final long serialVersionUID = 7536172485912903570L;

    private Integer frameNumber;
    // SortedSet because we want te get the set ordered in the query model
    private SortedSet<FrameThrow> frameThrows;
    private int numberOfSkittlesLeft;

    protected PlayerFrame() {
    }

    public PlayerFrame(final int frameNumber, final SortedSet<FrameThrow> frameThrows) {
        checkArgument(frameNumber > 0);
        this.frameNumber = frameNumber;
        this.frameThrows = checkNotNull(frameThrows, "throws list cannot be null");
        initializeNumberOfSkittles();
    }

    private void initializeNumberOfSkittles() {
        numberOfSkittlesLeft = 15;
        for (FrameThrow frameThrow : frameThrows) {
            final int numberOfSkittlesDown = frameThrow.getNumberOfSkittlesDown();
            checkArgument(numberOfSkittlesLeft < numberOfSkittlesDown, "Total number of skittles down cannot be more than total number of skittles.");
            numberOfSkittlesLeft -= numberOfSkittlesDown;
        }
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public SortedSet<FrameThrow> getFrameThrows() {
        return frameThrows;
    }

    public void addThrow(final int numberOfSkittlesDown) {
        checkAndUpdateNumberOfSkittlesLeft(numberOfSkittlesDown);
        int newThrowNumber = computeNewThrowNumber();
        int score = computeScore(numberOfSkittlesDown);
        final FrameThrow newThrow = new FrameThrow(newThrowNumber, numberOfSkittlesDown, score);
        frameThrows.add(newThrow);
    }

    private int computeNewThrowNumber() {
        final FrameThrow lastThrow = getLastThrow();
        int newThrowNumber = 0;
        if (lastThrow != null) {
            newThrowNumber = lastThrow.getThrowNumber() + 1;
        }
        return newThrowNumber;
    }

    private int computeScore(final int numberOfSkittlesDown) {
        // TODO Fill in to take spares and strikes in account
        return numberOfSkittlesDown;
    }

    private void checkAndUpdateNumberOfSkittlesLeft(final int numberOfSkittlesDown) {
        checkArgument(numberOfSkittlesLeft >= numberOfSkittlesDown, "Number of skittles down cannot be greater than number of skittles left.");
        numberOfSkittlesLeft -= numberOfSkittlesDown;
    }

    private FrameThrow getLastThrow() {
        if (frameThrows.isEmpty()) {
            return null;
        }
        final ImmutableList<FrameThrow> throwList = ImmutableList.copyOf(frameThrows);
        return throwList.get(throwList.size() - 1);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(frameNumber);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PlayerFrame other = (PlayerFrame) obj;
        return Objects.equal(this.frameNumber, other.frameNumber);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("frameNumber", frameNumber)
                .add("frameThrows", frameThrows)
                .toString();
    }

    @Override
    public int compareTo(final PlayerFrame objectToCompareTo) {
        if (objectToCompareTo == null) {
            return 1;
        }
        return frameNumber.compareTo(objectToCompareTo.frameNumber);
    }
}
