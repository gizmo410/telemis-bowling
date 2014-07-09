package com.telemis.bowling.domain.game;

import com.google.common.collect.ImmutableList;
import com.telemis.bowling.domain.api.game.GamePlayer;
import com.telemis.bowling.domain.api.game.GameStatus;
import com.telemis.bowling.domain.api.game.PlayerFrame;
import com.telemis.bowling.domain.api.game.PlayerScore;
import com.telemis.bowling.domain.api.game.command.CreateGame;
import com.telemis.bowling.domain.api.game.command.RegisterPlayerThrow;
import com.telemis.bowling.domain.api.game.command.StopGame;
import com.telemis.bowling.domain.api.game.event.GameCreated;
import com.telemis.bowling.domain.api.game.event.GameStopped;
import com.telemis.bowling.domain.api.game.event.PlayerScoreUpdated;

import java.util.LinkedList;
import java.util.List;

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

    public static final String PLAYER_1_NAME = "Player 1";
    public static final GamePlayer GAME_PLAYER_1 = GamePlayer.newBuilder()
            .withNumber(1)
            .withName(PLAYER_1_NAME)
            .withFrames(new LinkedList<PlayerFrame>())
            .build();
    public static final String PLAYER_2_NAME = "Player 2";
    public static final GamePlayer GAME_PLAYER_2 = GamePlayer.newBuilder()
            .withNumber(2)
            .withName(PLAYER_2_NAME)
            .withFrames(new LinkedList<PlayerFrame>())
            .build();

    public static final List<GamePlayer> GAME_PLAYERS = new LinkedList<>(ImmutableList.of(GAME_PLAYER_1, GAME_PLAYER_2));
    public static final GameCreated GAME_CREATED = new GameCreated(CREATE_GAME.getIdentifier(), GAME_PLAYERS, GAME_PLAYER_1);

    public static final PlayerScore PLAYER_1_SCORE = new PlayerScore(PLAYER_1_NAME, 1);
    public static final PlayerScore PLAYER_2_SCORE = new PlayerScore(PLAYER_2_NAME, 2);
    public static final List<PlayerScore> PLAYER_SCORES = ImmutableList.of(PLAYER_1_SCORE, PLAYER_2_SCORE);
    public static final GameStopped GAME_STOPPED = new GameStopped(GAME_IDENTIFIER, GameStatus.STOPPED, PLAYER_SCORES);

    public static final PlayerScoreUpdated PLAYER_SCORE_UPDATED = PlayerScoreUpdated.newBuilder()
            .withGameIdentifier(GAME_IDENTIFIER)
            .withPreviousPlayers(GAME_PLAYER_1)
            .withNextPlayer(GAME_PLAYER_2)
            .build();

}
