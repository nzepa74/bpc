package com.spring.project.development.config;

import com.spring.project.development.crypto.DBPasswordProtectorUtil;

public class EncryptDecryptDataSource {
    public EncryptDecryptDataSource() {

    }

    public String getDecryptedText(String encodedText) {
        return DBPasswordProtectorUtil.decrypt(encodedText);
    }

    public String getEncryptedText(String plainText) {
        return DBPasswordProtectorUtil.encrypt(plainText);
    }
}
