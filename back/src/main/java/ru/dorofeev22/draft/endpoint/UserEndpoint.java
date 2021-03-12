package ru.dorofeev22.draft.endpoint;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dorofeev22.draft.core.UrlConstants;
import ru.dorofeev22.draft.service.UserService;
import ru.dorofeev22.draft.service.model.UserCreationModel;
import ru.dorofeev22.draft.service.model.UserResponse;

@RestController
@RequestMapping(value = UrlConstants.USERS, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserEndpoint {

    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse post(@RequestBody final UserCreationModel userCreationModel) {
        return userService.createAndGetResponse(userCreationModel);
    }


}
