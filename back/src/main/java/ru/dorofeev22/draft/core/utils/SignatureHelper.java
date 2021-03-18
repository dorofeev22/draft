package ru.dorofeev22.draft.core.utils;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.reflect.FieldUtils.getFieldsWithAnnotation;

@Component
public class SignatureHelper {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final HmacHelper hmacHelper;
    
    public SignatureHelper(HmacHelper hmacHelper) {
        this.hmacHelper = hmacHelper;
    }
    
    public boolean ifSignatureCorrect(@NotNull final String actualSignature,
                                      @NotNull final String key,
                                      @NotNull final Object signedData,
                                      @NotNull final String signedDataDelimiter) {
        return createHmacSha256Sign(key, signedData, signedDataDelimiter).equals(actualSignature);
    }
    
    public String createHmacSha256Sign(@NotNull final String key,
                                       @NotNull final Object signedData,
                                       @NotNull final String signedDataDelimiter) {
        return createHmacSha256Sign(key, getAnnotatedFieldsValues(signedData), signedDataDelimiter);
    }
    
    public String createHmacSha256Sign(@NotNull final String key,
                                       @NotNull final List<String> signedData,
                                       @NotNull final String signedDataDelimiter) {
        try {
            return hmacHelper.calculateHmacSha256(key, String.join(signedDataDelimiter, signedData));
        } catch (InvalidKeyException | UnsupportedEncodingException exception) {
            log.error("Couldn't calculate signature for {}", signedData);
            throw new RuntimeException("Couldn't check signed data");
        }
    }
    
    public List<String> getAnnotatedFieldsValues(@NotNull final Object object) {
        final List<Field> annotatedFields =
                Arrays.asList(getFieldsWithAnnotation(object.getClass(), CalculateSignature.class));
        annotatedFields.sort(Comparator.comparing(f -> f.getAnnotation(CalculateSignature.class).order()));
        return annotatedFields.stream().map(f -> getFieldValue(f, object)).collect(Collectors.toList());
    }
    
    private String getFieldValue(@NotNull final Field field, @NotNull final Object object) {
        try {
            final Object fieldValue = field.get(object);
            return String.valueOf(fieldValue != null ? fieldValue : "").toUpperCase();
        } catch (Throwable throwable) {
            log.error("Couldn't read annotated value from field {} of object {}" + field.getName(), object);
            throw new RuntimeException("Couldn't read signed data");
        }
    }
}