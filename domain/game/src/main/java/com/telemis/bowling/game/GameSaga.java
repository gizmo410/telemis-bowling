package com.telemis.bowling.game;

import com.telemis.bowling.domain.api.game.GameStatus;
import com.telemis.bowling.domain.api.game.command.GameProceedTo;
import com.telemis.bowling.domain.api.game.event.GameCreated;
import com.telemis.bowling.domain.api.game.event.GameProceededTo;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @since 04/07/14
 */
public class GameSaga extends AbstractAnnotatedSaga {

    private static final long serialVersionUID = 6552890807640925710L;

    @Autowired
    private transient CommandGateway commandGateway;

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
    }
}
