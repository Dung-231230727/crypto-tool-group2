package com.group2.crypto;

import com.group2.crypto.service.AesService;
import com.group2.crypto.service.DesService;
import com.group2.crypto.model.AesRequest;
import com.group2.crypto.model.AesResponse;
import com.group2.crypto.model.DesRequest;
import com.group2.crypto.model.DesResponse;
import com.group2.crypto.service.ModuloService;
import com.group2.crypto.model.ModuloRequest;
import com.group2.crypto.model.ModuloResponse;

public class CryptoTest {
    public static void main(String[] args) {
        testDes();
        testAes();
        testModulo();
    }

    private static void testModulo() {
        ModuloService moduloService = new ModuloService();
        
        // Test Euclidean
        ModuloRequest req1 = new ModuloRequest();
        req1.setA("7");
        req1.setN("26");
        req1.setMethod("euclidean");
        ModuloResponse res1 = moduloService.findInverse(req1);
        System.out.println("Modulo Sync Result: " + res1.getInverse());
        
        // Test Brute Force
        ModuloRequest req2 = new ModuloRequest();
        req2.setA("7");
        req2.setN("26");
        req2.setMethod("brute");
        ModuloResponse res2 = moduloService.findInverse(req2);
        System.out.println("Modulo Brute Result: " + res2.getInverse());
        
        boolean ok = res1.getInverse() == 15 && res2.getInverse() == 15 && 
                     res2.getTranscript() != null && !res2.getTranscript().isEmpty();
        System.out.println("Modulo Test: " + (ok ? "PASSED" : "FAILED"));
    }

    private static void testDes() {
        DesService desService = new DesService();
        DesRequest request = new DesRequest();
        request.setData("E36B4C92DE9AD726");
        request.setKey("17FFCC5ADBF3EA87");
        request.setMode("encrypt");

        DesResponse response = desService.process(request);
        System.out.println("DES Result: " + response.getResult());
        
        boolean ok = "1792B696371659F4".equalsIgnoreCase(response.getResult());
        System.out.println("DES Test: " + (ok ? "PASSED" : "FAILED"));
    }

    private static void testAes() {
        AesService aesService = new AesService();
        AesRequest request = new AesRequest();
        request.setData("B104AADD3AC293DF787EFD2CF8065925");
        request.setKey("C281B1763B140EF7AB12EB2745F1F59F");
        request.setMode("encrypt");

        AesResponse response = aesService.process(request);
        System.out.println("AES Result: " + response.getResult());
        
        boolean ok = "59A6D04DAFD29C4F0B455D8CF88AFAF1".equalsIgnoreCase(response.getResult());
        System.out.println("AES Test: " + (ok ? "PASSED" : "FAILED"));
    }
}
