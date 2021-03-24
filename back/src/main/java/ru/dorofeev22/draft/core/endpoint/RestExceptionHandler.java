package ru.dorofeev22.draft.core.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.dorofeev22.draft.core.error.BaseError;
import ru.dorofeev22.draft.core.error.service.ErrorModel;
import ru.dorofeev22.draft.core.error.service.MessageHelper;

@RestControllerAdvice
public class RestExceptionHandler {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MessageHelper messageHelper;

    public RestExceptionHandler(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseError.class)
    public ErrorModel handleRuntime(BaseError e) {
        return createErrorModel(e);
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorModel handleRuntime(Throwable t) {
        return createErrorModel(t);
    }

    private ErrorModel createErrorModel(Throwable t) {
        final ErrorModel errorModel = new ErrorModel(t.getClass().getSimpleName(), messageHelper.localize(t));
        log.error("Api calling error: {}", errorModel);
        return errorModel;
    }

}