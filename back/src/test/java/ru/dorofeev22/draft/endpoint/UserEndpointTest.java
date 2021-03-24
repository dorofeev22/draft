package ru.dorofeev22.draft.endpoint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.dorofeev22.draft.core.constant.UrlConstants;
import ru.dorofeev22.draft.core.endpoint.PageModel;
import ru.dorofeev22.draft.core.error.service.ErrorModel;
import ru.dorofeev22.draft.domain.User;
import ru.dorofeev22.draft.service.model.UserRequest;
import ru.dorofeev22.draft.service.model.UserResponse;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;
import static ru.dorofeev22.draft.endpoint.UserTestHelper.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEndpointTest extends BaseTestEntityRestService<User, UserRequest, UserResponse> {

    @Override
    protected Class<UserResponse> getResponseClass() {
        return UserResponse.class;
    }

    @Override
    protected String getPath() {
        return UrlConstants.USERS;
    }

    @Test
    @Sql(scripts = {"/delete-all-users.sql"}, executionPhase = AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = ISOLATED))
    public void createUserTest() throws Exception {
        String name = "Mr. Jackson";
        String login = "jhon1986";
        UserResponse userResponse = post(UserTestHelper.createUserCreationModel(name, login));
        assertEquals(name, userResponse.getName());
        assertEquals(login, userResponse.getLogin());
        assertNotNull(userResponse.getCreationMoment());
        assertNotNull(userResponse.getId());
        assertNull(userResponse.getPassword());
    }
    
    @Test
    public void userNotFoundTest() throws Exception {
        ErrorModel errorModel = getByIdWithClientError(UUID.randomUUID().toString());
        assertEquals("ObjectNotFoundError", errorModel.getErrorCode());
    }
    
    @Test
    @Sql("/insert-user.sql")
    public void getUserTest() throws Exception {
        assertNotNull(getItem("087053ae-c31f-4842-baba-9ebafe3ee594"));
    }

    @Test
    @Sql("/insert-users.sql")
    public void getUsersTest() throws Exception {
        PageModel<UserResponse> result  = getItemsPage(createLikeSearchParameters());
        PageModel<UserResponse> results  = getItemsPage(createInSearchParameters());
        PageModel<UserResponse> results2  = getItemsPage(createDateSearchParameters());
        assertEquals(2 , result.getItems().size());
    }

    @Test
    public void getUsersParamErrorTest() throws Exception {
        ErrorModel errorModel = getWithClientError(createPageParameters(5, 11));
        assertEquals("BadParameterError", errorModel.getErrorCode());
    }

}