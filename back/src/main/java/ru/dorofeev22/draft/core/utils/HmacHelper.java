package ru.dorofeev22.draft.core.utils;

import org.apache.commons.codec.binary.Hex;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class HmacHelper {
    
    private static final String HMAC_SHA_256 = "HmacSHA256";
    private final String UTF8_CHARSET_NAME = StandardCharsets.UTF_8.name();
    private Mac sha256Mac;
    
    @PostConstruct
    public void init() throws NoSuchAlgorithmException {
        sha256Mac = Mac.getInstance(HMAC_SHA_256);
    }
    
    public String calculateHmacSha256(@NotNull final String key, @NotNull final String data)
            throws UnsupportedEncodingException, InvalidKeyException {
        sha256Mac.init(new SecretKeySpec(key.getBytes(UTF8_CHARSET_NAME), HMAC_SHA_256));
        return Hex.encodeHexString(sha256Mac.doFinal(data.getBytes(UTF8_CHARSET_NAME)));
    }

}