package ru.dorofeev22.draft.core.crypt;

import org.apache.commons.codec.binary.Hex;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.reflect.FieldUtils.getFieldsWithAnnotation;

@Component // for ability to throw NoSuchAlgorithmException while application is starting
public class HmacHelper {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String HMAC_SHA_256 = "HmacSHA256";
    private final String UTF8_CHARSET_NAME = StandardCharsets.UTF_8.name();
    private Mac sha256Mac;
    
    @PostConstruct
    public void init() throws NoSuchAlgorithmException {
        sha256Mac = Mac.getInstance(HMAC_SHA_256);
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
            return calculateHmacSha256(key, String.join(signedDataDelimiter, signedData));
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

    public String calculateHmacSha256(@NotNull final String key, @NotNull final String data)
            throws UnsupportedEncodingException, InvalidKeyException {
        sha256Mac.init(new SecretKeySpec(key.getBytes(UTF8_CHARSET_NAME), HMAC_SHA_256));
        return Hex.encodeHexString(sha256Mac.doFinal(data.getBytes(UTF8_CHARSET_NAME)));
    }

}