package com.lbc.awakening.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;

@Service
public class SecurityService<T> {

    private IvParameterSpec iv;
    private SecretKeySpec key;
    private Cipher cipher;

    public SecurityService() {
        try {
            iv = new IvParameterSpec("C2BF524EC88BD208".getBytes("UTF-8"));
            key = new SecretKeySpec("C510FA90B14ACB28".getBytes("UTF-8"), "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        } catch (UnsupportedEncodingException | NoSuchPaddingException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }


    public String encrypt(String model) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encrypted = cipher.doFinal(model.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public T decrypt(String encrypted, Class<T> genericClass) throws Exception {
        System.out.println(encrypted);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
        return new ObjectMapper().readValue(new String(original), genericClass);

    }


    public String decryptToString(String encrypted) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
        return new String(original);
    }
}
