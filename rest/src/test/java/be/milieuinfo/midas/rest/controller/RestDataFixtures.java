package be.milieuinfo.midas.rest.controller;

import be.milieuinfo.midas.domain.api.controle.command.MaakControle;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Throwables;

import java.util.HashSet;
import java.util.Set;

public class RestDataFixtures {

    // jackson json (de)serialization ? https://github.com/FasterXML/jackson-databind/
    static ObjectMapper mapper = new ObjectMapper();

    private RestDataFixtures() {
    }

    // Controle
    //===========================================

    public static final String UITVOEREND_TOEZICHTHOUDER = "me & my guitar";
    public static final String ONDERWERK = "onderwerp";
    public static final Set<String> PROGRAMMA_ONDERDELEN = new HashSet<>();
    public static final Set<String> INTERN_BEGELEIDER = new HashSet<>();

    public static final MaakControle MAAK_CONTROLE = MaakControle.newBuilder()
            .uitvoerendToezichthouder(UITVOEREND_TOEZICHTHOUDER)
            .onderwerp(ONDERWERK)
            .programmaOnderdelen(PROGRAMMA_ONDERDELEN)
            .internBegeleiders(INTERN_BEGELEIDER)
            .build();


    //  JSON MAPPING CONFIGURATION + FIXTURES
    //=======================================

    public static String standardRegisterControleJSON;

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // to prevent exception when encountering unknown property:
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // to allow coercion of JSON empty String ("") to null Object value:

        try {
            standardRegisterControleJSON = mapper.writeValueAsString(MAAK_CONTROLE);
        } catch (Exception e) {
            Throwables.propagate(e);
        }
    }


}
