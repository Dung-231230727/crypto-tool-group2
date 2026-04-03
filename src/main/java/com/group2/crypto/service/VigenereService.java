package com.group2.crypto.service;

import com.group2.crypto.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VigenereService {

    private static final int SIZE = 26;

    public VigenereResponse process(VigenereRequest request) {
        VigenereResponse response = new VigenereResponse();
        response.setMode(request.getMode());
        response.setKeyType(request.getKeyType());

        String rawText = request.getText();
        String rawKey  = request.getKey();

        if (rawText == null || rawText.isBlank()) {
            response.setErrorMessage("Vui lòng nhập văn bản cần xử lý.");
            return response;
        }
        if (rawKey == null || rawKey.isBlank()) {
            response.setErrorMessage("Vui lòng nhập khóa.");
            return response;
        }

        String text = rawText.toUpperCase();
        String key  = rawKey.toUpperCase();

        // Validate key contains only A-Z
        for (char c : key.toCharArray()) {
            if (c < 'A' || c > 'Z') {
                response.setErrorMessage("Khóa chỉ được chứa các ký tự A-Z.");
                return response;
            }
        }

        boolean encrypt  = "ENCRYPT".equals(request.getMode());
        boolean autokey  = "AUTOKEY".equals(request.getKeyType());

        StringBuilder result   = new StringBuilder();
        List<VigenereStep> steps = new ArrayList<>();

        // keystream for autokey mode
        StringBuilder keystream = new StringBuilder(key);
        int alphaIdx = 0; // index among alphabetic chars only

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (ch < 'A' || ch > 'Z') {
                // Non-alpha: keep as-is
                result.append(ch);
                steps.add(new VigenereStep(i, ch, '-', -1, -1,
                        "Ký tự không phải chữ cái – giữ nguyên", -1, ch, false));
                continue;
            }

            // Ensure keystream is long enough (autokey)
            if (autokey && alphaIdx >= keystream.length()) {
                // Should not happen – appended incrementally
                break;
            }

            char kc = autokey ? keystream.charAt(alphaIdx) : key.charAt(alphaIdx % key.length());
            int pv = ch - 'A';
            int kv = kc - 'A';
            int rv;
            String formula;

            if (encrypt) {
                rv = (pv + kv) % SIZE;
                formula = "(" + pv + " + " + kv + ") mod 26 = " + rv;
            } else {
                rv = (pv - kv + SIZE) % SIZE;
                formula = "(" + pv + " - " + kv + " + 26) mod 26 = " + rv;
            }

            char out = (char)('A' + rv);
            result.append(out);
            steps.add(new VigenereStep(i, ch, kc, pv, kv, formula, rv, out, true));

            // Extend keystream for autokey
            if (autokey) {
                keystream.append(encrypt ? ch : out);
            }
            alphaIdx++;
        }

        response.setResult(result.toString());
        response.setSteps(steps);
        return response;
    }
}
