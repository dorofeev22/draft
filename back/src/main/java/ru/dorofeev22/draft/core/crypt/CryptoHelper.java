package ru.dorofeev22.draft.core.crypt;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import ru.dorofeev22.draft.core.utils.FileUtils;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class CryptoHelper {
    
    private static final String RSA_CRYPTOGRAPHIC = "RSA";
    private KeyFactory keyFactory;
    
    @PostConstruct
    public void init() throws
            NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException {
        this.keyFactory = KeyFactory.getInstance(RSA_CRYPTOGRAPHIC);
    }
    
    public Cipher getDecrypt(@NotNull final String publicKeyFilePath)
            throws IOException, InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return getDecrypt(FileUtils.readFile(publicKeyFilePath));
    }
    
    public Cipher getDecrypt(final byte[] key)
            throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        final PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        return initCipher(Cipher.DECRYPT_MODE, publicKey);
    }
    
    public Cipher getEncrypt(final byte[] key)
            throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
        final PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        return initCipher(Cipher.ENCRYPT_MODE, privateKey);
    }
    
    private Cipher initCipher(int cryptoMode, @NotNull final Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        final Cipher cipher = Cipher.getInstance(RSA_CRYPTOGRAPHIC);
        cipher.init(cryptoMode, key);
        return cipher;
        
    }

    public String decrypt(@NotNull final Cipher decrypt, @NotNull final String message) throws IllegalBlockSizeException, BadPaddingException {
        return new String(decrypt.doFinal(Base64Utils.decodeFromString(message)), StandardCharsets.UTF_8);
    }
}