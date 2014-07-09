package com.telemis.bowling.query.game;

import com.telemis.bowling.domain.api.game.GamePlayer;
import com.telemis.bowling.domain.api.game.GameStatus;
import com.telemis.bowling.domain.api.game.event.GameCreated;
import com.telemis.bowling.domain.api.game.event.GameStopped;
import com.telemis.bowling.domain.api.game.event.PlayerScoreUpdated;
import com.telemis.bowling.query.notification.Notification;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @since 08/07/14
 */
@Service
public class GameViewUpdater {

    @Autowired
    private GameViewRepository gameViewRepository;

    @EventHandler
    @Notification
    @Transactional
    public void handle(final GameCreated event) {

        final GameView newGame = GameView.newBuilder()
                .withStatus(GameStatus.STARTED)
                .withCurrentPlayerName(event.getCurrentPlayer().getName())
                .withPlayers(new TreeSet<>(event.getPlayers()))
                .withId(event.getIdentifier())
                .build();

        gameViewRepository.save(newGame);

    }

    @EventHandler
    @Notification
    @Transactional
    public void handle(final PlayerScoreUpdated event) {

        final GameView game = gameViewRepository.getOne(event.getGameIdentifier());

        final SortedSet<GamePlayer> players = new TreeSet<>(game.getPlayers());
        resetCurrentPlayer(event, players);

        game.setCurrentPlayerName(event.getNextPlayer().getName());
        game.setPlayers(players);
        gameViewRepository.save(game);

    }

    private void resetCurrentPlayer(final PlayerScoreUpdated event, SortedSet<GamePlayer> players) {
        for (GamePlayer curPlayer : players) {
            if (curPlayer.equals(event.getPreviousPlayer())) {
                players.remove(curPlayer);
                players.add(event.getPreviousPlayer());
                break;
            }
        }
    }

    @EventHandler
    @Notification
    @Transactional
    public void handle(final GameStopped event) {
        gameViewRepository.delete(event.getGameIdentifier());
    }

}
