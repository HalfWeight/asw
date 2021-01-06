package asw.instagnam.ricetteseguite.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

@Service
public class RicetteSeguiteService {

    private final Logger logger = Logger.getLogger(RicetteSeguiteService.class.toString());

    @Autowired
    @Qualifier("connessioniServiceImpl")
    private ConnessioniService connessioniService;

    @Autowired
    @Qualifier("ricetteServiceImpl")
    private RicetteService ricetteService;

    @Autowired
    private RicetteSeguiteRepository ricetteSeguiteRepository;

    /* Trova le ricette (in formato breve) degli utenti seguiti da utente. */
    public Collection<Ricetta> getRicetteSeguite(String utente) {
        Collection<Ricetta> ricette = new ArrayList<>();
        Collection<Connessione> connessioni = connessioniService.getConnessioniByFollower(utente);
        for (Connessione connessione : connessioni) {
            String followed = connessione.getFollowed();
            //TODO -> Possiamo migliorare le performance eseguendo una sola query
            Collection<Ricetta> ricetteByFollowed = ricetteService.getRicetteByAutore(followed);
            ricette.addAll(ricetteByFollowed);
        }
        return ricette;
    }

    public RicetteSeguite save(String follower, Long idRicetta, String autoreRicetta, String titoloRicetta) {
        RicetteSeguite ricetteSeguite = new RicetteSeguite(follower, idRicetta, autoreRicetta, titoloRicetta);
        return ricetteSeguiteRepository.save(ricetteSeguite);
    }

    public void updateByRicetta(Long idRicetta, String autore, String titolo) {
        Collection<RicetteSeguite> ricetteSeguite = ricetteSeguiteRepository.findByRicetteSeguitePK_IdRicetta(idRicetta);
        if (CollectionUtils.isEmpty(ricetteSeguite)) {
            logger.info("No ricetteSeguite found by idRicetta " + idRicetta + " skipping update of RicetteSeguite");
            return;
        }
        ricetteSeguite.stream().peek(ricettaSeguita -> {
            ricettaSeguita.setAutoreRicetta(autore);
            ricettaSeguita.setTitoloRicetta(titolo);
        }).forEach(ricetteSeguiteRepository::save);
    }

    public void saveNewRicetta(Long idRicetta, String autore, String titolo) {
        Collection<Connessione> connessioni = connessioniService.getConnessioniByFollowed(autore);
        if (CollectionUtils.isEmpty(connessioni)) {
            logger.info("No connessioni found by autore " + autore + " skipping creation of new RicetteSeguite");
            return;
        }
        connessioni.stream()
                .map(connessione -> new RicetteSeguite(connessione.getFollower(), idRicetta, autore, titolo))
                .forEach(ricetteSeguiteRepository::save);
    }

    public void saveNewConnessione(String follower, String followed) {
        Collection<Ricetta> ricette = ricetteService.getRicetteByAutore(followed);
        if (CollectionUtils.isEmpty(ricette)) {
            logger.info("No ricette found by autore " + followed + " skipping create a new connection");
            return;
        }
        ricette.stream()
                .map(ricetta -> new RicetteSeguite(follower, ricetta.getId(), ricetta.getAutore(), ricetta.getTitolo()))
                .forEach(ricetteSeguiteRepository::save);
    }

}
