package br.ufes.participacidadao.services;

import br.ufes.participacidadao.dto.IBGEMunicipioDTO;
import br.ufes.participacidadao.models.DadosEnriquecidos;
import br.ufes.participacidadao.dto.IBGECidadeDadosDTO;

import org.junit.Test; // JUnit 4
import static org.junit.Assert.*; // Assertions JUnit 4

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class IBGEServiceTest {

    @Autowired
    private IBGEService ibgeService;

    @MockBean
    RestTemplate restTemplate;

    @Test

    void testBuscarMunicipioPorNome() {
        Optional<IBGEMunicipioDTO> municipio = ibgeService.buscarMunicipioPorNome("Vitória", null);

        assertTrue(municipio.isPresent());
        assertEquals("Vitória", municipio.get().getNome());
    }

    @Test
    void testBuscarDadosCompletosCidade() {
        Optional<IBGECidadeDadosDTO> opt = ibgeService.buscarDadosCompletosCidade("Vitória");

        assertTrue(opt.isPresent());
        IBGECidadeDadosDTO dados = opt.get();

        assertEquals("Vitória", dados.getNome());
        assertEquals("ES", dados.getUf());
        assertEquals("Sudeste", dados.getRegiao());
    }
}