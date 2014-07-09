package com.telemis.bowling.domain.game;

import com.google.common.collect.ImmutableList;
import com.telemis.bowling.domain.api.game.GamePlayer;
import com.telemis.bowling.domain.api.game.GameStatus;
import com.telemis.bowling.domain.api.game.PlayerFrame;
import com.telemis.bowling.domain.api.game.PlayerScore;
import com.telemis.bowling.domain.api.game.event.GameCreated;
import com.telemis.bowling.domain.api.game.event.GameProceededTo;
import com.telemis.bowling.domain.api.game.event.GameStopped;
import com.telemis.bowling.domain.api.game.event.PlayerScoreUpdated;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.*;

/**
 * @since 03/07/14
 */
public class GameAR extends AbstractAnnotatedAggregateRoot<String> {

    private static final long serialVersionUID = -3160433183079627649L;

    public static final int MAX_NUMBER_OF_FRAMES = 5;

    @AggregateIdentifier
    private String identifier;
    private GameStatus status;
    private List<GamePlayer> players;
    private int currentPlayerNumber;

    protected GameAR() {
    }

    public GameAR(final String identifier, final int numberOfPlayers) {
        final SortedSet<GamePlayer> gamePlayers = buildGamePlayerSetForNumberOfPlayers(numberOfPlayers);
        final GamePlayer firstPlayer = gamePlayers.iterator().next();

        apply(new GameCreated(identifier, new LinkedList<>(gamePlayers), firstPlayer));
    }

    private SortedSet<GamePlayer> buildGamePlayerSetForNumberOfPlayers(final int numberOfPlayers) {
        final SortedSet<GamePlayer> gamePlayers = new TreeSet<>();

        for (int i = 1; i <= numberOfPlayers; i++) {

            //final SortedSet<PlayerFrame> playerFrames = buildPlayerFrameSet();
            final GamePlayer newGamePlayer = GamePlayer.newBuilder()
                    .withNumber(i)
                    .withName("Player " + i)
                    .withFrames(new LinkedList<PlayerFrame>())
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
        final String exitedStatus = (status != null) ? status.name() : "";
        apply(new GameProceededTo(identifier, exitedStatus, newStatus));
    }

    public void currentPlayerHasPlay(final int numberOfSkittlesDown) {
        final GamePlayer currentPlayer = findCurrentPlayer();
        currentPlayer.registerScore(numberOfSkittlesDown);
        final GamePlayer nextPlayer = computeNextPlayer(currentPlayer);

        apply(PlayerScoreUpdated.newBuilder()
                .withGameIdentifier(identifier)
                .withPreviousPlayers(currentPlayer)
                .withNextPlayer(nextPlayer)
                .build());
    }

    private GamePlayer findCurrentPlayer() {
        for (GamePlayer player : players) {
            if (player.getNumber() == currentPlayerNumber) {
                return player;
            }
        }
        throw new IllegalStateException("current player number does not refer to any existing player");
    }

    private GamePlayer computeNextPlayer(final GamePlayer currentPlayer) {
        final ImmutableList<GamePlayer> gamePlayers = ImmutableList.copyOf(players);
        final int currentPlayerIndex = gamePlayers.indexOf(currentPlayer);

        if (currentPlayerIndex == gamePlayers.size() - 1) {
            return gamePlayers.get(0);
        }

        final int nextPlayerIndex = currentPlayerIndex + 1;
        return gamePlayers.get(nextPlayerIndex);
    }

    public void stopGame() {
        final List<PlayerScore> playerScores = getPlayerScores();
        Collections.sort(playerScores);
        apply(new GameStopped(identifier, GameStatus.STOPPED, playerScores));
    }

    private List<PlayerScore> getPlayerScores() {
        final List<PlayerScore> playerScores = new ArrayList<>();
        for (GamePlayer player : players) {
            final int totalScore = player.computeTotalScore();
            playerScores.add(new PlayerScore(player.getName(), totalScore));
        }
        return playerScores;
    }

    public boolean isGameCompletedForAllPlayers() {
        boolean isGameCompleted = true;

        for (GamePlayer player : players) {
            isGameCompleted = isGameCompleted && isGameCompletedForPlayer(player);
        }

        return isGameCompleted;
    }

    private boolean isGameCompletedForPlayer(final GamePlayer player) {
        boolean isPlayerGameCompleted = false;
        final int numberOfFrames = player.getFrames().size();
        if (numberOfFrames == 5) {
            final PlayerFrame lastFrame = player.getFrames().get(numberOfFrames - 1);
            if (isPlayerLastFrameCompleted(lastFrame)) {
                isPlayerGameCompleted = true;
            }
        }
        return isPlayerGameCompleted;
    }

    private boolean isPlayerLastFrameCompleted(final PlayerFrame frame) {
        boolean isPLayerLastFrameCompleted = false;
        final int numberOfThrows = frame.getFrameThrows().size();
        if (numberOfThrows >= 3) {
            if (!frame.doesContainStrikesOrSpares()) {

                isPLayerLastFrameCompleted = true;
            }
        }
        return isPLayerLastFrameCompleted;
    }

    @EventSourcingHandler
    @SuppressWarnings("UnusedDeclaration")
    public void handle(final GameCreated gameCreated) {
        identifier = gameCreated.getIdentifier();
        players = new LinkedList<>(gameCreated.getPlayers());
        findAndSetCurrentPlayer(gameCreated.getCurrentPlayer());
    }

    @EventSourcingHandler
    @SuppressWarnings("UnusedDeclaration")
    public void handle(final GameProceededTo gameProceededTo) {
        status = GameStatus.valueOf(gameProceededTo.getEnteredStatus());
    }

    @EventSourcingHandler
    @SuppressWarnings("UnusedDeclaration")
    public void handle(final PlayerScoreUpdated playerScoreUpdated) {
        final GamePlayer previousPlayer = playerScoreUpdated.getPreviousPlayer();
        final GamePlayer nextPlayer = playerScoreUpdated.getNextPlayer();

        findAndSetCurrentPlayer(nextPlayer);

        updatePlayerFramesToRegisterNewScore(previousPlayer);

        if (isGameCompletedForAllPlayers()) {
            proceedTo(GameStatus.COMPLETED.name());
            stopGame();
        }
    }

    private void updatePlayerFramesToRegisterNewScore(final GamePlayer previousPlayer) {
        final GamePlayer player = findPlayerInList(previousPlayer);
        final int playerIndex = players.indexOf(player);
        players.remove(player);
        players.add(playerIndex, previousPlayer);

    }

    private GamePlayer findPlayerInList(final GamePlayer player) {
        for (GamePlayer curPlayer : players) {
            if (curPlayer.equals(player)) {
                return curPlayer;
            }
        }
        throw new IllegalStateException("could not find given player in players list");
    }

    private void findAndSetCurrentPlayer(final GamePlayer playerToFind) {
        for (GamePlayer player : players) {
            if (player.equals(playerToFind)) {
                currentPlayerNumber = player.getNumber();
            }
        }
    }

    @EventSourcingHandler
    @SuppressWarnings("UnusedDeclaration")
    public void handle(final GameStopped gameStopped) {
        status = gameStopped.getGameStatus();
        markDeleted();
    }


}
