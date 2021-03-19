package ru.dorofeev22.draft.endpoint;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.test.web.servlet.MvcResult;
import ru.dorofeev22.draft.core.BaseEntity;
import ru.dorofeev22.draft.core.endpoint.PageModel;

import java.io.UnsupportedEncodingException;
import java.util.List;

public abstract class BaseTestEntityRestService<E extends BaseEntity, C, R> extends BaseTestRestService{

    protected abstract Class<R> getResponseClass();

    protected R getItem(String id) throws Exception {
        return map(getById(id, OK_STATUS));
    }

    protected PageModel<R> getItemsPage(List<ImmutablePair<String, String>> parameters) throws Exception {
        return toPageResponse(get(parameters, OK_STATUS));
    }


    protected R post(C creationModel) throws Exception {
        return map(post(createRequestBody(creationModel), OK_STATUS));
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