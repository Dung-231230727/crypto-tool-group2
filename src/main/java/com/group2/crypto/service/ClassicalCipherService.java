package com.group2.crypto.service;

import com.group2.crypto.model.ClassicalCipherRequest;
import com.group2.crypto.model.ClassicalCipherResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassicalCipherService {

    public ClassicalCipherResponse process(ClassicalCipherRequest request) {
        String algo = request.getAlgorithm().toUpperCase();
        switch (algo) {
            case "CAESAR":
                return processCaesar(request);
            case "VIGENERE":
                return processVigenere(request);
            case "MONO":
                return processMonoalphabetic(request);
            case "PLAYFAIR":
                return processPlayfair(request);
            case "TRANSPOSITION":
                return processTransposition(request);
            default:
                ClassicalCipherResponse res = new ClassicalCipherResponse();
                res.setErrorMessage("Thuật toán không được hỗ trợ.");
                return res;
        }
    }

    private ClassicalCipherResponse processCaesar(ClassicalCipherRequest request) {
        ClassicalCipherResponse response = new ClassicalCipherResponse();
        List<String> transcript = new ArrayList<>();
        boolean encrypt = "ENCRYPT".equals(request.getMode());
        int key;
        try {
            key = Integer.parseInt(request.getKey().trim());
        } catch (NumberFormatException e) {
            response.setErrorMessage("Khóa Caesar phải là một số nguyên.");
            return response;
        }

        String input = request.getText().toUpperCase();
        StringBuilder result = new StringBuilder();
        
        transcript.add("THUẬT TOÁN CAESAR (" + (encrypt ? "MÃ HÓA" : "GIẢI MÃ") + ")");
        transcript.add("Input: " + input);
        transcript.add("Key:   " + key);
        transcript.add("");

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                int p = c - 'A';
                int step = encrypt ? key : -key;
                int resIdx = (p + step) % 26;
                if (resIdx < 0) resIdx += 26;
                char resChar = (char) ('A' + resIdx);
                result.append(resChar);
                transcript.add(String.format("[%02d] '%c' (%2d) %s %d = %d mod 26 -> '%c' (%2d)", 
                        i, c, p, (encrypt ? "+" : "-"), Math.abs(key), (p + step), resChar, resIdx));
            } else {
                result.append(c);
            }
        }

        response.setTranscript(transcript);
        return response;
    }

    private ClassicalCipherResponse processVigenere(ClassicalCipherRequest request) {
        ClassicalCipherResponse response = new ClassicalCipherResponse();
        List<String> transcript = new ArrayList<>();
        boolean encrypt = "ENCRYPT".equals(request.getMode());
        boolean autokey = "AUTOKEY".equals(request.getKeyType());
        String text = request.getText().toUpperCase();
        String key = request.getKey().toUpperCase().replaceAll("[^A-Z]", "");

        if (key.isEmpty()) {
            response.setErrorMessage("Khóa Vigenere chỉ được chứa ký tự A-Z.");
            return response;
        }

        StringBuilder result = new StringBuilder();
        transcript.add("THUẬT TOÁN VIGENERE (" + (autokey ? "AUTOKEY" : "LẶP KHÓA") + ")");
        transcript.add("Input: " + text);
        transcript.add("Key:   " + key);
        transcript.add("");

        StringBuilder keystream = new StringBuilder(key);
        int alphaIdx = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch < 'A' || ch > 'Z') {
                result.append(ch);
                continue;
            }

            char kc = autokey ? keystream.charAt(alphaIdx) : key.charAt(alphaIdx % key.length());
            int pv = ch - 'A';
            int kv = kc - 'A';
            int rv = encrypt ? (pv + kv) % 26 : (pv - kv + 26) % 26;
            char out = (char) ('A' + rv);
            result.append(out);

            transcript.add(String.format("[%02d] '%c'(%d) %s '%c'(%d) = %d mod 26 -> '%c'", 
                    i, ch, pv, (encrypt ? "+" : "-"), kc, kv, (encrypt ? pv+kv : pv-kv+26), out));

            if (autokey) keystream.append(encrypt ? ch : out);
            alphaIdx++;
        }

        response.setResult(result.toString());
        response.setTranscript(transcript);
        return response;
    }

    private ClassicalCipherResponse processMonoalphabetic(ClassicalCipherRequest request) {
        ClassicalCipherResponse response = new ClassicalCipherResponse();
        List<String> transcript = new ArrayList<>();
        boolean encrypt = "ENCRYPT".equals(request.getMode());
        String key = request.getKey().toUpperCase().replaceAll("\\s+", "");

        if (key.length() != 26) {
            response.setErrorMessage("Khóa Monoalphabetic phải chứa đúng 26 chữ cái.");
            return response;
        }

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String input = request.getText().toUpperCase();
        StringBuilder result = new StringBuilder();

        transcript.add("THUẬT TOÁN MÃ HÓA CHỮ ĐƠN (MONOALPHABETIC)");
        transcript.add("Bảng chữ cái: " + alphabet);
        transcript.add("Bảng thế:    " + key);
        transcript.add("");

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                int idx;
                char resChar;
                if (encrypt) {
                    idx = alphabet.indexOf(c);
                    resChar = key.charAt(idx);
                } else {
                    idx = key.indexOf(c);
                    resChar = alphabet.charAt(idx);
                }
                result.append(resChar);
                transcript.add(String.format("[%02d] '%c' (%2d) -> '%c'", i, c, idx, resChar));
            } else {
                result.append(c);
            }
        }

        response.setResult(result.toString());
        response.setTranscript(transcript);
        return response;
    }

    private ClassicalCipherResponse processPlayfair(ClassicalCipherRequest request) {
        ClassicalCipherResponse response = new ClassicalCipherResponse();
        List<String> transcript = new ArrayList<>();
        boolean encrypt = "ENCRYPT".equals(request.getMode());
        String key = request.getKey().toUpperCase().replace("J", "I").replaceAll("[^A-Z]", "");
        
        // Generate Matrix
        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        StringBuilder uniqueChars = new StringBuilder();
        for (char c : (key + alphabet).toCharArray()) {
            if (uniqueChars.indexOf(String.valueOf(c)) == -1) uniqueChars.append(c);
        }
        
        char[][] matrix = new char[5][5];
        for (int i = 0; i < 25; i++) matrix[i / 5][i % 5] = uniqueChars.charAt(i);
        response.setExtraData(matrix);

        transcript.add("THUẬT TOÁN PLAYFAIR");
        transcript.add("Ma trận 5x5 (J thay bằng I):");
        for (int i = 0; i < 5; i++) {
            StringBuilder row = new StringBuilder("  ");
            for (int j = 0; j < 5; j++) row.append(matrix[i][j]).append(" ");
            transcript.add(row.toString());
        }
        transcript.add("");

        String input = request.getText().toUpperCase().replace("J", "I").replaceAll("[^A-Z]", "");
        if (encrypt) {
            // Prepare bigrams
            StringBuilder prepared = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                prepared.append(input.charAt(i));
                if (i + 1 < input.length() && input.charAt(i) == input.charAt(i + 1)) {
                    prepared.append('X');
                }
            }
            if (prepared.length() % 2 != 0) prepared.append('X');
            input = prepared.toString();
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i += 2) {
            char a = input.charAt(i);
            char b = input.charAt(i+1);
            int r1=0, c1=0, r2=0, c2=0;
            for(int r=0; r<5; r++) for(int c=0; c<5; c++) {
                if(matrix[r][c] == a) { r1=r; c1=c; }
                if(matrix[r][c] == b) { r2=r; c2=c; }
            }

            char res1, res2;
            if (r1 == r2) { // Same row
                res1 = matrix[r1][(c1 + (encrypt ? 1 : 4)) % 5];
                res2 = matrix[r2][(c2 + (encrypt ? 1 : 4)) % 5];
            } else if (c1 == c2) { // Same column
                res1 = matrix[(r1 + (encrypt ? 1 : 4)) % 5][c1];
                res2 = matrix[(r2 + (encrypt ? 1 : 4)) % 5][c2];
            } else { // Rectangle
                res1 = matrix[r1][c2];
                res2 = matrix[r2][c1];
            }
            result.append(res1).append(res2);
            transcript.add(String.format("[%s%s] -> [%s%s]", a, b, res1, res2));
        }

        response.setResult(result.toString());
        response.setTranscript(transcript);
        return response;
    }

    private ClassicalCipherResponse processTransposition(ClassicalCipherRequest request) {
        ClassicalCipherResponse response = new ClassicalCipherResponse();
        List<String> transcript = new ArrayList<>();
        boolean encrypt = "ENCRYPT".equals(request.getMode());
        int rails;
        try {
            rails = Integer.parseInt(request.getKey().trim());
        } catch (NumberFormatException e) {
            response.setErrorMessage("Khóa Transposition (Rail Fence) phải là một số nguyên (số đường ray).");
            return response;
        }

        if (rails < 2) {
            response.setErrorMessage("Số đường ray phải ít nhất là 2.");
            return response;
        }

        String input = request.getText().toUpperCase().replaceAll("\\s+", "");
        transcript.add("THUẬT TOÁN HOÁN VỊ (RAIL FENCE)");
        transcript.add("Số đường ray: " + rails);
        transcript.add("");

        if (encrypt) {
            char[][] fence = new char[rails][input.length()];
            for(int i=0; i<rails; i++) for(int j=0; j<input.length(); j++) fence[i][j] = '\n';
            
            boolean down = false;
            int row = 0;
            for (int i = 0; i < input.length(); i++) {
                fence[row][i] = input.charAt(i);
                if (row == 0 || row == rails - 1) down = !down;
                row += down ? 1 : -1;
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < rails; i++) {
                StringBuilder railStr = new StringBuilder("Rail " + (i + 1) + ": ");
                for (int j = 0; j < input.length(); j++) {
                    if (fence[i][j] != '\n') {
                        result.append(fence[i][j]);
                        railStr.append(fence[i][j]);
                    } else railStr.append(".");
                }
                transcript.add(railStr.toString());
            }
            response.setResult(result.toString());
        } else {
            // Decryption logic for Rail Fence
            char[][] fence = new char[rails][input.length()];
            for(int i=0; i<rails; i++) for(int j=0; j<input.length(); j++) fence[i][j] = '\n';
            
            boolean down = false;
            int row = 0;
            for (int i = 0; i < input.length(); i++) {
                fence[row][i] = '*';
                if (row == 0 || row == rails - 1) down = !down;
                row += down ? 1 : -1;
            }

            int index = 0;
            for (int i = 0; i < rails; i++) {
                for (int j = 0; j < input.length(); j++) {
                    if (fence[i][j] == '*' && index < input.length()) {
                        fence[i][j] = input.charAt(index++);
                    }
                }
            }

            StringBuilder result = new StringBuilder();
            down = false;
            row = 0;
            for (int i = 0; i < input.length(); i++) {
                result.append(fence[row][i]);
                if (row == 0 || row == rails - 1) down = !down;
                row += down ? 1 : -1;
            }
            response.setResult(result.toString());
            transcript.add("Đã hoàn tất giải hoán vị dựa trên đồ thị zic-zac.");
        }

        response.setTranscript(transcript);
        return response;
    }
}
