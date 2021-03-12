package ru.dorofeev22.draft.endpoint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.dorofeev22.draft.core.UrlConstants;
import ru.dorofeev22.draft.domain.User;
import ru.dorofeev22.draft.service.model.UserCreationModel;
import ru.dorofeev22.draft.service.model.UserResponse;

import static org.junit.Assert.assertEquals;
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

    private UserCreationModel createUserCreationModel(String name, String login) {
        UserCreationModel userCreationModel = new UserCreationModel();
        userCreationModel.setName(name);
        userCreationModel.setLogin(login);
        userCreationModel.setPassword("1_ghyjk^9");
        return userCreationModel;
    }

}
