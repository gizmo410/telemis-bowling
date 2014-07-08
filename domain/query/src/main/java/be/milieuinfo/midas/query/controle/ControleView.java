package be.milieuinfo.midas.query.controle;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.collect.ImmutableSet;
import com.telemis.bowling.infra.hibernate.PostgresJsonUserType;
import com.telemis.bowling.query.IsViewBuilder;
import com.telemis.bowling.query.common.ColumnDefinition;
import com.telemis.bowling.query.common.TypeDefs;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * @since 25/06/14
 */
@Entity
@Table(name = "controle_view", schema = "midas_view")
@org.hibernate.annotations.TypeDefs({@TypeDef(name = TypeDefs.POSTGRES_JSON_OBJECT, typeClass = PostgresJsonUserType.class)})
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public class ControleView {

    @Id
    @Column(name = "unieke_sleutel")
    private String identifier;

    @Column(name = "uitvoerend_toezichthouder")
    private String uitvoerendToezichthouder;

    @Column(name = "kenmerk")
    private String kenmerk;

    @Column(name = "onderwerp")
    private String onderwerp;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "besluit")
    private String besluit;

    @Column(name = "alleen_besluit")
    private Boolean alleenBesluit;

    @Column(name = "status")
    private String status;

    @Column(name = "programma_onderdelen", columnDefinition = ColumnDefinition.JSON)
    @Type(type = TypeDefs.POSTGRES_JSON_OBJECT, parameters = {
            @Parameter(name = "resultClass", value = "java.lang.String"),
            @Parameter(name = "collectionType", value = "java.util.Set")})
    private Set<String> programmaOnderdelen;

    @Column(name = "intern_begeleider", columnDefinition = ColumnDefinition.JSON)
    @Type(type = TypeDefs.POSTGRES_JSON_OBJECT, parameters = {
            @Parameter(name = "resultClass", value = "java.lang.String"),
            @Parameter(name = "collectionType", value = "java.util.Set")})
    private Set<String> internBegeleiders;

    @Version
    private int version;

    // Needed by hibernate
    protected ControleView() {
    }

    public ControleView(final Builder builder) {
        identifier = builder.identifier;
        uitvoerendToezichthouder = builder.uitvoerendToezichthouder;
        kenmerk = builder.kenmerk;
        onderwerp = builder.onderwerp;
        omschrijving = builder.omschrijving;
        besluit = builder.besluit;
        alleenBesluit = builder.alleenBesluit;
        status = builder.status;
        programmaOnderdelen = builder.programmaOnderdelen;
        internBegeleiders = builder.internBegeleiders;
    }

    public String getIdentifier() {
        return identifier;
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
        return ImmutableSet.copyOf(programmaOnderdelen);
    }

    public Set<String> getInternBegeleiders() {
        return ImmutableSet.copyOf(internBegeleiders);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder implements IsViewBuilder<String, ControleView> {

        private String identifier;
        private String uitvoerendToezichthouder;
        private String kenmerk;
        private String onderwerp;
        private String omschrijving;
        private String besluit;
        private Boolean alleenBesluit;
        private String status;
        private Set<String> programmaOnderdelen;
        private Set<String> internBegeleiders;

        // Do not expose
        private Builder() {
        }

        public Builder uitvoerendToezichthouder(final String uitvoerendToezichthouder) {
            this.uitvoerendToezichthouder = uitvoerendToezichthouder;
            return this;
        }

        public Builder kenmerk(final String kenmerk) {
            this.kenmerk = kenmerk;
            return this;
        }

        public Builder onderwerp(final String onderwerp) {
            this.onderwerp = onderwerp;
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

        public Builder internBegeleiders(final Set<String> internBegeleider) {
            this.internBegeleiders = internBegeleider;
            return this;
        }

        @Override
        public IsViewBuilder<String, ControleView> withId(final String uniekeSleutel) {
            this.identifier = uniekeSleutel;
            return this;
        }

        @Override
        public ControleView build() {
            return new ControleView(this);
        }
    }

}
