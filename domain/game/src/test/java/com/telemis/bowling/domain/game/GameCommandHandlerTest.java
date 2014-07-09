package com.telemis.bowling.domain.game;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.telemis.bowling.domain.game.DataFixtures.*;

/**
 * @since 04/07/14
 */
@Ignore
public class GameCommandHandlerTest {

    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = Fixtures.newGivenWhenThenFixture(GameAR.class);
        final GameCommandHandler commandHandler = new GameCommandHandler(fixture.getRepository());
        fixture.registerAnnotatedCommandHandler(commandHandler);
    }

    @Test
    public void when_CreateGameCommandReceived_then_GameCreatedEventIsTriggered() {

        fixture
                .given()
                .when(CREATE_GAME)
                .expectEvents(GAME_CREATED);

    }

    @Test
    public void when_RegisterPlayerThrowCommandReceived_then_PlayerScoreUpdatedEventIsTriggered() {

        fixture
                .given(GAME_CREATED)
                .when(REGISTER_PLAYER_THROW)
                .expectEvents(PLAYER_SCORE_UPDATED);

    }

    @Test
    public void when_StopGameCommandReceived_then_GameStoppedEventIsTriggered() {

        fixture
                .given(GAME_CREATED)
                .when(STOP_GAME)
                .expectEvents(GAME_STOPPED);

    }

}
