package be.milieuinfo.midas.rest.controller.controle;

import com.telemis.bowling.domain.api.CommandGateway;
import be.milieuinfo.midas.domain.api.controle.command.MaakControle;
import be.milieuinfo.midas.rest.support.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static be.milieuinfo.midas.rest.controller.ControlerConstants.DEFAULT_TIMEOUT;
import static be.milieuinfo.midas.rest.controller.ControlerConstants.DEFAULT_TIMEUNIT;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(ControlesController.ROOT_URL)
public class ControlesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControlesController.class);

    public static final String ROOT_URL = "/controles/action";

    @Autowired
    private CommandGateway commandGateway;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    ResourceSupport listActions() {
        return createActionLinks();
    }

    private ResourceSupport createActionLinks() {
        final ResourceSupport result = new ResourceSupport();
        result.add(linkTo(methodOn(ControlesController.class).listActions()).withSelfRel());
        result.add(linkTo(methodOn(ControlesController.class).create(createDummyMaakControle())).withRel("maakControle"));
        return result;
    }

    private MaakControle createDummyMaakControle() {
        return MaakControle.newBuilder().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity create(@RequestBody MaakControle command) {
        commandGateway.sendAndWait(command, SecurityUtils.getAuthenticatedUser(), DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
