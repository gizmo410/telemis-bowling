package com.telemis.bowling.game;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.telemis.bowling.game.DataFixtures.CREATE_GAME;
import static com.telemis.bowling.game.DataFixtures.GAME_CREATED;

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
    public void when_MaakControleCommandReceived_then_ControleAangemaaktEventIsTriggered() {

        fixture
                .given()
                .when(CREATE_GAME)
                .expectEvents(GAME_CREATED);

    }

}
