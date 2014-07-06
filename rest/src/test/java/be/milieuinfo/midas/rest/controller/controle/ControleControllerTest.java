package be.milieuinfo.midas.rest.controller.controle;

import com.telemis.bowling.domain.api.CommandGateway;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @since 02/07/14
 */
@RunWith(MockitoJUnitRunner.class)
public class ControleControllerTest {


    public static final String CONTROLE_RESOURCE_URL = "/controles/10/action";

    MockMvc mockMvc;

    @Mock
    CommandGateway commandGateway;

    @InjectMocks
    ControleController controller;


    @Before
    public void setUp() {

        mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();

    }


    @Test
    public void when_resourceUpdateIsRequested_then_responseIsValid() throws Exception {

        final MockHttpServletRequestBuilder requestBuilder = put(CONTROLE_RESOURCE_URL).contentType(MediaType.APPLICATION_JSON);

        final ResultActions resultActions = mockMvc.perform(requestBuilder);

        assertThat(resultActions, is(notNullValue()));
        resultActions
//                .andDo(print())
                .andExpect(status().isNotImplemented());
    }

    @Test
    public void when_resourceDeletionIsRequested_then_responseIsValid() throws Exception {

        final MockHttpServletRequestBuilder requestBuilder = delete(CONTROLE_RESOURCE_URL).contentType(MediaType.APPLICATION_JSON);

        final ResultActions resultActions = mockMvc.perform(requestBuilder);

        assertThat(resultActions, is(notNullValue()));
        resultActions
//                .andDo(print())
                .andExpect(status().isNotImplemented());
    }

}
