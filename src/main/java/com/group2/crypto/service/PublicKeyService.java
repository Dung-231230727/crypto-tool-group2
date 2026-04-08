package com.group2.crypto.service;

import com.group2.crypto.model.PublicKeyRequest;
import com.group2.crypto.model.PublicKeyResponse;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublicKeyService {

    public PublicKeyResponse process(PublicKeyRequest request) {
        String algo = request.getAlgorithm().toUpperCase();
        switch (algo) {
            case "DH":
                return processDiffieHellman(request);
            case "RSA":
                return processRSA(request);
            case "ELGAMAL":
                return processElGamal(request);
            case "DSA":
                return processDSA(request);
            default:
                PublicKeyResponse res = new PublicKeyResponse();
                res.setErrorMessage("Thuật toán không được hỗ trợ.");
                return res;
        }
    }

    private PublicKeyResponse processDiffieHellman(PublicKeyRequest request) {
        PublicKeyResponse response = new PublicKeyResponse();
        List<String> transcript = new ArrayList<>();
        try {
            BigInteger q = new BigInteger(request.getP().trim());
            BigInteger a = new BigInteger(request.getG().trim());
            BigInteger xA = new BigInteger(request.getXA().trim());
            BigInteger xB = new BigInteger(request.getYB().trim()); // Reusing yB field as xB

            transcript.add("TRAO ĐỔI KHÓA DIFFIE-HELLMAN");
            transcript.add("Tham số chung: q = " + q + ", a = " + a);
            transcript.add("An chọn khóa riêng xA = " + xA);
            transcript.add("Ba chọn khóa riêng xB = " + xB);
            transcript.add("");

            BigInteger yA = a.modPow(xA, q);
            BigInteger yB = a.modPow(xB, q);
            BigInteger K = yA.modPow(xB, q); // K is same for both

            transcript.add("a) Cách An tính khóa công khai yA và khóa phiên K:");
            transcript.add("   An tính yA = a^xA mod q = " + a + "^" + xA + " mod " + q + " = " + yA);
            transcript.add("   An tính K = yB^xA mod q = " + yB + "^" + xA + " mod " + q + " = " + K);
            transcript.add("");

            transcript.add("b) Cách Ba tính khóa công khai yB và khóa phiên K:");
            transcript.add("   Ba tính yB = a^xB mod q = " + a + "^" + xB + " mod " + q + " = " + yB);
            transcript.add("   Ba tính K = yA^xB mod q = " + yA + "^" + xB + " mod " + q + " = " + K);

            response.setResult("K = " + K);
            response.setTranscript(transcript);
        } catch (Exception e) {
            response.setErrorMessage("Lỗi: " + e.getMessage());
        }
        return response;
    }

    private PublicKeyResponse processRSA(PublicKeyRequest request) {
        PublicKeyResponse response = new PublicKeyResponse();
        List<String> transcript = new ArrayList<>();
        try {
            BigInteger p = new BigInteger(request.getP().trim());
            BigInteger q = new BigInteger(request.getQ().trim());
            BigInteger e = new BigInteger(request.getG().trim()); // Reused 'g' for 'e'
            BigInteger M = new BigInteger(request.getMessage().trim());
            String mode = request.getSubMethod().toUpperCase();

            transcript.add("THUẬT TOÁN RSA - " + (mode.equals("SIGNATURE") ? "BÀI TOÁN 1 (CHỮ KÝ)" : "BÀI TOÁN 2 (BẢO MẬT)"));
            
            BigInteger n = p.multiply(q);
            BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            BigInteger d = e.modInverse(phi);
            
            transcript.add("a) Khóa công khai PU = {e, n} = {" + e + ", " + n + "}");
            transcript.add("b) Cách An tạo khóa riêng: phi(n) = " + phi + " => d = e^-1 mod phi(n) = " + d + " => PR = {d, n} = {" + d + ", " + n + "}");
            
            if (mode.equals("SIGNATURE")) {
                BigInteger C = M.modPow(d, n);
                transcript.add("c) Cách An tạo bản mã hóa (Chữ ký): C = M^d mod n = " + M + "^" + d + " mod " + n + " = " + C);
                BigInteger verify = C.modPow(e, n);
                transcript.add("d) Cách người nhận giải mã bản mã C: M = C^e mod n = " + C + "^" + e + " mod " + n + " = " + verify);
                transcript.add("e) Việc mã hóa ở câu c) thực hiện nhiệm vụ: CHỮ KÝ SỐ");
                response.setResult("C = " + C);
            } else {
                BigInteger C = M.modPow(e, n);
                transcript.add("c) Cách người gửi (Ba) mã hóa thông điệp M gửi An: C = M^e mod n = " + M + "^" + e + " mod " + n + " = " + C);
                BigInteger M2 = C.modPow(d, n);
                transcript.add("d) Cách An giải mã bản mã C: M = C^d mod n = " + C + "^" + d + " mod " + n + " = " + M2);
                transcript.add("e) Việc mã hóa ở câu c) thực hiện nhiệm vụ: BẢO MẬT");
                response.setResult("C = " + C);
            }
            
            response.setTranscript(transcript);
        } catch (Exception ex) {
            response.setErrorMessage("Lỗi: " + ex.getMessage());
        }
        return response;
    }

    private PublicKeyResponse processElGamal(PublicKeyRequest request) {
        PublicKeyResponse response = new PublicKeyResponse();
        List<String> transcript = new ArrayList<>();
        try {
            BigInteger q = new BigInteger(request.getP().trim());
            BigInteger a = new BigInteger(request.getG().trim());
            BigInteger xA = new BigInteger(request.getXA().trim());
            BigInteger k = new BigInteger(request.getK().trim());
            BigInteger M = new BigInteger(request.getMessage().trim());

            transcript.add("MẬT MÃ ELGAMAL");
            transcript.add("Tham số chung: q = " + q + ", a = " + a);
            transcript.add("An chọn khóa riêng: xA = " + xA);
            
            BigInteger yA = a.modPow(xA, q);
            transcript.add("a) Khóa công khai của An: PU = {q, a, yA} với yA = a^xA mod q = " + a + "^" + xA + " mod " + q + " = " + yA);
            
            transcript.add("b) Ba chọn số k = " + k + " để mã hóa bản tin M = " + M);
            BigInteger K = yA.modPow(k, q);
            BigInteger c1 = a.modPow(k, q);
            BigInteger c2 = K.multiply(M).mod(q);
            
            transcript.add("   Tính K = yA^k mod q = " + yA + "^" + k + " mod " + q + " = " + K);
            transcript.add("   Tính C1 = a^k mod q = " + a + "^" + k + " mod " + q + " = " + c1);
            transcript.add("   Tính C2 = K * M mod q = " + K + " * " + M + " mod " + q + " = " + c2);
            transcript.add("   => Bản mã (C1, C2) = (" + c1 + ", " + c2 + ")");
            
            transcript.add("c) Cách An giải mã bản mã (C1, C2):");
            BigInteger K_dec = c1.modPow(xA, q);
            BigInteger K_inv = K_dec.modInverse(q);
            BigInteger M_dec = c2.multiply(K_inv).mod(q);
            transcript.add("   Tính K = C1^xA mod q = " + c1 + "^" + xA + " mod " + q + " = " + K_dec);
            transcript.add("   Tính K^-1 mod q = " + K_inv);
            transcript.add("   Tính M = C2 * K^-1 mod q = " + c2 + " * " + K_inv + " mod " + q + " = " + M_dec);
            
            response.setResult("(C1, C2) = (" + c1 + ", " + c2 + ")");
            response.setTranscript(transcript);
        } catch (Exception e) {
            response.setErrorMessage("Lỗi: " + e.getMessage());
        }
        return response;
    }

    private PublicKeyResponse processDSA(PublicKeyRequest request) {
        PublicKeyResponse response = new PublicKeyResponse();
        List<String> transcript = new ArrayList<>();
        try {
            BigInteger p = new BigInteger(request.getP().trim());
            BigInteger q = new BigInteger(request.getQ().trim());
            BigInteger h = new BigInteger(request.getH().trim());
            BigInteger x = new BigInteger(request.getXA().trim());
            BigInteger k = new BigInteger(request.getK().trim());
            BigInteger HM = new BigInteger(request.getMessage().trim());

            transcript.add("CHỮ KÝ ĐIỆN TỬ DSA");
            transcript.add(String.format("Tham số chung: p=%s, q=%s, h=%s", p, q, h));
            transcript.add("An chọn khóa riêng xA = " + x);
            
            BigInteger g = h.modPow(p.subtract(BigInteger.ONE).divide(q), p);
            transcript.add("1. Tính g = h^((p-1)/q) mod p = " + g);
            
            BigInteger yA = g.modPow(x, p);
            transcript.add("a) Khóa công khai của An: yA = g^xA mod p = " + g + "^" + x + " mod " + p + " = " + yA);
            
            transcript.add("b) Chữ ký số của An cho bản tin M có H(M) = " + HM + " dùng k = " + k + ":");
            BigInteger r = g.modPow(k, p).mod(q);
            BigInteger kInv = k.modInverse(q);
            BigInteger s = kInv.multiply(HM.add(x.multiply(r))).mod(q);
            
            transcript.add("   Tính r = (g^k mod p) mod q = " + r);
            transcript.add("   Tính k^-1 mod q = " + kInv);
            transcript.add("   Tính s = [k^-1 * (H(M) + x*r)] mod q = " + s);
            transcript.add("   => Chữ ký (r, s) = (" + r + ", " + s + ")");
            
            transcript.add("c) Cách Ba xác minh chữ ký (r, s):");
            BigInteger w = s.modInverse(q);
            BigInteger u1 = HM.multiply(w).mod(q);
            BigInteger u2 = r.multiply(w).mod(q);
            BigInteger v = g.modPow(u1, p).multiply(yA.modPow(u2, p)).mod(p).mod(q);
            
            transcript.add("   Tính w = s^-1 mod q = " + w);
            transcript.add("   Tính u1 = [H(M) * w] mod q = " + u1);
            transcript.add("   Tính u2 = [r * w] mod q = " + u2);
            transcript.add("   Tính v = [(g^u1 * y^u2) mod p] mod q = " + v);
            transcript.add(v.equals(r) ? "   Vì v = r, chữ ký HỢP LỆ." : "   Vì v != r, chữ ký KHÔNG HỢP LỆ.");
            
            response.setResult("(r, s) = (" + r + ", " + s + ")");
            response.setTranscript(transcript);
        } catch (Exception e) {
            response.setErrorMessage("Lỗi tham số hoặc không tính được nghịch đảo.");
        }
        return response;
    }
}
