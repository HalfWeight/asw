package asw.instagnam.ricetteseguite.rest;

import asw.instagnam.ricetteseguite.domain.Connessione;
import asw.instagnam.ricetteseguite.domain.ConnessioneRepository;
import asw.instagnam.ricetteseguite.domain.Ricetta;
import asw.instagnam.ricetteseguite.domain.RicettaRepository;
import asw.instagnam.ricetteseguite.domain.RicetteSeguite;
import asw.instagnam.ricetteseguite.domain.RicetteSeguiteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
public class RicetteSeguiteControllerTest {

    @Autowired
    ConnessioneRepository connessioneRepository;

    @Autowired
    RicettaRepository ricettaRepository;

    @Autowired
    RicetteSeguiteController ricetteSeguiteController;

    @Autowired
    RicetteSeguiteRepository ricetteSeguiteRepository;

    @Test
    public void testGetRicetteSeguite() {
        ricetteSeguiteRepository.save(new RicetteSeguite("Olivia", 1L, "Ava", "Polpettine di tonno e ricotta"));
        ricetteSeguiteRepository.save(new RicetteSeguite("Olivia", 2L, "Isla", "Insalata di polipo"));
        ricetteSeguiteRepository.save(new RicetteSeguite("Olivia", 3L, "Michael", "Salmone croccante"));
        ricetteSeguiteRepository.save(new RicetteSeguite("James", 4L, "Michael", "Orata al forno"));

        Collection<Ricetta> ricette = ricetteSeguiteController.getRicetteSeguite("Olivia");
        assertThat(ricette).hasSize(3);
        assertThat(ricette)
                .extracting(Ricetta::getTitolo)
                .containsExactlyInAnyOrder("Insalata di polipo", "Polpettine di tonno e ricotta", "Salmone croccante");
    }

}
