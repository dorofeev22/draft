package ru.dorofeev22.draft.core.crypt;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

@RunWith(JUnit4.class)
public class KeysGenerator {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String message = "Hello, word!";
    
    private KeyPairGenerator keyGen;
    private KeyFactory keyFactory;
    private Cipher encrypt;
    private Cipher decrypt;
    private static final String CRYPTOGRAPHIC_SYSTEM = "RSA";
    private static final String KEY_DIR_PATH = "src/test/resources/key-pair";
    
    @Before
    public void init() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.keyGen = KeyPairGenerator.getInstance(CRYPTOGRAPHIC_SYSTEM);
        this.keyGen.initialize(1024);
        
        this.keyFactory = KeyFactory.getInstance(CRYPTOGRAPHIC_SYSTEM);
        
        this.encrypt = Cipher.getInstance(CRYPTOGRAPHIC_SYSTEM);
        this.decrypt = Cipher.getInstance(CRYPTOGRAPHIC_SYSTEM);
    }

    @Ignore
    @Test
    public void generateKeyPair() throws IOException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        final KeyPair keyPair = this.keyGen.generateKeyPair();
    
        encrypt.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
        decrypt.init(Cipher.DECRYPT_MODE, keyPair.getPublic());
        
        byte[] encrypted = encrypt.doFinal(message.getBytes(StandardCharsets.UTF_8));
        String decrypted = new String(decrypt.doFinal(encrypted), StandardCharsets.UTF_8);
        
        log.info(decrypted);
        
        writeToFile("privateKey", keyPair.getPrivate().getEncoded());
        writeToFile("publicKey", keyPair.getPublic().getEncoded());
    
        RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);
        RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
        writeToFile("private.key", rsaPrivateKeySpec.getModulus(), rsaPrivateKeySpec.getPrivateExponent());
        writeToFile("public.key", rsaPublicKeySpec.getModulus(), rsaPublicKeySpec.getPublicExponent());
    }
    
    private void writeToFile(String fileName, byte[] key) throws IOException {
        final File file = new File(createFilePath(fileName));
        file.getParentFile().mkdirs();
        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(key);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
    
    private void writeToFile(String fileName, BigInteger mod, BigInteger exp) throws IOException {
        final ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(createFilePath(fileName))));
        objectOutputStream.writeObject(mod);
        objectOutputStream.writeObject(exp);
        objectOutputStream.close();
    }
    
    private String createFilePath(String fileName) {
        return KEY_DIR_PATH.concat("/").concat(fileName);
    }
}