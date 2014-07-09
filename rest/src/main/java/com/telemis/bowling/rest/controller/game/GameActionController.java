package com.telemis.bowling.rest.controller.game;

import com.google.common.collect.Lists;
import com.telemis.bowling.domain.api.CommandGateway;
import com.telemis.bowling.domain.api.game.command.RegisterPlayerThrow;
import com.telemis.bowling.domain.api.game.command.StopGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.telemis.bowling.rest.controller.ControlerConstants.DEFAULT_TIMEOUT;
import static com.telemis.bowling.rest.controller.ControlerConstants.DEFAULT_TIMEUNIT;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @since 08/07/14
 */
@Controller
@RequestMapping(GameActionController.ROOT_URL)
public class GameActionController {

    public static final String ROOT_URL = "/games/{id}/action";
    public static final String DUMMY_GAME_IDENTIFIER = "game identifier";
    public static final int DUMMY_NUMBER_OF_SKITTLES_DOWN = 15;

    @Autowired
    private CommandGateway commandGateway;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    ResourceSupport listCommands(@PathVariable("id") String id) {
        final ResourceSupport result = new ResourceSupport();
        result.add(commandLinks(id));
        return result;
    }

    private List<Link> commandLinks(String id) {
        final List<Link> links = Lists.newArrayList();
        links.add(linkTo(methodOn(GameActionController.class).listCommands(id)).withSelfRel());
        links.add(linkTo(methodOn(GameActionController.class).update(id, createDummyRegisterPlayerThrow())).withRel("registerPlayerThrow"));
        links.add(linkTo(methodOn(GameActionController.class).delete(id)).withRel("stopGame"));
        return links;
    }

    private RegisterPlayerThrow createDummyRegisterPlayerThrow() {
        return new RegisterPlayerThrow(DUMMY_GAME_IDENTIFIER, DUMMY_NUMBER_OF_SKITTLES_DOWN);
    }

    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity update(@PathVariable("id") String id, @RequestBody final RegisterPlayerThrow registerPlayerThrow) {
        commandGateway.sendAndWait(registerPlayerThrow, DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity delete(@PathVariable("id") String id) {
        final StopGame stopGame = new StopGame(id);
        commandGateway.sendAndWait(stopGame, DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
