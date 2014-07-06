package com.telemis.bowling.game;

import com.google.common.collect.ImmutableSet;
import com.telemis.bowling.domain.api.game.GamePlayer;
import com.telemis.bowling.domain.api.game.GameStatus;
import com.telemis.bowling.domain.api.game.PlayerFrame;
import com.telemis.bowling.domain.api.game.command.CreateGame;
import com.telemis.bowling.domain.api.game.event.GameCreated;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @since 04/07/14
 */
public final class DataFixtures {

    // COMMANDS
    // ===============================

    public static final String GAME_IDENTIFIER = "game identifier";
    public static final int NUMBER_OF_PLAYERS = 2;
    public static final CreateGame CREATE_GAME = new CreateGame(GAME_IDENTIFIER, NUMBER_OF_PLAYERS);

    // EVENTS
    // ===============================

    public static final PlayerFrame PLAYER_1_FRAME_1 = new PlayerFrame(1, new LinkedList<Integer>());
    public static final PlayerFrame PLAYER_1_FRAME_2 = new PlayerFrame(2, new LinkedList<Integer>());
    public static final PlayerFrame PLAYER_1_FRAME_3 = new PlayerFrame(3, new LinkedList<Integer>());
    public static final PlayerFrame PLAYER_1_FRAME_4 = new PlayerFrame(4, new LinkedList<Integer>());
    public static final PlayerFrame PLAYER_1_FRAME_5 = new PlayerFrame(5, new LinkedList<Integer>());
    public static final HashSet<PlayerFrame> PLAYER_1_FRAMES = new HashSet<>(ImmutableSet.of(
            PLAYER_1_FRAME_1,
            PLAYER_1_FRAME_2,
            PLAYER_1_FRAME_3,
            PLAYER_1_FRAME_4,
            PLAYER_1_FRAME_5
    ));
    public static final GamePlayer GAME_PLAYER_1 = GamePlayer.newBuilder()
            .withPlayerNumber(1)
            .withPlayerName("Player 1")
            .withPlayerFrames(PLAYER_1_FRAMES)
            .build();
    public static final PlayerFrame PLAYER_2_FRAME_1 = new PlayerFrame(1, new LinkedList<Integer>());
    public static final PlayerFrame PLAYER_2_FRAME_2 = new PlayerFrame(2, new LinkedList<Integer>());
    public static final PlayerFrame PLAYER_2_FRAME_3 = new PlayerFrame(3, new LinkedList<Integer>());
    public static final PlayerFrame PLAYER_2_FRAME_4 = new PlayerFrame(4, new LinkedList<Integer>());
    public static final PlayerFrame PLAYER_2_FRAME_5 = new PlayerFrame(5, new LinkedList<Integer>());
    public static final HashSet<PlayerFrame> PLAYER_2_FRAMES = new HashSet<>(ImmutableSet.of(
            PLAYER_2_FRAME_1,
            PLAYER_2_FRAME_2,
            PLAYER_2_FRAME_3,
            PLAYER_2_FRAME_4,
            PLAYER_2_FRAME_5
    ));
    public static final GamePlayer GAME_PLAYER_2 = GamePlayer.newBuilder()
            .withPlayerNumber(2)
            .withPlayerName("Player 2")
            .withPlayerFrames(PLAYER_2_FRAMES)
            .build();
    public static final Set<GamePlayer> GAME_PLAYERS = ImmutableSet.of(GAME_PLAYER_1, GAME_PLAYER_2);
    public static final GameCreated GAME_CREATED = new GameCreated(GAME_IDENTIFIER, GameStatus.STARTED, GAME_PLAYERS);

}
