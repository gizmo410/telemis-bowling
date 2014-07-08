package com.telemis.bowling.ui.controller;

import com.telemis.bowling.ui.controller.ViewController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @since 18/06/14
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewControllerTest {

    @InjectMocks
    private ViewController viewController;

    @Test
    public void assertThat_when_homeViewIsRequested_then_IndexPageIsReturned() {

        final String resultPage = viewController.home();

        assertThat(resultPage, is(notNullValue()));
        assertThat(resultPage, is(equalTo("index.html")));

    }
}
