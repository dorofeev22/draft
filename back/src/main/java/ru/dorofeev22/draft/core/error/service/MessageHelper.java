package ru.dorofeev22.draft.core.error.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import ru.dorofeev22.draft.core.error.BaseError;

import java.util.Arrays;
import java.util.Locale;

public class MessageHelper {

    private static final Locale locale = Locale.getDefault();
    private static final String BASE_PACKAGE_NAME = "ru.dorofeev22";
    private static final int BASE_PACKAGE_NAME_LENGTH = 14; //"ru.dorofeev22."
    private final MessageSource messageSource;

    public MessageHelper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String localize(@NotNull final Throwable t) {
        return t instanceof BaseError ? localize(t, ((BaseError) t).getArgs()) : localize(t, (String []) null);
    }

    private String localize(@NotNull final Throwable t, final String... args) {
        final Object[] localizedArgs = args != null ? Arrays.stream(args).map(this::localize).toArray(Object[]::new) : null;
        return messageSource.getMessage(getClassName(t), localizedArgs, locale);
    }

    private String localize(@NotNull final String arg) {
        return ifBasePackageClass(arg)
                ? messageSource.getMessage(arg.substring(BASE_PACKAGE_NAME_LENGTH), null, locale)
                : arg;
    }

    private String getClassName(@NotNull final Throwable o) {
        final String name = o.getClass().getName();
        return ifBasePackageClass(name) ? name.substring(BASE_PACKAGE_NAME_LENGTH) : name;
    }

    private boolean ifBasePackageClass(@NotNull final String name) {
        return name.startsWith(BASE_PACKAGE_NAME);
    }

}