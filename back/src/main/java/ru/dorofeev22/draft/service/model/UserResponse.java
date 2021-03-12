package ru.dorofeev22.draft.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponse extends UserCreationModel {

    private UUID id;
    private LocalDateTime creationMoment;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreationMoment() {
        return creationMoment;
    }

    public void setCreationMoment(LocalDateTime creationMoment) {
        this.creationMoment = creationMoment;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return null;
    }
}