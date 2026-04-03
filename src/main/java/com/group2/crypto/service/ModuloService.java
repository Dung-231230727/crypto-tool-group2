package com.group2.crypto.service;

import com.group2.crypto.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModuloService {

    public ModuloResponse findInverse(ModuloRequest request) {
        ModuloResponse response = new ModuloResponse();

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
        a = ((a % n) + n) % n;

        response.setAVal(a);
        response.setNVal(n);

        // Extended Euclidean Algorithm
        // n*x + a*y = gcd(n, a)
        // If gcd = 1, then a*y ≡ 1 (mod n), so y is the inverse of a mod n
        List<EuclideanStep> table = new ArrayList<>();

        long r0 = n, r1 = a;
        long x0 = 1, x1 = 0;
        long y0 = 0, y1 = 1;

        // Row 0: represents n
        table.add(new EuclideanStep(0, "—", r0, x0, y0));
        // Row 1: represents a
        table.add(new EuclideanStep(1, "—", r1, x1, y1));

        int step = 2;
        boolean foundOne = (r1 == 1);

        while (r1 != 0 && !foundOne) {
            long q = r0 / r1;
            long r2 = r0 % r1;
            long x2 = x0 - q * x1;
            long y2 = y0 - q * y1;

            table.add(new EuclideanStep(step, String.valueOf(q), r2, x2, y2));

            r0 = r1;
            x0 = x1;
            y0 = y1;
            r1 = r2;
            x1 = x2;
            y1 = y2;

            if (r1 == 1)
                foundOne = true;
            step++;
        }

        long gcd = r1 == 0 ? r0 : r1;
        response.setGcd(gcd);
        response.setTable(table);

        if (gcd != 1) {
            response.setHasInverse(false);
            response.setMessage("Không tồn tại nghịch đảo: gcd(" + a + ", " + n + ") = " + gcd + " ≠ 1");
        } else {
            // y1 is the coefficient for 'a' at the step where r1 = 1 (or y0 if loop
            // finished at r1=0)
            long yResult = (r1 == 1) ? y1 : y0;
            long inverse = ((yResult % n) + n) % n;
            response.setHasInverse(true);
            response.setInverse(inverse);
            response.setMessage("Nghịch đảo của " + response.getAVal()
                    + " (mod " + n + ") = " + inverse
                    + "   ✓  Kiểm tra: " + response.getAVal() + " × " + inverse
                    + " mod " + n + " = " + (response.getAVal() * inverse % n));
        }

        return response;
    }
}
