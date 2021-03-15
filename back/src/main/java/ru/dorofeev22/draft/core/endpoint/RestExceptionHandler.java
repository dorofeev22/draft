package ru.dorofeev22.draft.core.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.dorofeev22.draft.core.error.BaseError;
import ru.dorofeev22.draft.core.error.ErrorModel;
import ru.dorofeev22.draft.core.error.ObjectNotFoundError;

@RestControllerAdvice
public class RestExceptionHandler {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseError.class)
    public ErrorModel handleRuntime(BaseError e) {
        return new ErrorModel(e.getClass().getCanonicalName(), e.getLocalizedMessage());
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorModel handleRuntime(Throwable t) {
        return new ErrorModel(t.getClass().getCanonicalName(), t.getLocalizedMessage());
    }

}