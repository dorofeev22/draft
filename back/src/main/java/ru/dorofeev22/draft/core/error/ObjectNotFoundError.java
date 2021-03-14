package ru.dorofeev22.draft.core.error;

import java.util.UUID;

public class ObjectNotFoundError extends BaseError {
    private final String objectClass;
    private final String id;
    
    public ObjectNotFoundError(String objectClass, String id) {
        this.objectClass = objectClass;
        this.id = id;
    }
    
    public String getObjectClass() {
        return objectClass;
    }
    
    public String getId() {
        return id;
    }
}