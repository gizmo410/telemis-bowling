package com.telemis.bowling.game;

import com.telemis.bowling.domain.api.game.GamePlayer;
import com.telemis.bowling.domain.api.game.GameStatus;
import com.telemis.bowling.domain.api.game.PlayerFrame;
import com.telemis.bowling.domain.api.game.event.GameCreated;
import com.telemis.bowling.domain.api.game.event.GameProceededTo;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @since 03/07/14
 */
public class GameAR extends AbstractAnnotatedAggregateRoot<String> {

    private static final long serialVersionUID = -3160433183079627649L;

    public static final int MAX_NUMBER_OF_FRAMES = 5;

    @AggregateIdentifier
    private String identifier;
    private GameStatus status;
    private Set<GamePlayer> players;

    protected GameAR() {

    }

    public GameAR(final String identifier, final int numberOfPlayers) {
        final Set<GamePlayer> gamePlayers = buildGamePlayerSetForNumberOfPlayers(numberOfPlayers);

        apply(new GameCreated(identifier, gamePlayers));
    }

    private Set<GamePlayer> buildGamePlayerSetForNumberOfPlayers(final int numberOfPlayers) {
        final Set<GamePlayer> gamePlayers = new HashSet<>();

        for (int i = 1; i <= numberOfPlayers; i++) {

            final Set<PlayerFrame> playerFrames = buildPlayerFrameSet();
            final GamePlayer newGamePlayer = GamePlayer.newBuilder()
                    .withPlayerNumber(i)
                    .withPlayerName("Player " + i)
                    .withPlayerFrames(playerFrames)
                    .build();
            gamePlayers.add(newGamePlayer);
        }
        return gamePlayers;
    }

    private Set<PlayerFrame> buildPlayerFrameSet() {
        final Set<PlayerFrame> playerFrames = new HashSet<>();

        for (int i = 1; i <= MAX_NUMBER_OF_FRAMES; i++) {
            final PlayerFrame newPlayerFrame = new PlayerFrame(i, new TreeSet<Integer>());
            playerFrames.add(newPlayerFrame);
        }

        return playerFrames;
    }

    public void proceedTo(final String newStatus) {
        apply(new GameProceededTo(identifier, status.name(), newStatus));
    }

    @EventSourcingHandler
    @SuppressWarnings("UnusedDeclaration")
    public void handle(final GameCreated gameCreated) {
        identifier = gameCreated.getIdentifier();
        players = gameCreated.getPlayers();
    }

    @EventSourcingHandler
    @SuppressWarnings("UnusedDeclaration")
    public void handle(final GameProceededTo gameProceededTo) {
        status = GameStatus.valueOf(gameProceededTo.getEnteredStatus());
    }

}
