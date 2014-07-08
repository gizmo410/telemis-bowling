package com.telemis.bowling.query.game;

import com.telemis.bowling.domain.api.game.GamePlayer;
import com.telemis.bowling.domain.api.game.GameStatus;
import com.telemis.bowling.domain.api.game.event.GameCreated;
import com.telemis.bowling.domain.api.game.event.GameStopped;
import com.telemis.bowling.domain.api.game.event.PlayerScoreUpdated;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @since 08/07/14
 */
@Service
public class GameViewUpdater {

    @Autowired
    private GameViewRepository gameViewRepository;

    @EventHandler
    @Transactional
    public void handle(final GameCreated event) {

        final GameView newGame = GameView.newBuilder()
                .withStatus(GameStatus.STARTED)
                .withCurrentPlayerName(event.getCurrentPlayer().getPlayerName())
                .withPlayers(event.getPlayers())
                .withId(event.getIdentifier())
                .build();

        gameViewRepository.save(newGame);

    }

    @EventHandler
    @Transactional
    public void handle(final PlayerScoreUpdated event) {

        final GameView game = gameViewRepository.getOne(event.getGameIdentifier());

        game.setCurrentPlayerName(event.getNextPlayer().getPlayerName());

        resetCurrentPlayer(event, game);

    }

    private void resetCurrentPlayer(final PlayerScoreUpdated event, final GameView game) {
        for (GamePlayer curPlayer : game.getPlayers()) {
            if (curPlayer.equals(event.getPlayer())) {
                game.getPlayers().remove(curPlayer);
                game.getPlayers().add(event.getPlayer());
            }
        }
    }

    @EventHandler
    @Transactional
    public void handle(final GameStopped event) {

        final GameView game = gameViewRepository.getOne(event.getGameIdentifier());
        game.setStatus(event.getGameStatus());

    }

}
