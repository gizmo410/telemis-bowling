package com.telemis.bowling.rest.controller.game;

import com.telemis.bowling.domain.api.CommandGateway;
import com.telemis.bowling.domain.api.game.command.CreateGame;
import com.telemis.bowling.rest.controller.ControlerConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.concurrent.TimeUnit;

import static com.telemis.bowling.rest.controller.RestDataFixtures.standardCreateGameJSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @since 08/07/14
 */
@RunWith(MockitoJUnitRunner.class)
public class GamesActionControllerTest {

    MockMvc mockMvc;

    @Mock
    CommandGateway commandGateway;

    @InjectMocks
    GamesActionController controller;


    @Before
    public void setUp() {

        mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();

    }

    @Test
    public void when_postRootUrlIsRequested_then_responseIsValid() throws Exception {

        final MockHttpServletRequestBuilder requestBuilder = post(GamesActionController.ROOT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(standardCreateGameJSON);

        final ResultActions resultActions = mockMvc.perform(requestBuilder);

        assertThat(resultActions, is(notNullValue()));
        resultActions
//                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void when_postRootUrlIsRequested_then_givenCommandIsSentAndWait() throws Exception {

        final MockHttpServletRequestBuilder requestBuilder = post(GamesActionController.ROOT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(standardCreateGameJSON);

        mockMvc.perform(requestBuilder);

        final long expectedTimeout = ControlerConstants.DEFAULT_TIMEOUT;
        final TimeUnit expectedTimeUnit = ControlerConstants.DEFAULT_TIMEUNIT;
        verify(commandGateway).sendAndWait(any(CreateGame.class), anyString(), eq(expectedTimeout), eq(expectedTimeUnit));
        verifyNoMoreInteractions(commandGateway);
    }
}
