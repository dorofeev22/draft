package ru.dorofeev22.draft.endpoint;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.dorofeev22.draft.domain.BaseEntity;

import java.io.UnsupportedEncodingException;

@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class BaseTestRestService<E extends BaseEntity, C, R> {

    private final static ResultMatcher OK_STATUS = MockMvcResultMatchers.status().isOk();
    private final static String SLASH = "/";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    protected abstract Class<R> getResponseClass();
    protected abstract String getPath();

    protected R getItem(String id) throws Exception {
        return mapResult(get(id, OK_STATUS));
    }

    protected MvcResult get(String id, ResultMatcher resultMatcher) throws Exception {
        return mockMvc
                .perform(MockMvcRequestBuilders.get(getPath().concat(SLASH).concat(id)))
                .andExpect(resultMatcher)
                .andReturn();
    }

    protected R post(C creationModel) throws Exception {
        return mapResult(post(createRequestBody(creationModel), OK_STATUS));
    }


    protected MvcResult post(String requestBody, ResultMatcher resultMatcher) throws Exception {
        return mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(getPath())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(resultMatcher)
                .andReturn();

    }

    private R mapResult(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), getResponseClass());
    }

    private String createRequestBody(C creationModel) throws JsonProcessingException {
        return objectMapper.writeValueAsString(creationModel);
    }

}