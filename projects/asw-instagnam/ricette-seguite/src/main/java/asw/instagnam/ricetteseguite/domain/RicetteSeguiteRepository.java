package asw.instagnam.ricetteseguite.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RicetteSeguiteRepository extends JpaRepository<RicetteSeguite, RicetteSeguite.RicetteSeguitePK> {

    Collection<RicetteSeguite> findByRicetteSeguitePK_IdRicetta(Long idRicetta);

    Collection<RicetteSeguite> findByRicetteSeguitePK_UtenteFollower(String follower);

}
