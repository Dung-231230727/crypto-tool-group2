package com.group2.crypto;

import com.group2.crypto.model.*;
import com.group2.crypto.service.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CryptoLogicTest {

    @Test
    public void testAes() {
        AesService aes = new AesService();
        AesRequest req = new AesRequest();
        // Standard test vector
        req.setData("0123456789abcdeffedcba9876543210");
        req.setKey("0f1571c947d9e8590cb7add6af7f6798");
        req.setMode("encrypt");

        AesResponse res = aes.process(req);
        if (res.getErrorMessage() != null) System.out.println("AES Error: " + res.getErrorMessage());
        assertEquals("FF0B844A0853BF7C6934AB4364148FB9", res.getResult().toUpperCase());
        
        req.setData(res.getResult());
        req.setMode("decrypt");
        res = aes.process(req);
        assertEquals("0123456789ABCDEFFEDCBA9876543210", res.getResult().toUpperCase());
    }

    @Test
    public void testDes() {
        DesService des = new DesService();
        DesRequest req = new DesRequest();
        // Standard test vector
        req.setData("0123456789ABCDEF");
        req.setKey("133457799BBCDFF1");
        req.setMode("encrypt");

        DesResponse res = des.process(req);
        assertEquals("85E813540F0AB405", res.getResult().toUpperCase());

        req.setData(res.getResult());
        req.setMode("decrypt");
        res = des.process(req);
        assertEquals("0123456789ABCDEF", res.getResult().toUpperCase());
    }
}
