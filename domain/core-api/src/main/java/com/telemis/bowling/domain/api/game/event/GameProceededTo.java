package com.telemis.bowling.domain.api.game.event;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.telemis.bowling.domain.api.common.ProceededTo;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 04/07/14
 */
public class GameProceededTo extends ProceededTo implements Serializable {

    private static final long serialVersionUID = -5359218483040263214L;

    private String gameIdentifier;

    public GameProceededTo(String gameIdentifier, String exitedStatus, String enteredStatus) {
        super(exitedStatus, enteredStatus);
        this.gameIdentifier = checkNotNull(gameIdentifier, "gameIdentifier cannot be null");
    }

    public String getEnteredStatus() {
        return (Strings.isNullOrEmpty(this.enteredStatus)) ? null : this.enteredStatus;
    }

    public String getExitedStatus() {
        return (Strings.isNullOrEmpty(this.exitedStatus)) ? null : this.exitedStatus;
    }

    public String getGameIdentifier() {
        return gameIdentifier;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("gameIdentifier", gameIdentifier)
                .add("enteredStatus", enteredStatus)
                .add("exitedStatus", exitedStatus)
                .toString();
    }
}
