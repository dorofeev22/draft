package ru.dorofeev22.draft.endpoint;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
import ru.dorofeev22.draft.service.model.PageModel;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static ru.dorofeev22.draft.core.utils.WebUtils.createPath;

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
        return map(getById(id, OK_STATUS));
    }

    protected PageModel<R> getItemsPage(List<ImmutablePair<String, String>> parameters) throws Exception {
        return toPageResponse(get(parameters, OK_STATUS));
    }

    protected MvcResult getById(String id, ResultMatcher resultMatcher) throws Exception {
        return get(getPath().concat(SLASH).concat(id), resultMatcher);
    }
    
    protected MvcResult get(List<ImmutablePair<String, String>> parameters, ResultMatcher resultMatcher) throws Exception {
        return get(getPath().concat(createPath(parameters)), resultMatcher);
    }
    
    protected MvcResult get(String url, ResultMatcher resultMatcher) throws Exception {
        return mockMvc
                .perform(MockMvcRequestBuilders.get(url))
                .andExpect(resultMatcher)
                .andReturn();
    }

    protected R post(C creationModel) throws Exception {
        return map(post(createRequestBody(creationModel), OK_STATUS));
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
    
    private R map(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), getResponseClass());
    }
    
    private PageModel<R> toPageResponse(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructParametricType(PageModel.class, getResponseClass()));
    }

    private String createRequestBody(C creationModel) throws JsonProcessingException {
        return objectMapper.writeValueAsString(creationModel);
    }

}