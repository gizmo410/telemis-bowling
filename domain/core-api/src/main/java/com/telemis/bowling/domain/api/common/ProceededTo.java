package com.telemis.bowling.domain.api.common;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Event that states that the procedure state (ie state diagram) has proceeded to status X.
 *
 * @author bonamich on 23/05/14.
 */
public abstract class ProceededTo implements Serializable {

    protected String exitedStatus;
    protected String enteredStatus;

    public ProceededTo(String exitedStatus, String enteredStatus) {
        this.exitedStatus = checkNotNull(exitedStatus, "exitedStatus is null");
        this.enteredStatus = checkNotNull(enteredStatus, "enteredStatus is null");
    }

    public String getEnteredStatusStr() {
        return enteredStatus;
    }

    public String getExitedStatusStr() {
        return exitedStatus;
    }

    @Override
    public String toString() {
        return "ProceededTo{" +
                "exitedStatus='" + exitedStatus + '\'' +
                ", enteredStatus='" + enteredStatus + '\'' +
                '}';
    }

}
