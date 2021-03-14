package ru.dorofeev22.draft.endpoint;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev22.draft.core.constant.UrlConstants;
import ru.dorofeev22.draft.service.UserService;
import ru.dorofeev22.draft.service.model.PageModel;
import ru.dorofeev22.draft.service.model.UserCreationModel;
import ru.dorofeev22.draft.service.model.UserResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = UrlConstants.USERS, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserEndpoint {

    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse post(@RequestBody final UserCreationModel userCreationModel) {
        return userService.createAndGetOutcome(userCreationModel);
    }
    
    @GetMapping("/{id}")
    public UserResponse get(@PathVariable UUID id) {
        return userService.getOutcomeModel(id);
    }

    @GetMapping
    public PageModel<UserResponse> find(HttpServletRequest httpServletRequest) {
        return userService.searchOutcomes(httpServletRequest);
    }


}
