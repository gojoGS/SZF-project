package app.service;

import org.apache.commons.codec.digest.DigestUtils;

public class Sha256EncryptionService implements EncryptionService{
    @Override
    public String encrypt(String message) {
        return DigestUtils.sha256Hex(message);
    }
}
