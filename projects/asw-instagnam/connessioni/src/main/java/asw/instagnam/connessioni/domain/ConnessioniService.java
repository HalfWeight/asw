package asw.instagnam.connessioni.domain;

import asw.instagnam.connessioni.async.MessagePublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Logger; 
import java.util.*; 

@Service
public class ConnessioniService {

	@Autowired
	private ConnessioniRepository connessioniRepository;

	@Autowired
	MessagePublisher<Connessione> connessioneMessagePublisher;

 	public Connessione createConnessione(String follower, String followed) {
		//TODO -> salviamo la connessione e poi pubblichiamo l'evento.
		// Se l'invio dell'evento non va a buon fine, ci possiamo permettere di perdere un evento?
		// Dobbiamo lanciare un eccezione e fare rollback?
		Connessione connessione = new Connessione(follower, followed); 
		connessione = connessioniRepository.save(connessione);
		connessioneMessagePublisher.sendMessage(connessione);
		return connessione;
	}

 	public Connessione getConnessione(Long id) {
		Connessione connessione = connessioniRepository.findById(id).orElse(null);
		return connessione;
	}

 	public Collection<Connessione> getConnessioni() {
		Collection<Connessione> connessioni = connessioniRepository.findAll();
		return connessioni;
	}

	public Collection<Connessione> getConnessioniByFollower(String follower) {
		Collection<Connessione> connessioni = connessioniRepository.findAllByFollower(follower);
		return connessioni;
	}

}
