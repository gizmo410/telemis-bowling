package com.telemis.bowling.domain.api.common;

import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Command that moves the state diagram forward i.e. changes the status to specified value.
 * Can only be sent by the state diagram engine itself, and not by a third party.
 *
 * @author bonamich on 23/05/14.
 */
public abstract class ProceedTo implements Serializable {

    protected String newStatus;

    protected ProceedTo(String newStatus) {
        checkNotNull(newStatus, "newStatus is null");
        this.newStatus = newStatus;
    }

    @SuppressWarnings("unused")
    public String getNewStatusStr() {
        return newStatus;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("newStatus", newStatus)
                .toString();
    }
}
