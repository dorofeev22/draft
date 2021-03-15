package ru.dorofeev22.draft.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.dorofeev22.draft.core.BaseEntity;

import java.util.UUID;

public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, UUID>, JpaSpecificationExecutor<E> {

}