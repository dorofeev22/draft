package ru.dorofeev22.draft.core.rest;

public class BodyModel implements IRequestModel{

    public BodyModel() {
        // for jackson
    }

    public BodyModel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toLogString() {
        return String.format("Request {id: %s, name: %s }", id, name);
    }
}