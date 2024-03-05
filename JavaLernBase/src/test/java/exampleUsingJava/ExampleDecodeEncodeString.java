package exampleUsingJava;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ExampleDecodeEncodeString {

    @Test
    @DisplayName("Пример шифрования строк")
    public void exampleEncryptAndEncrypt(){
        String encrypt = "This password 1";
        MessageDigest messageDigest = null;
        byte[] bytesEncoded = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(encrypt.getBytes(StandardCharsets.UTF_8));
            bytesEncoded = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }
        BigInteger bigInt = new BigInteger(1, bytesEncoded);
        String resHex = bigInt.toString(16);
        System.out.println(resHex);
    }

    @Test
    public void exampleEncryptAndEncryptBase64(){
        String encrypt = "This some string example";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytesEncoded = encoder.encode(encrypt.getBytes());
        BigInteger bigInt = new BigInteger(1, bytesEncoded);
        String resHex = bigInt.toString(16);
        System.out.println(resHex);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytesDecode = decoder.decode(bytesEncoded);
        String decodeRes = new String(bytesDecode, StandardCharsets.UTF_8);
        System.out.println(decodeRes);
     }
}
