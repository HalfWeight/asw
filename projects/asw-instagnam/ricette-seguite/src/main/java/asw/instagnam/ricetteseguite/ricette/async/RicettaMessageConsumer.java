package asw.instagnam.ricetteseguite.ricette.async;

import asw.instagnam.ricetteseguite.domain.Ricetta;
import asw.instagnam.ricetteseguite.domain.RicetteSeguiteService;
import asw.instagnam.ricetteseguite.domain.RicetteService;
import asw.instagnam.ricetteseguite.rest.RicetteSeguiteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RicettaMessageConsumer {

    private final Logger logger = Logger.getLogger(RicetteSeguiteController.class.toString());

    @Autowired
    @Qualifier("ricetteServiceImpl")
    RicetteService ricetteServiceImpl;

    @Autowired
    RicetteSeguiteService ricetteSeguiteService;

    @KafkaListener(topics = "${asw.kafka.topic.ricetta.in}", groupId = "${asw.kafka.group-id.ricetta.in}")
    public void listener(RicettaCreatedEvent ricettaCreatedEvent) {
        logger.info(String.format("$$$$ => Consumed message: %s", ricettaCreatedEvent));
        ricetteServiceImpl.save(new Ricetta(ricettaCreatedEvent.getId(), ricettaCreatedEvent.getAutore(), ricettaCreatedEvent.getTitolo()));
    }

    @KafkaListener(topics = "${asw.kafka.topic.ricetta.in}", groupId = "${asw.kafka.group-id.ricetta.ricetteseguite.in}")
    public void listener2(RicettaCreatedEvent ricettaCreatedEvent) {
        logger.info(String.format("$$$$ => Consumed message: %s", ricettaCreatedEvent));
        ricetteSeguiteService.updateByRicetta(ricettaCreatedEvent.getId(), ricettaCreatedEvent.getAutore(), ricettaCreatedEvent.getTitolo());
        ricetteSeguiteService.saveNewRicetta(ricettaCreatedEvent.getId(), ricettaCreatedEvent.getAutore(), ricettaCreatedEvent.getTitolo());
    }

}
