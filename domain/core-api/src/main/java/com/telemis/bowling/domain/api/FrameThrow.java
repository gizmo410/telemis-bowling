package com.telemis.bowling.domain.api;

import com.google.common.base.Objects;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * @since 06/07/14
 */
public class FrameThrow implements Serializable, Comparable<FrameThrow> {

    private static final long serialVersionUID = 1314146403931303694L;

    private Integer throwNumber;
    private int numberOfSkittlesDown;
    private int score;

    protected FrameThrow() {
    }

    public FrameThrow(final int throwNumber, final int numberOfSkittlesDown, final int score) {
        this.throwNumber = throwNumber;
        this.numberOfSkittlesDown = numberOfSkittlesDown;
        this.score = score;
    }

    public int getThrowNumber() {
        return throwNumber;
    }

    public int getNumberOfSkittlesDown() {
        return numberOfSkittlesDown;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("throwNumber", throwNumber)
                .add("numberOfSkittlesDown", numberOfSkittlesDown)
                .add("score", score)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(throwNumber);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final FrameThrow other = (FrameThrow) obj;
        return Objects.equal(this.throwNumber, other.throwNumber);
    }

    @Override
    public int compareTo(@Nullable final FrameThrow objectToCompareTo) {
        if (objectToCompareTo == null) {
            return 1;
        }
        return throwNumber.compareTo(objectToCompareTo.throwNumber);
    }
}
