package ru.dorofeev22.draft.domain;

import ru.dorofeev22.draft.core.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String name;
    private String login;
    private String password;
    private final LocalDateTime creationMoment = LocalDateTime.now();

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreationMoment() {
        return creationMoment;
    }
}