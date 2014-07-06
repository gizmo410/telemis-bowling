package be.milieuinfo.midas.rest.controller.controle;

import com.google.common.collect.Lists;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @since 02/07/14
 */
@Controller
@RequestMapping(ControleController.ROOT_URL)
public class ControleController {

    public static final String ROOT_URL = "/controles/{id}/action";

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    ResourceSupport listCommands(@PathVariable("id") String id) {
        final ResourceSupport result = new ResourceSupport();
        result.add(commandLinks(id));
        return result;
    }

    private List<Link> commandLinks(String id) {
        final List<Link> links = Lists.newArrayList();
        links.add(linkTo(methodOn(ControleController.class).listCommands(id)).withSelfRel());
        links.add(linkTo(methodOn(ControleController.class).update(id)).withRel("wijzigControle"));
        links.add(linkTo(methodOn(ControleController.class).delete(id)).withRel("verwijderControle"));
        return links;
    }

    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity update(@PathVariable("id") String id) {
        //TODO Implement here...
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity delete(@PathVariable("id") String id) {
        //TODO Implement here...
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


}
