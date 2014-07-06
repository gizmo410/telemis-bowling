package be.milieuinfo.midas.domain.api.controle.event;

import com.telemis.bowling.domain.api.common.ProceededTo;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 24/06/14
 */
public class ControleProceededTo extends ProceededTo {

    String controleId;

    public ControleProceededTo(String controleId, String exitedStatus, String enteredStatus) {
        super(exitedStatus, enteredStatus);
        this.controleId = checkNotNull(controleId, "controleId cannot be null");
    }

    public String getEnteredStatus() {
        return (Strings.isNullOrEmpty(this.enteredStatus)) ? null : this.enteredStatus;
    }

    public String getExitedStatus() {
        return (Strings.isNullOrEmpty(this.exitedStatus)) ? null : this.exitedStatus;
    }

    public String getControleId() {
        return controleId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("controleId", controleId)
                .toString();
    }
}
