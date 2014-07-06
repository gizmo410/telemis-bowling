package be.milieuinfo.midas.rest.controller;

import com.telemis.bowling.domain.api.CommandGateway;
import be.milieuinfo.midas.domain.api.controle.command.MaakControle;
import be.milieuinfo.midas.rest.controller.controle.ControlesController;
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

import static be.milieuinfo.midas.rest.controller.RestDataFixtures.standardRegisterControleJSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class ControlesControllerTest {

    MockMvc mockMvc;

    @Mock
    CommandGateway commandGateway;

    @InjectMocks
    ControlesController controller;


    @Before
    public void setUp() {

        mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();

    }

    @Test
    public void when_postRootUrlIsRequested_then_responseIsValid() throws Exception {

        final MockHttpServletRequestBuilder requestBuilder = post(ControlesController.ROOT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(standardRegisterControleJSON);

        final ResultActions resultActions = mockMvc.perform(requestBuilder);

        assertThat(resultActions, is(notNullValue()));
        resultActions
//                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void when_postRootUrlIsRequested_then_givenCommandIsSentAndForget() throws Exception {

        final MockHttpServletRequestBuilder requestBuilder = post(ControlesController.ROOT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(standardRegisterControleJSON);

        mockMvc.perform(requestBuilder);

        final long expectedTimeout = ControlerConstants.DEFAULT_TIMEOUT;
        final TimeUnit expectedTimeUnit = ControlerConstants.DEFAULT_TIMEUNIT;
        verify(commandGateway).sendAndWait(any(MaakControle.class), anyString(), eq(expectedTimeout), eq(expectedTimeUnit));
        verifyNoMoreInteractions(commandGateway);
    }

}
