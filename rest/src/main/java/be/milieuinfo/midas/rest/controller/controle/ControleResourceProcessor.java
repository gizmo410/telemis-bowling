package be.milieuinfo.midas.rest.controller.controle;

import be.milieuinfo.midas.query.controle.ControleView;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @since 02/07/14
 */
@Component
public class ControleResourceProcessor implements ResourceProcessor<Resource<ControleView>> {

    @Override
    public Resource<ControleView> process(Resource<ControleView> resource) {
        final ControleView content = resource.getContent();
		resource.add(linkTo(methodOn(ControleController.class).listCommands(content.getIdentifier())).withRel("action"));
        return resource;
    }

}
