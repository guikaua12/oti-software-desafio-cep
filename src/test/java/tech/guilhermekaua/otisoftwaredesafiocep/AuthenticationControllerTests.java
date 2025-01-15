package tech.guilhermekaua.otisoftwaredesafiocep;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import tech.guilhermekaua.otisoftwaredesafiocep.dao.UserRepository;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Testcontainers
@Transactional
class AuthenticationControllerTests {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17"));

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

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
    void shouldRegister() throws Exception {
        final JSONObject body = new JSONObject(Map.of(
                "username", "some_username",
                "password", "some_password"
        ));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toJSONString())
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        assertNotNull(userRepository.findByUsername("some_username").orElse(null));
    }

    @Test
    void shouldNotRegisterIfUsernameAlreadyExists() throws Exception {
        final JSONObject body = new JSONObject(Map.of(
                "username", "some_username",
                "password", "some_password"
        ));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toJSONString())
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toJSONString())
                ).andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode").value("user_already_exists"))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Usuário já existente."));
    }

    @Test
    void shouldLogin() throws Exception {
        final JSONObject body = new JSONObject(Map.of(
                "username", "some_username",
                "password", "some_password"
        ));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toJSONString())
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());


        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toJSONString())
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void shouldNotLoginIfUsernameDoesNotExists() throws Exception {
        final JSONObject body = new JSONObject(Map.of(
                "username", "some_username",
                "password", "some_password"
        ));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toJSONString())
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("incorrect_credentials"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Usuário ou senha incorretos."));
    }

    @Test
    void shouldNotLoginIfIncorrectPassword() throws Exception {
        final JSONObject registerBody = new JSONObject(Map.of(
                "username", "some_username",
                "password", "some_password"
        ));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerBody.toJSONString())
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        final JSONObject loginBody = new JSONObject(Map.of(
                "username", "some_username",
                "password", "incorrect_password"
        ));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody.toJSONString())
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("incorrect_credentials"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Usuário ou senha incorretos."));
    }
}
