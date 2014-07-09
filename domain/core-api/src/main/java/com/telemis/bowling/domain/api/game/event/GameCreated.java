package com.telemis.bowling.domain.api.game.event;

import com.google.common.base.Objects;
import com.telemis.bowling.domain.api.game.GamePlayer;

import java.io.Serializable;
import java.util.List;

/**
 * @since 03/07/14
 */
public class GameCreated implements Serializable {

    private static final long serialVersionUID = 6298798250268753713L;

    private String identifier;
    private List<GamePlayer> players;
    private GamePlayer currentPlayer;

    protected GameCreated() {

    }

    public GameCreated(final String identifier,
                       final List<GamePlayer> players,
                       final GamePlayer currentPlayer) {
        this.identifier = identifier;
        this.players = players;
        this.currentPlayer = currentPlayer;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public String getIdentifier() {
        return identifier;
    }

    public GamePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("identifier", identifier)
                .add("players", players)
                .add("currentPlayer", currentPlayer)
                .toString();
    }
}
