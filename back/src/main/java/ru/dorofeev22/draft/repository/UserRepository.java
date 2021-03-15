package ru.dorofeev22.draft.repository;

import org.springframework.stereotype.Repository;
import ru.dorofeev22.draft.core.BaseRepository;
import ru.dorofeev22.draft.domain.User;

@Repository
public interface UserRepository extends BaseRepository<User> {

}