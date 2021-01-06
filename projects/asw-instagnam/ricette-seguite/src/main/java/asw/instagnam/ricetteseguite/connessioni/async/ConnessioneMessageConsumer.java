package asw.instagnam.ricetteseguite.connessioni.async;

import asw.instagnam.ricetteseguite.domain.Connessione;
import asw.instagnam.ricetteseguite.domain.ConnessioniService;
import asw.instagnam.ricetteseguite.domain.RicetteSeguiteService;
import asw.instagnam.ricetteseguite.rest.RicetteSeguiteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ConnessioneMessageConsumer {

    private final Logger logger = Logger.getLogger(RicetteSeguiteController.class.toString());

    @Autowired
    @Qualifier("connessioniServiceImpl")
    ConnessioniService connessioniServiceImpl;

    @Autowired
    RicetteSeguiteService ricetteSeguiteService;

    @KafkaListener(topics = "${asw.kafka.topic.connessione.in}", groupId = "${asw.kafka.group-id.connessione.in}")
    public void listenerForConnessione(ConnessioneCreatedEvent connessioneCreatedEvent) {
        logger.info(String.format("$$$$ => listenerForConnessione = Consumed message: %s", connessioneCreatedEvent));
        connessioniServiceImpl.save(new Connessione(connessioneCreatedEvent.getId(), connessioneCreatedEvent.getFollower(), connessioneCreatedEvent.getFollowed()));
    }

    @KafkaListener(topics = "${asw.kafka.topic.connessione.in}",  groupId = "${asw.kafka.group-id.connessione.ricetteseguite.in}")
    public void listenerForRicetteSeguite(ConnessioneCreatedEvent connessioneCreatedEvent) {
        logger.info(String.format("$$$$ => listenerForRicetteSeguite = Consumed message: %s", connessioneCreatedEvent));
        ricetteSeguiteService.saveNewConnessione(connessioneCreatedEvent.getFollower(), connessioneCreatedEvent.getFollowed());
    }


}
