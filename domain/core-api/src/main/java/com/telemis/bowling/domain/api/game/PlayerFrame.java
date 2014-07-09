package com.telemis.bowling.domain.api.game;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.telemis.bowling.domain.api.FrameThrow;

import java.io.Serializable;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 03/07/14
 */
public class PlayerFrame implements Serializable, Comparable<PlayerFrame> {

    private static final long serialVersionUID = 7536172485912903570L;

    private Integer number;
    private List<FrameThrow> frameThrows;
    private int numberOfSkittlesLeft;

    protected PlayerFrame() {
    }

    public PlayerFrame(final int number, final List<FrameThrow> frameThrows) {
        checkArgument(number > 0);
        this.number = number;
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

    public int getNumber() {
        return number;
    }

    public List<FrameThrow> getFrameThrows() {
        return frameThrows;
    }

    public void addThrow(final int numberOfSkittlesDown) {
        checkAndUpdateNumberOfSkittlesLeft(numberOfSkittlesDown);
        final int newThrowNumber = computeNewThrowNumber();
        final boolean isStrike = isStrike(newThrowNumber, numberOfSkittlesDown);
        final boolean isSpare = isSpare(newThrowNumber, numberOfSkittlesDown);
        final FrameThrow newThrow = new FrameThrow(newThrowNumber, numberOfSkittlesDown, numberOfSkittlesDown, isStrike, isSpare);
        frameThrows.add(newThrow);
    }

    private boolean isStrike(final int newThrowNumber, final int numberOfSkittlesDown) {
        return (newThrowNumber == 0 && numberOfSkittlesDown == 15);
    }

    private boolean isSpare(final int newThrowNumber, final int numberOfSkittlesDown) {
        return (newThrowNumber > 0 && numberOfSkittlesDown == 15);
    }

    private int computeNewThrowNumber() {
        final FrameThrow lastThrow = getLastThrow();
        int newThrowNumber = 0;
        if (lastThrow != null) {
            newThrowNumber = lastThrow.getThrowNumber() + 1;
        }
        return newThrowNumber;
    }

    private void checkAndUpdateNumberOfSkittlesLeft(final int numberOfSkittlesDown) {
        checkArgument(numberOfSkittlesLeft >= numberOfSkittlesDown, "Number of skittles down cannot be greater than number of skittles left.");
        numberOfSkittlesLeft -= numberOfSkittlesDown;
        if (numberOfSkittlesLeft == 0) {
            numberOfSkittlesLeft = 15;
        }
    }

    private FrameThrow getLastThrow() {
        if (frameThrows.isEmpty()) {
            return null;
        }
        final ImmutableList<FrameThrow> throwList = ImmutableList.copyOf(frameThrows);
        return throwList.get(throwList.size() - 1);
    }

    public boolean doesContainStrikesOrSpares() {
        for (FrameThrow frameThrow : frameThrows) {
            if (frameThrow.isSpare() || frameThrow.isStrike()) {
                return true;
            }
        }
        return false;
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
        final PlayerFrame other = (PlayerFrame) obj;
        return Objects.equal(this.number, other.number);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("number", number)
                .add("frameThrows", frameThrows)
                .toString();
    }

    @Override
    public int compareTo(final PlayerFrame objectToCompareTo) {
        if (objectToCompareTo == null) {
            return 1;
        }
        return number.compareTo(objectToCompareTo.number);
    }
}
