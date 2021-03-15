package ru.dorofeev22.draft.core;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    private final UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }
}