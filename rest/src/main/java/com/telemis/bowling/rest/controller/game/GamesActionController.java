package com.telemis.bowling.rest.controller.game;

import com.telemis.bowling.domain.api.CommandGateway;
import com.telemis.bowling.domain.api.game.command.CreateGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.telemis.bowling.rest.controller.ControlerConstants.DEFAULT_TIMEOUT;
import static com.telemis.bowling.rest.controller.ControlerConstants.DEFAULT_TIMEUNIT;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @since 08/07/14
 */
@RestController
@RequestMapping(GamesActionController.ROOT_URL)
public class GamesActionController {

    public static final String ROOT_URL = "/games/action";

    @Autowired
    private CommandGateway commandGateway;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    ResourceSupport listActions() {
        return createActionLinks();
    }

    private ResourceSupport createActionLinks() {
        final ResourceSupport result = new ResourceSupport();
        result.add(linkTo(methodOn(GamesActionController.class).listActions()).withSelfRel());
        result.add(linkTo(methodOn(GamesActionController.class).create(createDummyCreateGame())).withRel("createGame"));
        return result;
    }

    private CreateGame createDummyCreateGame() {
        return new CreateGame(1);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity create(@RequestBody CreateGame command) {
        commandGateway.sendAndWait(command, DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
        return new ResponseEntity<>(command, HttpStatus.OK);
    }


}
