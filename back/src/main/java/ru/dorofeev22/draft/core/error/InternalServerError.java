package ru.dorofeev22.draft.core.error;

import java.util.ArrayList;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class InternalServerError extends BaseError {

    public InternalServerError() {
        super(new String[1]);
        getArgs()[0] = createErrorId();
    }

    private String createErrorId() {
        return String.join("-", new ArrayList<String>() {{
            add(randomNumeric(3));
            add(randomNumeric(3));
            add(randomNumeric(3));
        }});
    }

    public String getErrorId() {
        return getArgs()[0];
    }

}