package com.group2.crypto.service;

import com.group2.crypto.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModuloService {

    public ModuloResponse findInverse(ModuloRequest request) {
        ModuloResponse response = new ModuloResponse();
        response.setSelectedMethod(request.getMethod());

        long a, n;
        try {
            a = Long.parseLong(request.getA().trim());
            n = Long.parseLong(request.getN().trim());
        } catch (NumberFormatException ex) {
            response.setErrorMessage("Vui lòng nhập số nguyên hợp lệ cho a và n.");
            return response;
        }

        if (n <= 1) {
            response.setErrorMessage("n phải lớn hơn 1.");
            return response;
        }
        if (a <= 0) {
            response.setErrorMessage("a phải là số nguyên dương.");
            return response;
        }

        // Normalize a to [0, n)
        long originalA = a;
        a = ((a % n) + n) % n;

        response.setAVal(a);
        response.setNVal(n);

        // Pre-check GCD
        long gcdVal = gcd(n, a);
        response.setGcd(gcdVal);

        if (gcdVal != 1) {
            response.setHasInverse(false);
            response.setMessage("Không tồn tại nghịch đảo: gcd(" + originalA + ", " + n + ") = " + gcdVal + " ≠ 1");
            return response;
        }

        if ("brute".equalsIgnoreCase(request.getMethod())) {
            calculateBruteForce(a, n, response);
        } else {
            calculateEuclidean(a, n, response);
        }

        return response;
    }

    private void calculateBruteForce(long a, long n, ModuloResponse response) {
        List<String> transcript = new ArrayList<>();
        transcript.add("PHƯƠNG PHÁP VÉT CẠN (THEO ĐỊNH NGHĨA)");
        transcript.add("Tìm x trong khoảng [1, " + (n - 1) + "] sao cho (" + a + " * x) mod " + n + " = 1");
        transcript.add("");

        long inverse = -1;
        // Limit transcript entries for very large n, but search all
        for (long x = 1; x < n; x++) {
            long res = (a * x) % n;
            boolean isFound = (res == 1);
            
            if (x <= 50 || (n - x) <= 5 || isFound) { 
                transcript.add(String.format("x = %d: (%d * %d) mod %d = %d", x, a, x, n, res));
            } else if (x == 51) {
                transcript.add("... (đang tìm tiếp) ...");
            }

            if (isFound) {
                inverse = x;
                break;
            }
        }

        response.setInverse(inverse);
        response.setHasInverse(true);
        response.setTranscript(transcript);
        response.setMessage(a + "⁻¹ mod " + n + " = " + inverse);
    }

    private void calculateEuclidean(long a, long n, ModuloResponse response) {
        List<EuclideanStep> table = new ArrayList<>();
        long r0 = n, r1 = a;
        long x0 = 1, x1 = 0;
        long y0 = 0, y1 = 1;

        table.add(new EuclideanStep(0, "—", r0, x0, y0));
        table.add(new EuclideanStep(1, "—", r1, x1, y1));

        int step = 2;
        boolean foundOne = (r1 == 1);

        while (r1 != 0 && !foundOne) {
            long q = r0 / r1;
            long r2 = r0 % r1;
            long x2 = x0 - q * x1;
            long y2 = y0 - q * y1;

            table.add(new EuclideanStep(step, String.valueOf(q), r2, x2, y2));

            r0 = r1; x0 = x1; y0 = y1;
            r1 = r2; x1 = x2; y1 = y2;

            if (r1 == 1) foundOne = true;
            step++;
        }

        response.setTable(table);
        long yResult = (r1 == 1) ? y1 : y0;
        long inverse = ((yResult % n) + n) % n;
        response.setHasInverse(true);
        response.setInverse(inverse);
        response.setMessage(a + "⁻¹ mod " + n + " = " + inverse);
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
