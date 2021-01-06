package asw.instagnam.ricetteseguite.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RicetteSeguite {

    //TODO we can create a explicit relations with Connessione and Ricetta

    @EmbeddedId
    private RicetteSeguitePK ricetteSeguitePK;
    private String autoreRicetta;
    private String titoloRicetta;

    public RicetteSeguite(String utenteFollower, Long idRicetta, String autoreRicetta, String titoloRicetta) {
        this.ricetteSeguitePK = new RicetteSeguitePK(idRicetta, utenteFollower);
        this.autoreRicetta = autoreRicetta;
        this.titoloRicetta = titoloRicetta;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RicetteSeguitePK implements Serializable {

        @Column(nullable = false)
        private Long idRicetta;

        @Column(nullable = false)
        private String utenteFollower;

    }


}
