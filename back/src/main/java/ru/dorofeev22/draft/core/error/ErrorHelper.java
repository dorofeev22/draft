package ru.dorofeev22.draft.core.error;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ErrorHelper {
    
    public static <T> ObjectNotFoundError createNotFountError(@NotNull final Class<T> objectClass, @NotNull final UUID id) {
        return new ObjectNotFoundError(objectClass.getName(), id.toString());
    }
}
