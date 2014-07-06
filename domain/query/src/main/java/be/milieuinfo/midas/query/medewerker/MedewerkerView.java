package be.milieuinfo.midas.query.medewerker;

import be.milieuinfo.midas.query.IsViewBuilder;
import be.milieuinfo.midas.query.support.Joiners;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static be.milieuinfo.midas.query.common.ColumnDefinition.JSON;
import static be.milieuinfo.midas.query.common.TypeDefs.POSTGRES_JSON_OBJECT;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * @since 02/07/14
 */
@Entity
@Table(name = "medewerker_view", schema = "midas_view")
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public class MedewerkerView {

    @Id
    @Column(name = "unieke_sleutel")
    private String persoonId;

    @Column(name = "voornaam")
    private String voornaam;

    @Column(name = "achternaam")
    private String achternaam;

    @Column(name = "rollen", columnDefinition = JSON)
    @Type(type = POSTGRES_JSON_OBJECT, parameters = {
            @org.hibernate.annotations.Parameter(name = "resultClass", value = "java.lang.String"),
            @org.hibernate.annotations.Parameter(name = "collectionType", value = "java.util.Set")})
    private Set<String> rollen;

    /**
     * Compiled field for full-text searching.
     */
    @JsonIgnore
    @Column(name = "full_text", columnDefinition = "TEXT")
    private String fulltxt;

    @Version
    private int version;

    // Needed by hibernate
    protected MedewerkerView() {
    }


    public MedewerkerView(final Builder builder) {
        persoonId = builder.persoonId;
        voornaam = builder.voornaam;
        achternaam = builder.achternaam;
    }

    public String getPersoonId() {
        return persoonId;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @PrePersist
    @PreUpdate
    public void rebuildSearchfield() {
        List<String> fulltxtList = ImmutableList.of(voornaam, achternaam);
        fulltxt = Joiners.SPACE.join(fulltxtList);
    }

    public static final class Builder implements IsViewBuilder<String, MedewerkerView> {

        private String persoonId;
        private String voornaam;
        private String achternaam;

        // Do not expose
        private Builder() {
        }

        public Builder voornaam(final String voornaam) {
            this.voornaam = voornaam;
            return this;
        }

        public Builder achternaam(final String achternaam) {
            this.achternaam = achternaam;
            return this;
        }

        @Override
        public IsViewBuilder<String, MedewerkerView> withId(final String identifier) {
            this.persoonId = identifier;
            return this;
        }

        @Override
        public MedewerkerView build() {
            return new MedewerkerView(this);
        }
    }

}
