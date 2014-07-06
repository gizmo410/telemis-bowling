package be.milieuinfo.midas.ui.controller;

import be.milieuinfo.midas.ui.internationalization.OndersteundeTalen;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Properties;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 13/06/14
 */
@Controller
public class ViewController implements InitializingBean {

    private ObjectMapper mapper;
    private Properties messageProperties;
    private Properties validationMessageProperties;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String home() {
        return "index.html";
    }

    /**
     * Allows for internationalization.
     * Based on the language requested we get the right property file and map it to JSON.
     *
     * @param language The language requested
     * @return A JSON version of the properties
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/languages/{language}.json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    String getLanguage(@PathVariable("language") OndersteundeTalen language) throws JsonProcessingException {

        checkNotNull(language, "Language cannot be null");

        final Properties messages = new Properties();
        messages.putAll(messageProperties);
        messages.putAll(validationMessageProperties);

        return mapper.writeValueAsString(messages);
    }

    public void setMessageProperties(final Properties messageProperties) {
        this.messageProperties = messageProperties;
    }

    public void setValidationMessageProperties(final Properties validationMessageProperties) {
        this.validationMessageProperties = validationMessageProperties;
    }

    public void setMapper(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        checkNotNull(mapper, "Object mapper cannot be null");
        checkNotNull(messageProperties, "Message properties file cannot be null");
        checkNotNull(validationMessageProperties, "Validation message properties file cannot be null");
    }
}
