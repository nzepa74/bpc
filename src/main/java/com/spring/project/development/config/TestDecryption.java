package com.spring.project.development.config;
 public class TestDecryption {

    public static void main(String[] args) {
        EncryptDecryptDataSource encryptDecryptDataSource = new EncryptDecryptDataSource();

        String plainText = "zepa";
        String encrypted = encryptDecryptDataSource.getEncryptedText(plainText);
        String decrypted = encryptDecryptDataSource.getDecryptedText(encrypted);

        System.out.println("Text to encrypt: " + plainText);
        System.out.println("Encrypted text:" + encrypted);
        System.out.println("Decrypted text:" + decrypted);

    }

}