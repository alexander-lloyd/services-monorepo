package com.alexlloyd.configservice;

import java.net.URI;
import java.util.List;
import java.util.Map;

import com.alexlloyd.configservice.api.ConfigDAO;
import com.alexlloyd.configservice.api.ConfigService;
import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.exception.ConfigDoesNotExistException;
import com.alexlloyd.configservice.model.Response;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc
@ContextConfiguration(initializers = ConfigServiceIT.Initializer.class)
@ExtendWith({SpringExtension.class})
@Testcontainers
@SpringBootTest(
        classes = ConfigServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfigServiceIT {
    private static final String KEY = "Config-Key";
    private static final String VALUE = "Config-Value";
    private static final String CONFIG_NAME = "config-name";
    private static RestTemplate restTemplate;

    @Container
    private static final GenericContainer redis = new GenericContainer("redis:3.0.6")
            .withExposedPorts(6379)
            .waitingFor(Wait.defaultWaitStrategy());

    static {
        System.setProperty("io.netty.noUnsafe", "true");
        redis.start();
    }


    @Autowired
    private ConfigService configService;

    @Autowired
    private ConfigDAO configDAO;

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void beforeAll() {
        restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);
    }

    @BeforeEach
    public void beforeEach() throws ConfigAlreadyExistsException, ConfigDoesNotExistException {
        assertTrue(redis.isRunning());

        this.configDAO.deleteAll();
        this.configService.createConfig(CONFIG_NAME);
        this.configService.updateConfig(CONFIG_NAME, KEY, VALUE);
    }

    @Test
    @DisplayName("should get the list of configs.")
    public void testGetConfigList() {
        String url = "http://localhost:" + port + "/config/";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        String json = response.getBody();

        List<String> configs = JsonPath.read(json, "$.data.configs");

        assertEquals(1, configs.size());
        assertEquals(CONFIG_NAME, configs.get(0));
    }

    @Test
    @DisplayName("should thrown an error if trying to get a config that does not exist.")
    public void testGetConfigError() {
        String url = "http://localhost:" + port + "/config/abc";

        assertThrows(HttpClientErrorException.NotFound.class, () -> restTemplate.getForEntity(url, Response.class));
    }

    @Test
    @DisplayName("should get a config.")
    public void testGetConfig() {
        String url = "http://localhost:" + port + "/config/" + CONFIG_NAME;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        String json = response.getBody();

        Map<String, String> configs = JsonPath.read(json, "$.data.config");

        assertEquals(1, configs.size());
        assertEquals(VALUE, configs.get(KEY));
    }

    @Test
    @DisplayName("should get a value from the config.")
    public void testGetValue() {
        UriTemplate urlTemplate = new UriTemplate("http://localhost:{port}/config/{configName}/{key}");
        URI url = urlTemplate.expand(Map.of("port", port, "configName", CONFIG_NAME, "key", KEY));

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        String json = response.getBody();

        String value = JsonPath.read(json, "$.data");

        assertEquals(VALUE, value);
    }

    @Test
    @DisplayName("should create a config.")
    public void testCreateConfig() {
        String configName = "a";
        UriTemplate urlTemplate = new UriTemplate("http://localhost:{port}/config/{configName}");
        URI url = urlTemplate.expand(Map.of("port", port, "configName", configName));

        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        String json = response.getBody();

        String value = JsonPath.read(json, "$.data");

        assertTrue(value.contains("created"));

        assertEquals(2, this.configDAO.getConfigCount());
    }

    @Test
    @DisplayName("should throw an exception when trying to create a config that already exists.")
    public void testCreateConfigException() {
        UriTemplate urlTemplate = new UriTemplate("http://localhost:{port}/config/{configName}");
        URI url = urlTemplate.expand(Map.of("port", port, "configName", CONFIG_NAME));

        assertThrows(HttpClientErrorException.BadRequest.class, () -> restTemplate.postForEntity(url, null, String.class));
    }

    @Test
    @DisplayName("should update a config.")
    public void testUpdateConfig() throws ConfigDoesNotExistException {
        String configValue = "value";
        UriTemplate urlTemplate = new UriTemplate("http://localhost:{port}/config/{configName}/?key={key}&value={value}");
        URI url = urlTemplate.expand(Map.of("port", port, "configName", CONFIG_NAME, "key", KEY, "value", configValue));

        String json = restTemplate.patchForObject(url, null, String.class);

        String value = JsonPath.read(json, "$.data");

        assertTrue(value.contains("Update config"));

        assertEquals(configValue, this.configService.getConfig(CONFIG_NAME).getValue(KEY));
    }

    @Test
    @DisplayName("should delete a config.")
    public void testDeleteConfig() {
        UriTemplate urlTemplate = new UriTemplate("http://localhost:{port}/config/{configName}");
        URI url = urlTemplate.expand(Map.of("port", port, "configName", CONFIG_NAME));

        restTemplate.delete(url);

        assertEquals(0, this.configService.getConfigNames().size());
    }

    @Test
    @DisplayName("should delete a value from config.")
    public void testDeleteValue() throws ConfigDoesNotExistException {
        UriTemplate urlTemplate = new UriTemplate("http://localhost:{port}/config/{configName}/{key}");
        URI url = urlTemplate.expand(Map.of("port", port, "configName", CONFIG_NAME, "key", KEY));

        restTemplate.delete(url);

        assertEquals(0, this.configService.getConfig(CONFIG_NAME).getConfigMap().size());
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.redis.host=" + redis.getContainerIpAddress(),
                    "spring.redis.port=" + redis.getMappedPort(6379),
                    "spring.redis.password="
            );
            values.applyTo(configurableApplicationContext);
        }
    }
}
