package com.spring.project.development.crypto;



import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public abstract class DBPasswordProtectorUtil {

    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = new byte[]{'A', 'S', 'C', 'E', 'N', 'D', 'F', 'I', 'N', 'A', 'N', 'C', 'I', 'A', 'L', 'S'};

    private static Key generateKey() {

        return new SecretKeySpec(keyValue, ALGORITHM);

    }

    public static String encrypt(String value) {

        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byteValue = cipher.doFinal(value.getBytes());
//            return new BASE64Encoder().encode(byteValue);
            return Base64.getEncoder().encodeToString(byteValue);

        } catch (Exception ex) {
            return null;
        }
    }

    public static String decrypt(String value) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
//            byte[] byteValue = new BASE64Decoder().decodeBuffer(value);
            byte[] byteValue = Base64.getDecoder().decode(value);
            byteValue = cipher.doFinal(byteValue);
            return new String(byteValue);

        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) {

        System.out.println(encrypt(args[0]));
    }

}