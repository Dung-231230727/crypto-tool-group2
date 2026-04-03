package com.group2.crypto;

import com.group2.crypto.model.*;
import com.group2.crypto.service.*;

public class CryptoTest {
    public static void main(String[] args) {
        testAes();
        testDes();
    }

    private static void testAes() {
        AesService aes = new AesService();
        AesRequest req = new AesRequest();
        req.setData("0123456789abcdeffedcba9876543210");
        req.setKey("0f1571c947d9e8590cb7add6af7f6798");
        req.setMode("encrypt");

        AesResponse res = aes.process(req);
        System.out.println("AES Encrypt: " + res.getResult());
        
        req.setData(res.getResult());
        req.setMode("decrypt");
        res = aes.process(req);
        System.out.println("AES Decrypt: " + res.getResult());
    }

    private static void testDes() {
        DesService des = new DesService();
        DesRequest req = new DesRequest();
        req.setData("0123456789ABCDEF");
        req.setKey("133457799BBCDFF1");
        req.setMode("encrypt");

        DesResponse res = des.process(req);
        System.out.println("DES Encrypt: " + res.getResult());

        req.setData(res.getResult());
        req.setMode("decrypt");
        res = des.process(req);
        System.out.println("DES Decrypt: " + res.getResult());
    }
}
