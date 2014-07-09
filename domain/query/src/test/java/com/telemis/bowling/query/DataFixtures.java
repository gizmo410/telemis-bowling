package com.telemis.bowling.query;

import com.google.common.collect.ImmutableList;
import com.telemis.bowling.domain.api.game.GamePlayer;
import com.telemis.bowling.domain.api.game.event.GameCreated;

import java.util.LinkedList;
import java.util.List;

/**
 * @since 08/07/14
 */
public final class DataFixtures {

    public static final String GAME_IDENTIFIER = "game identifier";
    public static final int PLAYER_NUMBER = 1;
    public static final String PLAYER_NAME = "Player 1";
    public static final GamePlayer GAME_PLAYER = GamePlayer.newBuilder()
            .withNumber(PLAYER_NUMBER)
            .withName(PLAYER_NAME)
            .build();
    public static final List<GamePlayer> GAME_PLAYERS = new LinkedList<>(ImmutableList.of(GAME_PLAYER));

    public static final GameCreated GAME_CREATED = new GameCreated(GAME_IDENTIFIER, GAME_PLAYERS, GAME_PLAYER);

}
