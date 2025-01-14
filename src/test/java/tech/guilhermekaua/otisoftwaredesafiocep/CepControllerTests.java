package tech.guilhermekaua.otisoftwaredesafiocep;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import tech.guilhermekaua.otisoftwaredesafiocep.dao.CepRepository;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.CEP;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Testcontainers
@Transactional
class CepControllerTests {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17"));

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CepRepository cepRepository;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldFindByCepByCep() throws Exception {
        cepRepository.save(new CEP(null, "90160-092", "Avenida Ipiranga", "Porto Alegre", "RS"));

        mockMvc.perform(get("/cep/90160-092"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.cep").value("90160-092"))
                .andExpect(jsonPath("$.logradouro").value("Avenida Ipiranga"))
                .andExpect(jsonPath("$.cidade").value("Porto Alegre"))
                .andExpect(jsonPath("$.estado").value("RS"));
    }

    @Test
    void shouldReturnErrorIfCepNotFound() throws Exception {
        mockMvc.perform(get("/cep/99999-999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("cep_not_found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("CEP não encontrado."));
    }

    @Test
    void shouldReturnErrorIfInvalidCepFormat() throws Exception {
        mockMvc.perform(get("/cep/99999-9991"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("validation_error"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("CEP Inválido"));
    }

    @Test
    void shouldFilterCepsByCidadeAndLogradouro() throws Exception {
        var cep1 = new CEP(null, "90160-092", "Avenida Ipiranga", "Porto Alegre", "RS");
        var cep2 = new CEP(null, "91781-001", "Avenida Juca Batista", "Porto Alegre", "RS");
        var cep3 = new CEP(null, "02233-000", "Rua Capitão Rubens", "São Paulo", "SP");
        cepRepository.save(cep1);
        cepRepository.save(cep2);
        cepRepository.save(cep3);

        // cidade
        // lowercase
        var resultActions = mockMvc.perform(get("/cep/filter")
                        .queryParam("cidade", "porto alegre")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));

        assertPagedCep(resultActions, cep1, 0);
        assertPagedCep(resultActions, cep2, 1);

        // lowercase, uppercase and accentuation
        resultActions = mockMvc.perform(get("/cep/filter")
                        .queryParam("cidade", "PoRTó ÁLEGRÊ")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));

        assertPagedCep(resultActions, cep1, 0);
        assertPagedCep(resultActions, cep2, 1);

        // logradouro
        // lowercase
        resultActions = mockMvc.perform(get("/cep/filter")
                        .queryParam("logradouro", "rua")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));

        assertPagedCep(resultActions, cep3, 0);

        // lowercase, uppercase and accentuation
        resultActions = mockMvc.perform(get("/cep/filter")
                        .queryParam("logradouro", "RuÁ")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));

        assertPagedCep(resultActions, cep3, 0);

        // without cidade and logradouro (should return all ceps)
        resultActions = mockMvc.perform(get("/cep/filter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3));

        assertPagedCep(resultActions, cep1, 0);
        assertPagedCep(resultActions, cep2, 1);
        assertPagedCep(resultActions, cep3, 2);
    }

    ResultActions assertPagedCep(ResultActions resultActions, CEP cep, int index) throws Exception {
        return resultActions.andExpect(jsonPath("$.content[" + index + "].cep").value(cep.getCep()))
                .andExpect(jsonPath("$.content[" + index + "].logradouro").value(cep.getLogradouro()))
                .andExpect(jsonPath("$.content[" + index + "].cidade").value(cep.getCidade()))
                .andExpect(jsonPath("$.content[" + index + "].estado").value(cep.getEstado()));
    }

}
