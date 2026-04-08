package com.group2.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.HexFormat;

public class StandardDesTest {
    public static void main(String[] args) throws Exception {
        byte[] keyBytes = HexFormat.of().parseHex("17FFCC5ADBF3EA87");
        byte[] dataBytes = HexFormat.of().parseHex("E36B4C92DE9AD726");
        
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        
        byte[] result = cipher.doFinal(dataBytes);
        System.out.println("Standard DES result: " + HexFormat.of().formatHex(result).toUpperCase());
    }
}
