package ru.dorofeev22.draft.endpoint;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev22.draft.core.constant.UrlConstants;
import ru.dorofeev22.draft.core.endpoint.PageModel;
import ru.dorofeev22.draft.service.UserService;
import ru.dorofeev22.draft.service.model.UserRequest;
import ru.dorofeev22.draft.service.model.UserResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(value = UrlConstants.USERS, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserEndpoint {

    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse post(@RequestBody final UserRequest userRequest) {
        return userService.createAndGetOutcome(userRequest);
    }
    
    @PutMapping("/{id}")
    public UserResponse put(@PathVariable final UUID id, @RequestBody final UserRequest userRequest) {
        return userService.updateAnfGetOutcome(id, userRequest);
    }
    
    @GetMapping("/{id}")
    public UserResponse get(@PathVariable final UUID id) {
        return userService.getOutcomeModel(id);
    }

    @GetMapping
    public PageModel<UserResponse> find(final HttpServletRequest httpServletRequest) {
        return userService.searchOutcomes(httpServletRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final UUID id) {
        userService.delete(id);
    }

}