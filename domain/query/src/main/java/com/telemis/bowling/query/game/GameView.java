package com.telemis.bowling.query.game;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.telemis.bowling.domain.api.game.GamePlayer;
import com.telemis.bowling.domain.api.game.GameStatus;
import com.telemis.bowling.infra.hibernate.PostgresJsonUserType;
import com.telemis.bowling.query.IsViewBuilder;
import com.telemis.bowling.query.common.TypeDefs;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.telemis.bowling.query.common.ColumnDefinition.JSON;

/**
 * @since 08/07/14
 */
@Entity
@Table(name = "game_view", schema = "bowling")
@org.hibernate.annotations.TypeDefs({@TypeDef(name = TypeDefs.POSTGRES_JSON_OBJECT, typeClass = PostgresJsonUserType.class)})
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public class GameView {

    @Id
    @Column(name = "identifier")
    private String identifier;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @Column(name = "players", columnDefinition = JSON)
    @Type(type = TypeDefs.POSTGRES_JSON_OBJECT, parameters = {
            @org.hibernate.annotations.Parameter(name = "resultClass", value = "com.telemis.bowling.domain.api.game.GamePlayer"),
            @org.hibernate.annotations.Parameter(name = "collectionType", value = "java.util.Set")})
    private Set<GamePlayer> players;

    @Column(name = "current_player_name")
    private String currentPlayerName;

    @Version
    private int version;

    // Needed by hibernate
    protected GameView() {
    }

    GameView(final Builder builder) {
        identifier = builder.identifier;
        status = builder.status;
        players = builder.players;
        currentPlayerName = builder.currentPlayerName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(final GameStatus status) {
        this.status = status;
    }

    public SortedSet<GamePlayer> getPlayers() {
        return new TreeSet<>(players);
    }

    public void setPlayers(final Set<GamePlayer> players) {
        this.players = players;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public void setCurrentPlayerName(final String currentPlayerName) {
        this.currentPlayerName = currentPlayerName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder implements IsViewBuilder<String, GameView> {

        private String identifier;
        private GameStatus status;
        private Set<GamePlayer> players;
        private String currentPlayerName;

        Builder() {
        }

        public Builder withStatus(final GameStatus status) {
            this.status = status;
            return this;
        }

        public Builder withPlayers(final Set<GamePlayer> players) {
            this.players = players;
            return this;
        }

        public Builder withCurrentPlayerName(final String currentPlayerName) {
            this.currentPlayerName = currentPlayerName;
            return this;
        }

        @Override
        public IsViewBuilder<String, GameView> withId(final String identifier) {
            this.identifier = identifier;
            return this;
        }

        @Override
        public GameView build() {
            return new GameView(this);
        }
    }

}
