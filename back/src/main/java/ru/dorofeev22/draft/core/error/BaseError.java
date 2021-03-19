package ru.dorofeev22.draft.core.error;

public class BaseError extends RuntimeException {

    private final String[] args;

    public BaseError(String... args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

}