package com.telemis.bowling.game;

import com.telemis.bowling.domain.api.game.GameStatus;
import com.telemis.bowling.domain.api.game.command.GameProceedTo;
import com.telemis.bowling.domain.api.game.event.GameCreated;
import com.telemis.bowling.domain.api.game.event.GameProceededTo;
import com.telemis.bowling.domain.api.game.event.GameStopped;
import com.telemis.bowling.domain.api.game.event.PlayerScoreUpdated;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.repository.Repository;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.EndSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @since 04/07/14
 */
public class GameSaga extends AbstractAnnotatedSaga {

    private static final long serialVersionUID = 6552890807640925710L;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameSaga.class);

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient Repository<GameAR> gameRepository;

    private GameStatus gameStatus;

    @StartSaga
    @SagaEventHandler(associationProperty = "identifier")
    @SuppressWarnings("unused")
    public void handle(final GameCreated event) {
        final GameProceedTo gameProceedTo = new GameProceedTo(event.getIdentifier(), GameStatus.STARTED.name());
        commandGateway.send(gameProceedTo);
    }

    @SagaEventHandler(associationProperty = "gameIdentifier")
    @SuppressWarnings("unused")
    public void handle(final GameProceededTo event) {
        gameStatus = GameStatus.valueOf(event.getEnteredStatus());

        if (gameStatus == GameStatus.COMPLETED) {
            LOGGER.info("Game Saga for Game identifier {} will end because the game is now completed.", event.getGameIdentifier());
            end();
        }
    }

    @SagaEventHandler(associationProperty = "gameIdentifier")
    @SuppressWarnings("unused")
    public void handle(final PlayerScoreUpdated event) {
        final String gameIdentifier = event.getGameIdentifier();
        final GameAR game = gameRepository.load(gameIdentifier);
        if (isGameCompleted(game)) {
            final GameProceedTo gameProceedTo = new GameProceedTo(gameIdentifier, GameStatus.COMPLETED.toString());
            commandGateway.send(gameProceedTo);
        }
    }

    private boolean isGameCompleted(final GameAR game) {
        // TODO Fill in

        return false;
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "gameIdentifier")
    @SuppressWarnings("unused")
    public void handle(final GameStopped event) {
        gameStatus = event.getGameStatus();
        LOGGER.info("Game Saga for Game identifier {} will end because the game has been stopped.", event.getGameIdentifier());
    }
}
