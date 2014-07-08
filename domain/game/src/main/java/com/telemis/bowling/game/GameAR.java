package com.telemis.bowling.game;

import com.google.common.collect.ImmutableList;
import com.telemis.bowling.domain.api.game.GamePlayer;
import com.telemis.bowling.domain.api.game.GameStatus;
import com.telemis.bowling.domain.api.game.PlayerFrame;
import com.telemis.bowling.domain.api.game.event.GameCreated;
import com.telemis.bowling.domain.api.game.event.GameProceededTo;
import com.telemis.bowling.domain.api.game.event.GameStopped;
import com.telemis.bowling.domain.api.game.event.PlayerScoreUpdated;
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
    private GamePlayer currentPlayer;

    protected GameAR() {
    }

    public GameAR(final String identifier, final int numberOfPlayers) {
        final Set<GamePlayer> gamePlayers = buildGamePlayerSetForNumberOfPlayers(numberOfPlayers);
        final GamePlayer firstPlayer = gamePlayers.iterator().next();

        apply(new GameCreated(identifier, gamePlayers, firstPlayer));
    }

    private Set<GamePlayer> buildGamePlayerSetForNumberOfPlayers(final int numberOfPlayers) {
        final Set<GamePlayer> gamePlayers = new HashSet<>();

        for (int i = 1; i <= numberOfPlayers; i++) {

            //final SortedSet<PlayerFrame> playerFrames = buildPlayerFrameSet();
            final GamePlayer newGamePlayer = GamePlayer.newBuilder()
                    .withPlayerNumber(i)
                    .withPlayerName("Player " + i)
                    .withPlayerFrames(new TreeSet<PlayerFrame>())
                    .build();
            gamePlayers.add(newGamePlayer);
        }
        return gamePlayers;
    }

   /* private SortedSet<PlayerFrame> buildPlayerFrameSet() {
        final SortedSet<PlayerFrame> playerFrames = new TreeSet<>();

        for (int i = 1; i <= MAX_NUMBER_OF_FRAMES; i++) {
            final PlayerFrame newPlayerFrame = new PlayerFrame(i, new TreeSet<FrameThrow>());
            playerFrames.add(newPlayerFrame);
        }

        return playerFrames;
    }*/

    public void proceedTo(final String newStatus) {
        apply(new GameProceededTo(identifier, status.name(), newStatus));
    }

    public void currentPlayerHasPlay(final int numberOfSkittlesDown) {
        currentPlayer.registerScore(numberOfSkittlesDown);
        final GamePlayer nextPlayer = computeNextPlayer();

        apply(PlayerScoreUpdated.newBuilder()
                .withGameIdentifier(identifier)
                .withPlayer(currentPlayer)
                .withNextPlayer(nextPlayer)
                .build());
    }

    private GamePlayer computeNextPlayer() {
        final ImmutableList<GamePlayer> gamePlayers = ImmutableList.copyOf(players);
        final int currentPlayerIndex = gamePlayers.indexOf(currentPlayer);

        if (currentPlayerIndex == gamePlayers.size() - 1) {
            return gamePlayers.get(0);
        }

        final int nextPlayerIndex = currentPlayerIndex + 1;
        return gamePlayers.get(nextPlayerIndex);
    }

    public void stopGame() {
        apply(new GameStopped(identifier, GameStatus.STOPPED));
    }

    @EventSourcingHandler
    @SuppressWarnings("UnusedDeclaration")
    public void handle(final GameCreated gameCreated) {
        identifier = gameCreated.getIdentifier();
        players = gameCreated.getPlayers();
        currentPlayer = gameCreated.getCurrentPlayer();
    }

    @EventSourcingHandler
    @SuppressWarnings("UnusedDeclaration")
    public void handle(final GameProceededTo gameProceededTo) {
        status = GameStatus.valueOf(gameProceededTo.getEnteredStatus());
    }

    @EventSourcingHandler
    @SuppressWarnings("UnusedDeclaration")
    public void handle(final PlayerScoreUpdated playerScoreUpdated) {
        currentPlayer = playerScoreUpdated.getNextPlayer();
    }

    @EventSourcingHandler
    @SuppressWarnings("UnusedDeclaration")
    public void handle(final GameStopped gameStopped) {
        status = gameStopped.getGameStatus();
    }

}
