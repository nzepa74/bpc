package com.spring.project.development.crypto;

/**
 * Created by nzepa on 9/26/2020.
 */

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;

public class TrippleDes {
    public TrippleDes() throws Exception {
    }

    public static String decrypt(String encryptedString) throws Exception{

        final String UNICODE_FORMAT = "UTF8";
        KeySpec ks;
        SecretKeyFactory skf;
        Cipher cipher;
        String myEncryptionKey = "ThisIsSpartaThisIsSparta";
        String myEncryptionScheme = "DESede";
        SecretKey key;
        ks = new DESedeKeySpec(myEncryptionKey.getBytes(UNICODE_FORMAT));
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
        String decryptedText = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decodeBase64(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
}