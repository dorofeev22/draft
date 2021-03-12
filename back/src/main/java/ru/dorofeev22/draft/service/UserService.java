package ru.dorofeev22.draft.service;

import org.springframework.stereotype.Service;
import ru.dorofeev22.draft.domain.User;
import ru.dorofeev22.draft.repository.BaseRepository;
import ru.dorofeev22.draft.repository.UserRepository;
import ru.dorofeev22.draft.service.model.UserCreationModel;
import ru.dorofeev22.draft.service.model.UserResponse;

@Service
public class UserService extends EntityBaseService<User, UserCreationModel, UserResponse> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    protected Class<UserResponse> getResponseClass() {
        return UserResponse.class;
    }

}