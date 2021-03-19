package ru.dorofeev22.draft.core.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import ru.dorofeev22.draft.core.error.service.MessageHelper;

public class ErrorHandlingConfig {

    @Bean
    public MessageHelper messageHelper(@NotNull AbstractResourceBasedMessageSource messageSource) {
        return new MessageHelper(messageSource);
    }

}