package be.milieuinfo.midas.domain.api.controle.command;

import com.telemis.bowling.domain.api.IsEntityBuilder;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.axonframework.domain.IdentifierFactory;

import java.io.Serializable;
import java.util.Set;

/**
 * MaakControle command object.
 *
 * @author Yves Hulet
 */
public class MaakControle implements Serializable {

    private static final long serialVersionUID = -7921538589536933668L;

    // Needed by Axon
    protected MaakControle() {
    }

    @TargetAggregateIdentifier
    private String uniekeSleutel = IdentifierFactory.getInstance().generateIdentifier();

    private String uitvoerendToezichthouder;
    private String onderwerp;
    private Boolean alleenBesluit = Boolean.FALSE;
    private Set<String> programmaOnderdelen;
    private Set<String> internBegeleider;

    MaakControle(final Builder builder) {
        this.uitvoerendToezichthouder = builder.uitvoerendToezichthouder;
        this.onderwerp = builder.onderwerp;
        this.programmaOnderdelen = builder.programmaOnderdelen;
        this.internBegeleider = builder.internBegeleider;
    }

    public String getUniekeSleutel() {
        return uniekeSleutel;
    }

    public String getUitvoerendToezichthouder() {
        return uitvoerendToezichthouder;
    }

    public String getOnderwerp() {
        return onderwerp;
    }

    public Boolean getAlleenBesluit() {
        return alleenBesluit;
    }

    public Set<String> getProgrammaOnderdelen() {
        return programmaOnderdelen;
    }

    public Set<String> getInternBegeleider() {
        return internBegeleider;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "MaakControle{" +
                "uniekeSleutel='" + uniekeSleutel + '\'' +
                ", uitvoerendToezichthouder='" + uitvoerendToezichthouder + '\'' +
                ", onderwerp='" + onderwerp + '\'' +
                ", alleenBesluit=" + alleenBesluit +
                ", programmaOnderdelen=" + programmaOnderdelen +
                ", internBegeleider=" + internBegeleider +
                '}';
    }

    /**
     * Builder object voor het MaakControle command object.
     *
     * @author Yves Hulet
     */
    public static final class Builder implements IsEntityBuilder<MaakControle> {

        private String uitvoerendToezichthouder;
        private String onderwerp;
        private Set<String> programmaOnderdelen;
        private Set<String> internBegeleider;

        // Do not expose
        private Builder() {
        }

        public Builder uitvoerendToezichthouder(final String uitvoerendToezichthouder) {
            this.uitvoerendToezichthouder = uitvoerendToezichthouder;
            return this;
        }

        public Builder onderwerp(final String onderwerp) {
            this.onderwerp = onderwerp;
            return this;
        }

        public Builder programmaOnderdelen(final Set<String> programmaOnderdelen) {
            this.programmaOnderdelen = programmaOnderdelen;
            return this;
        }

        public Builder internBegeleiders(final Set<String> internBegeleider) {
            this.internBegeleider = internBegeleider;
            return this;
        }

        @Override
        public MaakControle build() {
            return new MaakControle(this);
        }
    }
}