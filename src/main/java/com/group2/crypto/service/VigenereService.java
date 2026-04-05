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
        List<String> transcript = new ArrayList<>();

        transcript.add("THUẬT TOÁN VIGENERE (" + request.getMode() + ", " + request.getKeyType() + ")");
        transcript.add("Input: " + text);
        transcript.add("Key:   " + key);
        transcript.add("");

        // keystream for autokey mode
        StringBuilder keystream = new StringBuilder(key);
        int alphaIdx = 0; // index among alphabetic chars only

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (ch < 'A' || ch > 'Z') {
                result.append(ch);
                transcript.add(String.format("[%02d] Ký tự '%c': Giữ nguyên (không phải chữ cái)", i, ch));
                continue;
            }

            char kc = autokey ? keystream.charAt(alphaIdx) : key.charAt(alphaIdx % key.length());
            int pv = ch - 'A';
            int kv = kc - 'A';
            int rv;
            String formula;

            if (encrypt) {
                rv = (pv + kv) % SIZE;
                formula = String.format("(%d + %d) mod 26 = %d", pv, kv, rv);
            } else {
                rv = (pv - kv + SIZE) % SIZE;
                formula = String.format("(%d - %d + 26) mod 26 = %d", pv, kv, rv);
            }

            char out = (char)('A' + rv);
            result.append(out);
            transcript.add(String.format("[%02d] '%c' (%2d) %s '%c' (%2d) -> %s -> '%c' (%2d)", 
                i, ch, pv, (encrypt ? "+" : "-"), kc, kv, formula, out, rv));

            // Extend keystream for autokey
            if (autokey) {
                keystream.append(encrypt ? ch : out);
            }
            alphaIdx++;
        }

        response.setResult(result.toString());
        response.setTranscript(transcript);
        return response;
    }
}
