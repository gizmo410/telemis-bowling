package com.telemis.bowling.game;

import com.telemis.bowling.domain.api.game.command.CreateGame;
import com.telemis.bowling.domain.api.game.command.GameProceedTo;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

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
        final String gameIdentifier = checkNotNull(command.getGameIdentifier(), "game identifier cannot be null");
        final GameAR gameAR = gameRepository.load(gameIdentifier);

        gameAR.proceedTo(command.getNewStatus());
    }

}
