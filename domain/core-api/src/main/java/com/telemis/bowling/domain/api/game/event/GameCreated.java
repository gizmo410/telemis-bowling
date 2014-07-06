package com.telemis.bowling.domain.api.game.event;

import com.google.common.base.Objects;
import com.telemis.bowling.domain.api.game.GamePlayer;

import java.io.Serializable;
import java.util.Set;

/**
 * @since 03/07/14
 */
public class GameCreated implements Serializable {

    private static final long serialVersionUID = 6298798250268753713L;

    private String identifier;
    private Set<GamePlayer> players;

    protected GameCreated() {

    }

    public GameCreated(final String identifier, final Set<GamePlayer> players) {
        this.identifier = identifier;
        this.players = players;
    }

    public Set<GamePlayer> getPlayers() {
        return players;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("identifier", identifier)
                .add("players", players)
                .toString();
    }
}
