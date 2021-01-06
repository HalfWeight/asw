package asw.instagnam.ricetteseguite;

import asw.instagnam.ricetteseguite.domain.Connessione;
import asw.instagnam.ricetteseguite.domain.ConnessioneRepository;
import asw.instagnam.ricetteseguite.domain.Ricetta;
import asw.instagnam.ricetteseguite.domain.RicettaRepository;
import asw.instagnam.ricetteseguite.domain.RicetteSeguite;
import asw.instagnam.ricetteseguite.domain.RicetteSeguiteRepository;
import asw.instagnam.ricetteseguite.domain.RicetteSeguiteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
public class RicetteSeguiteServiceTest {

    @Autowired
    RicetteSeguiteService ricetteSeguiteService;

    @Autowired
    RicettaRepository ricettaRepository;

    @Autowired
    ConnessioneRepository connessioneRepository;

    @Autowired
    RicetteSeguiteRepository ricetteSeguiteRepository;

    @BeforeEach
    public void setUp() {
        ricettaRepository.deleteAll();
        connessioneRepository.deleteAll();
        ricetteSeguiteRepository.deleteAll();
    }

    @Test
    public void testCreaNuovaRicettaSeguita_DopoAverRicevutoUnEventoDiRicettaCreata() {
        connessioneRepository.save(new Connessione(1L, "Vito", "Tommaso"));
        connessioneRepository.save(new Connessione(2L, "Francesco", "Tommaso"));

        ricetteSeguiteService.saveNewRicetta(5L, "Tommaso", "Tortellini con brodo");
        List<RicetteSeguite> ricetteSeguite = ricetteSeguiteRepository.findAll();
        assertThat(ricetteSeguite).hasSize(2);
    }

    @Test
    public void testAggiornaRicetteSeguite_DopoAverRicevutoUnEventoDiRicettaCreata() {
        connessioneRepository.save(new Connessione(1L, "Vito", "Tommaso"));
        connessioneRepository.save(new Connessione(2L, "Francesco", "Tommaso"));

        ricettaRepository.save(new Ricetta(5L, "Tommaso", "Tortellini con panna"));
        ricetteSeguiteService.save("Vito", 5L, "Tommaso", "Tortellini con panna");
        ricetteSeguiteService.save("Francesco", 5L, "Tommaso", "Tortellini con panna");

        ricetteSeguiteService.updateByRicetta(5L, "Tommaso", "Tortellini con brodo");
        List<RicetteSeguite> ricetteSeguite = ricetteSeguiteRepository.findAll();
        assertThat(ricetteSeguite).hasSize(2);

        List<String> titoliRicette = ricetteSeguite.stream().map(RicetteSeguite::getTitoloRicetta).distinct().collect(toList());
        assertThat(titoliRicette).hasSize(1);
        assertThat(titoliRicette.get(0)).isEqualTo("Tortellini con brodo");
    }

    @Test
    public void testCreaNuovaRicetteSeguite_DopoAverRicevutoUnEventoDiConnessioneCreata() {
        ricettaRepository.save(new Ricetta(5L, "Achille", "Bistecca alla fiorentina"));
        ricettaRepository.save(new Ricetta(6L, "Achille", "Pesce spada"));

        ricetteSeguiteService.saveNewConnessione("Vito", "Achille");
        List<RicetteSeguite> ricetteSeguite = ricetteSeguiteRepository.findAll();
        assertThat(ricetteSeguite).hasSize(2);

        assertThat(ricetteSeguite)
                .extracting(RicetteSeguite::getTitoloRicetta)
                .containsExactlyInAnyOrder("Bistecca alla fiorentina", "Pesce spada");
    }

}
