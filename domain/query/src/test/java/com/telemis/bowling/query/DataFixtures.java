package com.telemis.bowling.query;

import com.google.common.collect.ImmutableSet;
import com.telemis.bowling.domain.api.game.GamePlayer;
import com.telemis.bowling.domain.api.game.event.GameCreated;

/**
 * @since 08/07/14
 */
public final class DataFixtures {

    public static final String GAME_IDENTIFIER = "game identifier";
    public static final int PLAYER_NUMBER = 1;
    public static final String PLAYER_NAME = "Player 1";
    public static final GamePlayer GAME_PLAYER = GamePlayer.newBuilder()
            .withPlayerNumber(PLAYER_NUMBER)
            .withPlayerName(PLAYER_NAME)
            .build();
    public static final ImmutableSet<GamePlayer> GAME_PLAYERS = ImmutableSet.of(GAME_PLAYER);

    public static final GameCreated GAME_CREATED = new GameCreated(GAME_IDENTIFIER, GAME_PLAYERS, GAME_PLAYER);

}
