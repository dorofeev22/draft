package ru.dorofeev22.draft.service.model;

import javax.validation.constraints.Size;

import static ru.dorofeev22.draft.core.constant.ModelConstants.DEFAULT_STRING_LENGTH;

public class UserRequest {

    @Size(max = DEFAULT_STRING_LENGTH)
    private String name;

    @Size(max = DEFAULT_STRING_LENGTH)
    private String login;

    private String password;

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

    public void setPassword(String password) {
        this.password = password;
    }
}