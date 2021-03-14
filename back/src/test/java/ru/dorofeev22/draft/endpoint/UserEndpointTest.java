package ru.dorofeev22.draft.endpoint;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.dorofeev22.draft.core.constant.UrlConstants;
import ru.dorofeev22.draft.domain.User;
import ru.dorofeev22.draft.service.model.PageModel;
import ru.dorofeev22.draft.service.model.UserCreationModel;
import ru.dorofeev22.draft.service.model.UserResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEndpointTest extends BaseTestRestService<User, UserCreationModel, UserResponse> {

    @Override
    protected Class<UserResponse> getResponseClass() {
        return UserResponse.class;
    }

    @Override
    protected String getPath() {
        return UrlConstants.USERS;
    }

    @Test
    public void createUserTest() throws Exception {
        String name = "Mr. Jackson";
        String login = "jhon1986";
        UserResponse userResponse = post(createUserCreationModel(name, login));
        assertEquals(name, userResponse.getName());
        assertEquals(login, userResponse.getLogin());
        assertNotNull(userResponse.getCreationMoment());
        assertNotNull(userResponse.getId());
        assertNull(userResponse.getPassword());
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

    private UserCreationModel createUserCreationModel(String name, String login) {
        UserCreationModel userCreationModel = new UserCreationModel();
        userCreationModel.setName(name);
        userCreationModel.setLogin(login);
        userCreationModel.setPassword("1_ghyjk^9");
        return userCreationModel;
    }
    
    private List<ImmutablePair<String, String>> createLikeSearchParameters() {
        return new ArrayList<ImmutablePair<String, String>>() {{
            add(new ImmutablePair<>("name", "r"));
            add(new ImmutablePair<>("login", "ey"));
        }};
    }

    private List<ImmutablePair<String, String>> createInSearchParameters() {
        return new ArrayList<ImmutablePair<String, String>>() {{
            add(new ImmutablePair<>("id", "087053ae-c31f-4842-baba-9ebafe3ee594"));
            add(new ImmutablePair<>("id", "087053ae-c31f-4842-baba-9ebafe3ee59b"));
        }};
    }

    private List<ImmutablePair<String, String>> createDateSearchParameters() {
        return new ArrayList<ImmutablePair<String, String>>() {{
            add(new ImmutablePair<>("creationMoment", "2021-03-13T01:00:00.000"));
            add(new ImmutablePair<>("creationMoment", "2021-03-14T22:00:00.000"));
        }};
    }

}
