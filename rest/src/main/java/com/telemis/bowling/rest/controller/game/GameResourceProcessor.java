package com.telemis.bowling.rest.controller.game;

import com.telemis.bowling.query.game.GameView;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @since 08/07/14
 */
@Component
public class GameResourceProcessor implements ResourceProcessor<Resource<GameView>> {
    @Override
    public Resource<GameView> process(final Resource<GameView> resource) {
        final GameView content = resource.getContent();
        resource.add(linkTo(methodOn(GameActionController.class).listCommands(content.getIdentifier())).withRel("action"));
        return resource;
    }
}
