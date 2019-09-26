package com.company;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Main {

    private static final String Pin = "511050";
    private static final byte[] csecret = new byte[]{(byte) 210, (byte) 141, 107, 16, 28, (byte) 189, 64, (byte) 207, 118, 10, (byte) 188, (byte) 216, 57, (byte) 235, (byte) 237, (byte) 142};

    public static void main(String[] args) throws Exception {
        // generate AES key
        byte[] encryptPin = Pin.getBytes(StandardCharsets.UTF_8);
        byte[] checkPin = MessageDigest.getInstance("SHA-256").digest(encryptPin);

        var dbKey = new SecretKeySpec(
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(
                        new PBEKeySpec(Pin.toCharArray(), checkPin, 10000, 256))
                        .getEncoded(), "AES");

        var res = aesDecryptEcb(dbKey, csecret);

        System.out.println(new String(res));

    }

    private static byte[] aesDecryptEcb(SecretKey key, byte[] data) throws Exception {
        Cipher msgCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        msgCipher.init(2, key); // 2 == decrypt
        return msgCipher.doFinal(data);
    }
}
