package com.telemis.bowling.domain.api.game.command;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.telemis.bowling.domain.api.common.ProceedTo;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 04/07/14
 */
public class GameProceedTo extends ProceedTo implements Serializable {

    private static final long serialVersionUID = 1272565547686272817L;

    private String gameIdentifier;

    /**
     * Name of property to get the status.
     */
    public final static String NEW_STATUS = "newStatus";

    /**
     * Used by drools.
     *
     * @param newStatus
     */
    @SuppressWarnings("unused")
    public GameProceedTo(String gameIdentifier, String newStatus) {
        super(newStatus);
        this.gameIdentifier = gameIdentifier;
    }

    /**
     * Used by native java code.
     *
     * @param newStatus
     */
    public GameProceedTo(String newStatus) {
        super(checkNotNull(newStatus, "newStatus is null"));
    }

    public String getGameIdentifier() {
        return gameIdentifier;
    }

    public String getNewStatus() {
        return (Strings.isNullOrEmpty(this.newStatus)) ? null : this.newStatus;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("gameIdentifier", gameIdentifier)
                .add("newStatus", newStatus)
                .toString();
    }
}
