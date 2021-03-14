package ru.dorofeev22.draft.service;

import org.springframework.stereotype.Service;
import ru.dorofeev22.draft.core.searching.SearchOperation;
import ru.dorofeev22.draft.domain.User;
import ru.dorofeev22.draft.repository.BaseRepository;
import ru.dorofeev22.draft.repository.UserRepository;
import ru.dorofeev22.draft.service.model.UserCreationModel;
import ru.dorofeev22.draft.service.model.UserResponse;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    
}