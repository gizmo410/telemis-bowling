package com.telemis.bowling.game;

import com.google.common.collect.ImmutableSet;
import com.telemis.bowling.domain.api.game.GamePlayer;
import com.telemis.bowling.domain.api.game.GameStatus;
import com.telemis.bowling.domain.api.game.PlayerFrame;
import com.telemis.bowling.domain.api.game.command.CreateGame;
import com.telemis.bowling.domain.api.game.command.RegisterPlayerThrow;
import com.telemis.bowling.domain.api.game.command.StopGame;
import com.telemis.bowling.domain.api.game.event.GameCreated;
import com.telemis.bowling.domain.api.game.event.GameStopped;
import com.telemis.bowling.domain.api.game.event.PlayerScoreUpdated;

import java.util.Set;
import java.util.TreeSet;

/**
 * @since 04/07/14
 */
public final class DataFixtures {

    // COMMANDS
    // ===============================
    public static final int NUMBER_OF_PLAYERS = 2;
    public static final CreateGame CREATE_GAME = new CreateGame(NUMBER_OF_PLAYERS);
    public static final String GAME_IDENTIFIER = CREATE_GAME.getIdentifier();
    public static final StopGame STOP_GAME = new StopGame(GAME_IDENTIFIER);
    public static final int NUMBER_OF_SKITTLES_DOWN = 8;
    public static final RegisterPlayerThrow REGISTER_PLAYER_THROW = new RegisterPlayerThrow(GAME_IDENTIFIER, NUMBER_OF_SKITTLES_DOWN);

    // EVENTS
    // ===============================

    public static final GamePlayer GAME_PLAYER_1 = GamePlayer.newBuilder()
            .withPlayerNumber(1)
            .withPlayerName("Player 1")
            .withPlayerFrames(new TreeSet<PlayerFrame>())
            .build();
    public static final GamePlayer GAME_PLAYER_2 = GamePlayer.newBuilder()
            .withPlayerNumber(2)
            .withPlayerName("Player 2")
            .withPlayerFrames(new TreeSet<PlayerFrame>())
            .build();
    public static final Set<GamePlayer> GAME_PLAYERS = ImmutableSet.of(GAME_PLAYER_1, GAME_PLAYER_2);
    public static final GameCreated GAME_CREATED = new GameCreated(CREATE_GAME.getIdentifier(), GAME_PLAYERS, GAME_PLAYER_1);

    public static final GameStopped GAME_STOPPED = new GameStopped(GAME_IDENTIFIER, GameStatus.STOPPED);

    public static final PlayerScoreUpdated PLAYER_SCORE_UPDATED = PlayerScoreUpdated.newBuilder()
            .withGameIdentifier(GAME_IDENTIFIER)
            .withPlayer(GAME_PLAYER_1)
            .withNextPlayer(GAME_PLAYER_2)
            .build();

}
