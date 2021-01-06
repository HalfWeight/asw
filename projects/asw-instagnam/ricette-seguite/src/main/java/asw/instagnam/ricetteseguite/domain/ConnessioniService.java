package asw.instagnam.ricetteseguite.domain;

import java.util.*; 

public interface ConnessioniService {

	public Collection<Connessione> getConnessioniByFollower(String follower);

	public Collection<Connessione> getConnessioniByFollowed(String follower);

	public void save(Connessione connessione);

}
