package be.milieuinfo.midas.domain.api.controle.command;

import com.telemis.bowling.domain.api.common.ProceedTo;
import com.google.common.base.Strings;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Adapts {@link ProceedTo} for usage with the procedure-specific enumeration {@link ControleProceedTo}.
 *
 * @since 24/06/14
 */
public class ControleProceedTo extends ProceedTo implements Serializable {

    private String controleId;

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
    public ControleProceedTo(String controleId, String newStatus) {
        super(newStatus);
        this.controleId = controleId;
    }

    /**
     * Used by native java code.
     *
     * @param newStatus
     */
    public ControleProceedTo(String newStatus) {
        super(checkNotNull(newStatus, "newStatus is null"));
    }

    public String getControleId() {
        return controleId;
    }

    public String getNewStatus() {
        return (Strings.isNullOrEmpty(this.newStatus)) ? null : this.newStatus;
    }

    @Override
    public String toString() {
        return "ControleProceedTo{" +
                "controleId=" + controleId +
                ", newStatus=" + newStatus +
                "} ";
    }

}
