package ru.dorofeev22.draft.core.crypt;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static ru.dorofeev22.draft.core.utils.ResourceUtils.getResourceAsBytes;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CryptoHelperTest {
    
    @Autowired
    private CryptoHelper cryptoHelper;
    
    @Test
    public void decryptTest()
            throws IOException, NoSuchPaddingException, InvalidKeySpecException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        final String message = "Hello";
        final Cipher encrypt = cryptoHelper.getEncrypt(getResourceAsBytes("classpath:key-pair/privateKey"));
        final String encrypted = Base64Utils.encodeToString(encrypt.doFinal(message.getBytes(StandardCharsets.UTF_8)));
        final Cipher decrypt = cryptoHelper.getDecrypt(getResourceAsBytes("classpath:key-pair/publicKey"));
        Assert.assertEquals(message, cryptoHelper.decrypt(decrypt, encrypted));
    }
}