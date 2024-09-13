package ru.netology.mynewservicemoneytransfer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MyNewServiceMoneyTransferApplicationTests {

    private final TestRestTemplate restTemplate;
    private static HttpHeaders headers = new HttpHeaders();

    public MyNewServiceMoneyTransferApplicationTests() {
        this.restTemplate = new TestRestTemplate();
    }

    public static GenericContainer<?> container = new GenericContainer<>("myservice:1.0")
            .withExposedPorts(5500);

    @BeforeAll
    public static void staticInit() {
        container.start();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void transferOkTest() {
        String json = "{\n" +
                "  \"cardFromNumber\": \"1111111111111111\",\n" +
                "  \"cardToNumber\": \"2222222222222222\",\n" +
                "  \"cardFromCVV\": \"111\",\n" +
                "  \"cardFromValidTill\": \"09/26\",\n" +
                "  \"amount\": {\n" +
                "    \"currency\": \"RUR\",\n" +
                "    \"value\": 333\n" +
                "  }\n" +
                "}";

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String response = restTemplate.postForObject(
                "http://localhost:" + container.getMappedPort(5500) + "/transfer",request, String.class);
        assertEquals("{\"operationId\":\"0\"}", response);
    }

    @Test
    public void badRequestTest() {
        String json = "{\n" +
                "  \"cardFromNumber\": \"1111111111111111\",\n" +
                "  \"cardToNumber\": \"1111111111111111\",\n" +
                "  \"cardFromCVV\": \"111\",\n" +
                "  \"cardFromValidTill\": \"09/26\",\n" +
                "  \"amount\": {\n" +
                "    \"currency\": \"RUR\",\n" +
                "    \"value\": 333\n" +
                "  }\n" +
                "}";

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String response = restTemplate.postForObject(
                "http://localhost:" + container.getMappedPort(5500) + "/transfer",request, String.class);
        assertEquals("{\"message\":\"Ошибка при введении данных.\"}", response);
    }

    @Test
    public void InternalServerErrorTest() {
        String json = "{\n" +
                "  \"cardFromNumber\": \"1111111111111111\",\n" +
                "  \"cardToNumber\": \"2222222222222222\",\n" +
                "  \"cardFromCVV\": \"111\",\n" +
                "  \"cardFromValidTill\": \"09/26\",\n" +
                "  \"amount\": {\n" +
                "    \"currency\": \"RUR\",\n" +
                "    \"value\": 3333333333333\n" +
                "  }\n" +
                "}";

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String response = restTemplate.postForObject(
                "http://localhost:" + container.getMappedPort(5500) + "/transfer",request, String.class);
        assertEquals("{\"message\":\"Внутренняя ошибка сервера\"}", response);
    }

    /*
    @Test
    void confirmOkTest() {
        String json = "{\n" +
                "  \"operationId\": \"0\",\n" +
                "  \"code\": \"0000\"\n" +
                "}";

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String response = restTemplate.postForObject(
                "http://localhost:" + container.getMappedPort(5500) + "/confirmOperation",request, String.class);
        assertEquals("{\"operationId\":\"0\",\"code\":\"0000\"}", response);
    }

    @Test
    void confirmErrorTest() {
        String json = "{\n" +
                "  \"operationId\": \"0\",\n" +
                "  \"code\": \"1111\"\n" +
                "}";

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String response = restTemplate.postForObject(
                "http://localhost:" + container.getMappedPort(5500) + "/confirmOperation",request, String.class);
        assertEquals("{\"message\":\"Ошибка подтверждения операции.\"}", response);
    }
    */

}
