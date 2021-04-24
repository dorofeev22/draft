package ru.dorofeev22.draft.core.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseActions;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateServiceTest {

    @Autowired
    private RestTemplateService restTemplateService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private final static String BASE_URL = "http://localhost:8080";
    private MockRestServiceServer mockServer;

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void getTest() throws URISyntaxException, JsonProcessingException {
        final BodyModel expectedResponse = new BodyModel(1, "John");
        final String url = createUrl("/models");
        adjustServerForResponseBody(expectedResponse, url, HttpMethod.GET);
        final BodyModel actualResponse = restTemplateService.executeGetQuery(url, null, BodyModel.class);
        mockServer.verify();
        mockServer.reset();
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
    }

    @Test
    public void postTest() throws URISyntaxException, JsonProcessingException {
        final BodyModel expectedRequest = new BodyModel(2, "Jack");
        final String url = createUrl("/models");
        adjustServerForRequestBody(expectedRequest, url, HttpMethod.POST);
        restTemplateService.executePostQuery(url, expectedRequest, null);
        mockServer.verify();
        mockServer.reset();
    }

    @SuppressWarnings("SameParameterValue")
    private String createUrl(final String path) {
        return StringUtils.isNotBlank(path) ? BASE_URL.concat(path) : BASE_URL;
    }

    @SuppressWarnings("SameParameterValue")
    private <R> void adjustServerForResponseBody(R expectedResponse, String url, HttpMethod httpMethod) throws JsonProcessingException, URISyntaxException {
        adjustMockServer(url, httpMethod)
                .andRespond(
                        withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(objectMapper.writeValueAsString(expectedResponse)));
    }

    @SuppressWarnings("SameParameterValue")
    private <R> void adjustServerForRequestBody(R expectedRequest, String url, HttpMethod httpMethod) throws JsonProcessingException, URISyntaxException {
        adjustMockServer(url, httpMethod)
                .andExpect(content().json(objectMapper.writeValueAsString(expectedRequest)))
                .andRespond(withStatus(HttpStatus.OK));
    }

    private ResponseActions adjustMockServer(String url, HttpMethod httpMethod) throws URISyntaxException {
        return mockServer
                .expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(httpMethod));

    }
}