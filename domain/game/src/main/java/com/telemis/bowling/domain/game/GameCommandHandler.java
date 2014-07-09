package com.telemis.bowling.domain.game;

import com.telemis.bowling.domain.api.game.command.CreateGame;
import com.telemis.bowling.domain.api.game.command.GameProceedTo;
import com.telemis.bowling.domain.api.game.command.RegisterPlayerThrow;
import com.telemis.bowling.domain.api.game.command.StopGame;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Component handling commands regarding {@link GameAR}.
 *
 * @since 04/07/14
 */
@Component
public class GameCommandHandler {

    private Repository<GameAR> gameRepository;

    @Inject
    public GameCommandHandler(Repository<GameAR> gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Handles CreateGame commands by creating a new Game Aggregate Root based on the commands data.
     *
     * @param command The command to be handled.
     */
    @CommandHandler
    public void handle(final CreateGame command) {

        final GameAR gameAR = new GameAR(
                command.getIdentifier(),
                command.getNumberOfPlayers()
        );

        gameRepository.add(gameAR);
    }

    /**
     * Handles GameProceedTo commands by fetching the Aggregate root it applies to and triggering the status change on it..
     *
     * @param command The command to be handled.
     */
    @CommandHandler
    public void handle(final GameProceedTo command) {
        final String gameIdentifier = command.getGameIdentifier();
        final GameAR gameAR = gameRepository.load(gameIdentifier);

        gameAR.proceedTo(command.getNewStatus());
    }

    @CommandHandler
    public void handle(final RegisterPlayerThrow command) {
        final String gameIdentifier = command.getGameId();
        final GameAR gameAR = gameRepository.load(gameIdentifier);
        final int numberOfSkittlesDown = command.getNumberOfSkittlesDown();

        gameAR.currentPlayerHasPlay(numberOfSkittlesDown);
    }

    @CommandHandler
    public void handle(final StopGame command) {
        final String gameIdentifier = command.getGameId();
        final GameAR gameAR = gameRepository.load(gameIdentifier);

        gameAR.stopGame();
    }

}
