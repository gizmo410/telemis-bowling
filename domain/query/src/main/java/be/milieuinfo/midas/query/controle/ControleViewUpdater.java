package be.milieuinfo.midas.query.controle;

import be.milieuinfo.midas.domain.api.controle.event.ControleAangemaakt;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @since 25/06/14
 */
@Service
public class ControleViewUpdater {

    @Autowired
    private ControleViewRepository controleViewRepository;

    @EventHandler
    @Transactional
    public void handle(final ControleAangemaakt event) {

        final ControleView nieuwControle = ControleView.newBuilder()
                .uitvoerendToezichthouder(event.getUitvoerendToezichthouder())
                .onderwerp(event.getOnderwerp())
                .alleenBesluit(event.getAlleenBesluit())
                .programmaOnderdelen(event.getProgrammaOnderdelen())
                .internBegeleiders(event.getInternBegeleider())
                .withId(event.getControleId())
                .build();

        controleViewRepository.save(nieuwControle);

    }

}
