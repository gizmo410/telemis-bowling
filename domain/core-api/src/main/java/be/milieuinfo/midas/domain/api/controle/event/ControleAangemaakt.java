package be.milieuinfo.midas.domain.api.controle.event;

import com.telemis.bowling.domain.api.IsEntityBuilder;
import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Set;

/**
 * @since 16/06/14
 */
public class ControleAangemaakt implements Serializable {

    private static final long serialVersionUID = -2649244842451840154L;

    private String controleId;

    private String uitvoerendToezichthouder;
    private String kenmerk;
    private String onderwerp;
    private String omschrijving;
    private String besluit;
    private Boolean alleenBesluit;
    private String status;
    private Set<String> programmaOnderdelen;
    private Set<String> internBegeleider;

    public ControleAangemaakt(final Builder builder) {
        controleId = builder.controleId;
        uitvoerendToezichthouder = builder.uitvoerendToezichthouder;
        kenmerk = builder.kenmerk;
        onderwerp = builder.onderwerp;
        omschrijving = builder.omschrijving;
        besluit = builder.besluit;
        alleenBesluit = builder.alleenBesluit;
        status = builder.status;
        programmaOnderdelen = builder.programmaOnderdelen;
        internBegeleider = builder.internBegeleider;
        //TODO Validatie...
    }

    public String getControleId() {
        return controleId;
    }

    public String getUitvoerendToezichthouder() {
        return uitvoerendToezichthouder;
    }

    public String getKenmerk() {
        return kenmerk;
    }

    public String getOnderwerp() {
        return onderwerp;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public String getBesluit() {
        return besluit;
    }

    public Boolean getAlleenBesluit() {
        return alleenBesluit;
    }

    public String getStatus() {
        return status;
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
        return Objects.toStringHelper(this)
                .add("controleId", controleId)
                .add("uitvoerendToezichthouder", uitvoerendToezichthouder)
                .add("kenmerk", kenmerk)
                .add("onderwerp", onderwerp)
                .add("omschrijving", omschrijving)
                .add("besluit", besluit)
                .add("alleenBesluit", alleenBesluit)
                .add("status", status)
                .add("programmaOnderdelen", programmaOnderdelen)
                .add("internBegeleider", internBegeleider)
                .toString();
    }

    public static final class Builder implements IsEntityBuilder<ControleAangemaakt> {

        private String controleId;
        private String uitvoerendToezichthouder;
        private String kenmerk;
        private String onderwerp;
        private String omschrijving;
        private String besluit;
        private Boolean alleenBesluit;
        private String status;
        private Set<String> programmaOnderdelen;
        private Set<String> internBegeleider;

        // Do not expose.
        private Builder() {
        }

        public Builder controlId(final String uniekeSleutel) {
            this.controleId = uniekeSleutel;
            return this;
        }

        public Builder uitvoerendToezichthouder(final String uitvoerendToezichthouder) {
            this.uitvoerendToezichthouder = uitvoerendToezichthouder;
            return this;
        }

        public Builder kenmerk(final String kenmerk) {
            this.kenmerk = kenmerk;
            return this;
        }

        public Builder onderwerp(final String onderwerk) {
            this.onderwerp = onderwerk;
            return this;
        }

        public Builder omschrijving(final String omschrijving) {
            this.omschrijving = omschrijving;
            return this;
        }

        public Builder besluit(final String besluit) {
            this.besluit = besluit;
            return this;
        }

        public Builder alleenBesluit(final Boolean alleenBesluit) {
            this.alleenBesluit = alleenBesluit;
            return this;
        }

        public Builder status(final String status) {
            this.status = status;
            return this;
        }

        public Builder programmaOnderdelen(final Set<String> programmaOnderdelen) {
            this.programmaOnderdelen = programmaOnderdelen;
            return this;
        }

        public Builder internBegeleider(final Set<String> internBegeleider) {
            this.internBegeleider = internBegeleider;
            return this;
        }

        @Override
        public ControleAangemaakt build() {
            return new ControleAangemaakt(this);
        }
    }
}
