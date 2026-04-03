package com.group2.crypto.service;

import com.group2.crypto.model.*;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class RsaService {

    public RsaResponse process(RsaRequest request) {
        RsaResponse response = new RsaResponse();
        List<String> steps = new ArrayList<>();

        BigInteger p, q, e;
        try {
            p = new BigInteger(request.getP().trim());
            q = new BigInteger(request.getQ().trim());
            e = new BigInteger(request.getE().trim());
        } catch (NumberFormatException ex) {
            response.setErrorMessage("Vui lòng nhập số nguyên hợp lệ cho p, q và e.");
            return response;
        }

        // Validate primes (basic check)
        if (!p.isProbablePrime(50) || !q.isProbablePrime(50)) {
            response.setErrorMessage("p và q phải là số nguyên tố.");
            return response;
        }
        if (p.equals(q)) {
            response.setErrorMessage("p và q phải khác nhau.");
            return response;
        }

        // Step 1: n = p * q
        BigInteger n = p.multiply(q);
        response.setN(n.toString());
        steps.add("Bước 1: n = p × q = " + p + " × " + q + " = " + n);

        // Step 2: phi(n) = (p-1)(q-1)
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        response.setPhi(phi.toString());
        steps.add("Bước 2: φ(n) = (p−1)(q−1) = " + p.subtract(BigInteger.ONE)
                + " × " + q.subtract(BigInteger.ONE) + " = " + phi);

        // Step 3: Validate e
        if (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phi) >= 0) {
            response.setErrorMessage("e phải thỏa mãn 1 < e < φ(n) = " + phi);
            return response;
        }
        BigInteger gcdEPhi = e.gcd(phi);
        if (!gcdEPhi.equals(BigInteger.ONE)) {
            response.setErrorMessage("e = " + e + " không hợp lệ: gcd(e, φ(n)) = gcd("
                    + e + ", " + phi + ") = " + gcdEPhi + " ≠ 1");
            return response;
        }
        steps.add("Bước 3: Kiểm tra e = " + e + ": gcd(" + e + ", " + phi + ") = 1  ✓ (hợp lệ)");

        // Step 4: d = e^(-1) mod phi
        BigInteger d = e.modInverse(phi);
        response.setE(e.toString());
        response.setD(d.toString());
        steps.add("Bước 4: d = e⁻¹ mod φ(n) = " + e + "⁻¹ mod " + phi + " = " + d);
        steps.add("        Kiểm tra: e × d mod φ(n) = " + e + " × " + d
                + " mod " + phi + " = " + e.multiply(d).mod(phi) + "  ✓");

        // Step 5: Keys
        response.setPublicKey("PU = {e = " + e + ",  n = " + n + "}");
        response.setPrivateKey("PR = {d = " + d + ",  n = " + n + "}");
        steps.add("Bước 5: Khóa công khai  PU = {e=" + e + ", n=" + n + "}");
        steps.add("        Khóa bí mật    PR = {d=" + d + ", n=" + n + "}");

        // Step 6: Encrypt
        String msgStr = request.getMessage();
        if (msgStr != null && !msgStr.isBlank()) {
            BigInteger M;
            try {
                M = new BigInteger(msgStr.trim());
            } catch (NumberFormatException ex) {
                response.setErrorMessage("Bản rõ M phải là số nguyên.");
                return response;
            }
            if (M.compareTo(n) >= 0) {
                response.setErrorMessage("Bản rõ M = " + M + " phải nhỏ hơn n = " + n + ".");
                return response;
            }
            BigInteger C = M.modPow(e, n);
            response.setCiphertext(C.toString());
            steps.add("Bước 6: Mã hóa:  C = Mᵉ mod n = " + M + "^" + e + " mod " + n + " = " + C);

            // Step 7: Decrypt
            BigInteger Mprime = C.modPow(d, n);
            response.setPlaintext(Mprime.toString());
            steps.add("Bước 7: Giải mã: M' = Cᵈ mod n = " + C + "^" + d + " mod " + n + " = " + Mprime);
            if (Mprime.equals(M)) {
                steps.add("✓ Kết quả: M' = M = " + M + "  → Thành công!");
            }
        }

        response.setSteps(steps);
        return response;
    }
}
