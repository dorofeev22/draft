package ru.dorofeev22.draft.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.dorofeev22.draft.core.BaseRepository;
import ru.dorofeev22.draft.core.EntityBaseService;
import ru.dorofeev22.draft.core.searching.SearchOperation;
import ru.dorofeev22.draft.domain.User;
import ru.dorofeev22.draft.repository.UserRepository;
import ru.dorofeev22.draft.service.model.UserCreationModel;
import ru.dorofeev22.draft.service.model.UserResponse;

import javax.annotation.PostConstruct;

import static java.util.Collections.singletonMap;

@Service
public class UserService extends EntityBaseService<User, UserCreationModel, UserResponse> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @PostConstruct
    public void init() {
        super.init();
        searchOperationMap.put("creationMoment", SearchOperation.BETWEEN);
        searchParameterFunctionMap.put("creationMoment", super::createLocalDateTimes);
    }

    @Override
    protected BaseRepository<User> getRepository() {
        return userRepository;
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected Class<UserResponse> getOutcomeModelClass() {
        return UserResponse.class;
    }
    
    @Override
    protected void beforeCreation(@NotNull final UserCreationModel model) {
        findAndThrow(singletonMap("login", new String[]{model.getLogin()}));
    }
    
}