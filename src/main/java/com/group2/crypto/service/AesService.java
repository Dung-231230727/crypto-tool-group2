package com.group2.crypto.service;

import com.group2.crypto.model.AesRequest;
import com.group2.crypto.model.AesResponse;
import com.group2.crypto.model.AesStep;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

@Service
public class AesService {

    private static final byte[] SBOX = {
        (byte) 0x63, (byte) 0x7c, (byte) 0x77, (byte) 0x7b, (byte) 0xf2, (byte) 0x6b, (byte) 0x6f, (byte) 0xc5, (byte) 0x30, (byte) 0x01, (byte) 0x67, (byte) 0x2b, (byte) 0xfe, (byte) 0xd7, (byte) 0xab, (byte) 0x76,
        (byte) 0xca, (byte) 0x82, (byte) 0xc9, (byte) 0x7d, (byte) 0xfa, (byte) 0x59, (byte) 0x47, (byte) 0xf0, (byte) 0xad, (byte) 0xd4, (byte) 0xa2, (byte) 0xaf, (byte) 0x9c, (byte) 0xa4, (byte) 0x72, (byte) 0xc0,
        (byte) 0xb7, (byte) 0xfd, (byte) 0x93, (byte) 0x26, (byte) 0x36, (byte) 0x3f, (byte) 0xf7, (byte) 0xcc, (byte) 0x34, (byte) 0xa5, (byte) 0xe5, (byte) 0xf1, (byte) 0x71, (byte) 0xd8, (byte) 0x31, (byte) 0x15,
        (byte) 0x04, (byte) 0xc7, (byte) 0x23, (byte) 0xc3, (byte) 0x18, (byte) 0x96, (byte) 0x05, (byte) 0x9a, (byte) 0x07, (byte) 0x12, (byte) 0x80, (byte) 0xe2, (byte) 0xeb, (byte) 0x27, (byte) 0xb2, (byte) 0x75,
        (byte) 0x09, (byte) 0x83, (byte) 0x2c, (byte) 0x1a, (byte) 0x1b, (byte) 0x6e, (byte) 0x5a, (byte) 0xa0, (byte) 0x52, (byte) 0x3b, (byte) 0xd6, (byte) 0xb3, (byte) 0x29, (byte) 0xe3, (byte) 0x2f, (byte) 0x84,
        (byte) 0x53, (byte) 0xd1, (byte) 0x00, (byte) 0xed, (byte) 0x20, (byte) 0xfc, (byte) 0xb1, (byte) 0x5b, (byte) 0x6a, (byte) 0xcb, (byte) 0xbe, (byte) 0x39, (byte) 0x4a, (byte) 0x4c, (byte) 0x58, (byte) 0xcf,
        (byte) 0xd0, (byte) 0xef, (byte) 0xaa, (byte) 0xfb, (byte) 0x43, (byte) 0x4d, (byte) 0x33, (byte) 0x85, (byte) 0x45, (byte) 0xf9, (byte) 0x02, (byte) 0x7f, (byte) 0x50, (byte) 0x3c, (byte) 0x9f, (byte) 0xa8,
        (byte) 0x51, (byte) 0xa3, (byte) 0x40, (byte) 0x8f, (byte) 0x92, (byte) 0x9d, (byte) 0x38, (byte) 0xf5, (byte) 0xbc, (byte) 0xb6, (byte) 0xda, (byte) 0x21, (byte) 0x10, (byte) 0xff, (byte) 0xf3, (byte) 0xd2,
        (byte) 0xcd, (byte) 0x0c, (byte) 0x13, (byte) 0xec, (byte) 0x5f, (byte) 0x97, (byte) 0x44, (byte) 0x17, (byte) 0xc4, (byte) 0xa7, (byte) 0x7e, (byte) 0x3d, (byte) 0x64, (byte) 0x5d, (byte) 0x19, (byte) 0x73,
        (byte) 0x60, (byte) 0x81, (byte) 0x4f, (byte) 0xdc, (byte) 0x22, (byte) 0x2a, (byte) 0x90, (byte) 0x88, (byte) 0x46, (byte) 0xee, (byte) 0xb8, (byte) 0x14, (byte) 0xde, (byte) 0x5e, (byte) 0x0b, (byte) 0xdb,
        (byte) 0xe0, (byte) 0x32, (byte) 0x3a, (byte) 0x0a, (byte) 0x49, (byte) 0x06, (byte) 0x24, (byte) 0x5c, (byte) 0xc2, (byte) 0xd3, (byte) 0xac, (byte) 0x62, (byte) 0x91, (byte) 0x95, (byte) 0xe4, (byte) 0x79,
        (byte) 0xe7, (byte) 0xc8, (byte) 0x37, (byte) 0x6d, (byte) 0x8d, (byte) 0xd5, (byte) 0x4e, (byte) 0xa9, (byte) 0x6c, (byte) 0x56, (byte) 0xf4, (byte) 0xea, (byte) 0x65, (byte) 0x7a, (byte) 0xae, (byte) 0x08,
        (byte) 0xba, (byte) 0x78, (byte) 0x25, (byte) 0x2e, (byte) 0x1c, (byte) 0xa6, (byte) 0xb4, (byte) 0xc6, (byte) 0xe8, (byte) 0xdd, (byte) 0x74, (byte) 0x1f, (byte) 0x4b, (byte) 0xbd, (byte) 0x8b, (byte) 0x8a,
        (byte) 0x70, (byte) 0x3e, (byte) 0xb5, (byte) 0x66, (byte) 0x48, (byte) 0x03, (byte) 0xf6, (byte) 0x0e, (byte) 0x61, (byte) 0x35, (byte) 0x57, (byte) 0xb9, (byte) 0x86, (byte) 0xc1, (byte) 0x1d, (byte) 0x9e,
        (byte) 0xe1, (byte) 0xf8, (byte) 0x98, (byte) 0x11, (byte) 0x69, (byte) 0xd9, (byte) 0x8e, (byte) 0x94, (byte) 0x9b, (byte) 0x1e, (byte) 0x87, (byte) 0xe9, (byte) 0xce, (byte) 0x55, (byte) 0x28, (byte) 0xdf,
        (byte) 0x8c, (byte) 0xa1, (byte) 0x89, (byte) 0x0d, (byte) 0xbf, (byte) 0xe6, (byte) 0x42, (byte) 0x68, (byte) 0x41, (byte) 0x99, (byte) 0x2d, (byte) 0x0f, (byte) 0xb0, (byte) 0x54, (byte) 0xbb, (byte) 0x16
    };

    private static final byte[] INV_SBOX = new byte[256];
    static {
        for (int i = 0; i < 256; i++) {
            INV_SBOX[SBOX[i] & 0xFF] = (byte) i;
        }
    }

    private static final int[] RCON = {
        0x01000000, 0x02000000, 0x04000000, 0x08000000, 0x10000000, 0x20000000, 0x40000000, 0x80000000, 0x1B000000, 0x36000000
    };

    public AesResponse process(AesRequest request) {
        AesResponse response = new AesResponse();
        response.setInputData(request.getData());
        response.setInputKey(request.getKey());
        response.setMode(request.getMode());

        try {
            byte[] data = HexFormat.of().parseHex(request.getData().replaceAll("\\s+", ""));
            byte[] key = HexFormat.of().parseHex(request.getKey().replaceAll("\\s+", ""));

            if (data.length != 16 || key.length != 16) {
                response.setErrorMessage("Dữ liệu và khóa phải đủ 128-bit (32 ký tự hex).");
                return response;
            }

            List<AesStep> steps = new ArrayList<>();
            byte[] result;
            if ("encrypt".equalsIgnoreCase(request.getMode())) {
                result = encrypt(data, key, steps);
            } else {
                result = decrypt(data, key, steps);
            }
            response.setResult(HexFormat.of().formatHex(result).toUpperCase());
            response.setSteps(steps);

        } catch (Exception e) {
            response.setErrorMessage("Lỗi xử lý: " + e.getMessage());
        }

        return response;
    }

    private byte[] encrypt(byte[] plaintext, byte[] key, List<AesStep> steps) {
        byte[][][] roundKeys = keyExpansion(key);
        
        // --- Step 0: BẢN RÕ (INPUT) ---
        AesStep inputStep = new AesStep("BẢN RÕ (INPUT)", -1);
        byte[][] state = bytesToState(plaintext);
        inputStep.setStateStart(copyState(state));
        inputStep.setStateAfterAdd(copyState(state)); // No change yet
        steps.add(inputStep);

        // --- Step 1: KHỞI TẠO (AddRoundKey 0) ---
        AesStep initStep = new AesStep("KHỞI TẠO", 0);
        initStep.setStateStart(copyState(state));
        initStep.setRoundKey(roundKeys[0]);
        addRoundKey(state, roundKeys[0]);
        initStep.setStateAfterAdd(copyState(state));
        steps.add(initStep);

        // --- Step 2-10: Rounds 1-9 ---
        for (int round = 1; round <= 9; round++) {
            AesStep roundStep = new AesStep("VÒNG " + round, round);
            roundStep.setStateStart(copyState(state));
            
            subBytes(state);
            roundStep.setStateAfterSub(copyState(state));
            
            shiftRows(state);
            roundStep.setStateAfterShift(copyState(state));
            
            mixColumns(state);
            roundStep.setStateAfterMix(copyState(state));
            
            roundStep.setRoundKey(roundKeys[round]);
            addRoundKey(state, roundKeys[round]);
            roundStep.setStateAfterAdd(copyState(state));
            
            steps.add(roundStep);
        }

        // --- Step 11: Round 10: Cuối ---
        AesStep finalStep = new AesStep("VÒNG 10 (CUỐI)", 10);
        finalStep.setStateStart(copyState(state));
        
        subBytes(state);
        finalStep.setStateAfterSub(copyState(state));
        
        shiftRows(state);
        finalStep.setStateAfterShift(copyState(state));
        
        finalStep.setRoundKey(roundKeys[10]);
        addRoundKey(state, roundKeys[10]);
        finalStep.setStateAfterAdd(copyState(state));
        
        steps.add(finalStep);

        return stateToBytes(state);
    }

    private byte[] decrypt(byte[] ciphertext, byte[] key, List<AesStep> steps) {
        byte[][][] roundKeys = keyExpansion(key);
        
        // --- Step 0: BẢN MÃ (INPUT) ---
        AesStep inputStep = new AesStep("BẢN MÃ (INPUT)", -1);
        byte[][] state = bytesToState(ciphertext);
        inputStep.setStateStart(copyState(state));
        inputStep.setStateAfterAdd(copyState(state));
        steps.add(inputStep);

        // --- Step 1: KHỞI TẠO GIẢI MÃ ---
        AesStep initStep = new AesStep("KHỞI TẠO GIẢI MÃ", 0);
        initStep.setStateStart(copyState(state));
        initStep.setRoundKey(roundKeys[10]);
        addRoundKey(state, roundKeys[10]);
        initStep.setStateAfterAdd(copyState(state));
        steps.add(initStep);

        // --- Rounds 1-9 (10-1 in reverse logic) ---
        for (int round = 9; round >= 1; round--) {
            AesStep roundStep = new AesStep("VÒNG " + (10 - round), 10 - round);
            roundStep.setStateStart(copyState(state));
            
            invShiftRows(state);
            roundStep.setStateAfterShift(copyState(state));
            
            invSubBytes(state);
            roundStep.setStateAfterSub(copyState(state));
            
            roundStep.setRoundKey(roundKeys[round]);
            addRoundKey(state, roundKeys[round]);
            roundStep.setStateAfterAdd(copyState(state));
            
            invMixColumns(state);
            roundStep.setStateAfterMix(copyState(state));
            
            steps.add(roundStep);
        }

        // --- Final Step: Cuối (AddRoundKey 0) ---
        AesStep lastStep = new AesStep("VÒNG CUỐI (GIẢI MÃ)", 10);
        lastStep.setStateStart(copyState(state));
        
        invShiftRows(state);
        lastStep.setStateAfterShift(copyState(state));
        
        invSubBytes(state);
        lastStep.setStateAfterSub(copyState(state));
        
        lastStep.setRoundKey(roundKeys[0]);
        addRoundKey(state, roundKeys[0]);
        lastStep.setStateAfterAdd(copyState(state));
        
        steps.add(lastStep);

        return stateToBytes(state);
    }

    private byte[][] copyState(byte[][] state) {
        byte[][] copy = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(state[i], 0, copy[i], 0, 4);
        }
        return copy;
    }

    private byte[][] bytesToState(byte[] bytes) {
        byte[][] state = new byte[4][4];
        for (int i = 0; i < 16; i++) {
            state[i % 4][i / 4] = bytes[i];
        }
        return state;
    }

    private byte[] stateToBytes(byte[][] state) {
        byte[] bytes = new byte[16];
        for (int i = 0; i < 16; i++) {
            bytes[i] = state[i % 4][i / 4];
        }
        return bytes;
    }

    private void addRoundKey(byte[][] state, byte[][] roundKey) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][j] ^= roundKey[i][j];
            }
        }
    }

    private void subBytes(byte[][] state) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][j] = SBOX[state[i][j] & 0xFF];
            }
        }
    }

    private void invSubBytes(byte[][] state) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][j] = INV_SBOX[state[i][j] & 0xFF];
            }
        }
    }

    private void shiftRows(byte[][] state) {
        for (int r = 1; r < 4; r++) {
            byte[] row = new byte[4];
            for (int c = 0; c < 4; c++) row[c] = state[r][c];
            for (int c = 0; c < 4; c++) state[r][c] = row[(c + r) % 4];
        }
    }

    private void invShiftRows(byte[][] state) {
        for (int r = 1; r < 4; r++) {
            byte[] row = new byte[4];
            for (int c = 0; c < 4; c++) row[c] = state[r][c];
            for (int c = 0; c < 4; c++) state[r][c] = row[(c - r + 4) % 4];
        }
    }

    private void mixColumns(byte[][] state) {
        for (int c = 0; c < 4; c++) {
            byte b0 = state[0][c], b1 = state[1][c], b2 = state[2][c], b3 = state[3][c];
            state[0][c] = (byte) (gm(2, b0) ^ gm(3, b1) ^ b2 ^ b3);
            state[1][c] = (byte) (b0 ^ gm(2, b1) ^ gm(3, b2) ^ b3);
            state[2][c] = (byte) (b0 ^ b1 ^ gm(2, b2) ^ gm(3, b3));
            state[3][c] = (byte) (gm(3, b0) ^ b1 ^ b2 ^ gm(2, b3));
        }
    }

    private void invMixColumns(byte[][] state) {
        for (int c = 0; c < 4; c++) {
            byte b0 = state[0][c], b1 = state[1][c], b2 = state[2][c], b3 = state[3][c];
            state[0][c] = (byte) (gm(14, b0) ^ gm(11, b1) ^ gm(13, b2) ^ gm(9, b3));
            state[1][c] = (byte) (gm(9, b0) ^ gm(14, b1) ^ gm(11, b2) ^ gm(13, b3));
            state[2][c] = (byte) (gm(13, b0) ^ gm(9, b1) ^ gm(14, b2) ^ gm(11, b3));
            state[3][c] = (byte) (gm(11, b0) ^ gm(13, b1) ^ gm(9, b2) ^ gm(14, b3));
        }
    }

    private byte gm(int a, byte b) {
        int res = 0;
        int bb = b & 0xFF;
        for (int i = 0; i < 8; i++) {
            if ((a & 1) != 0) res ^= bb;
            boolean hiBit = (bb & 0x80) != 0;
            bb <<= 1;
            if (hiBit) bb ^= 0x1B;
            a >>= 1;
        }
        return (byte) (res & 0xFF);
    }

    private byte[][][] keyExpansion(byte[] key) {
        int[] w = new int[44];
        for (int i = 0; i < 4; i++) {
            w[i] = (key[4 * i] << 24) | ((key[4 * i + 1] & 0xFF) << 16) | ((key[4 * i + 2] & 0xFF) << 8) | (key[4 * i + 3] & 0xFF);
        }

        for (int i = 4; i < 44; i++) {
            int temp = w[i - 1];
            if (i % 4 == 0) {
                temp = subWord(rotWord(temp)) ^ RCON[i / 4 - 1];
            }
            w[i] = w[i - 4] ^ temp;
        }

        byte[][][] roundKeys = new byte[11][4][4];
        for (int round = 0; round < 11; round++) {
            for (int j = 0; j < 4; j++) {
                int word = w[round * 4 + j];
                roundKeys[round][0][j] = (byte) (word >>> 24);
                roundKeys[round][1][j] = (byte) (word >>> 16);
                roundKeys[round][2][j] = (byte) (word >>> 8);
                roundKeys[round][3][j] = (byte) word;
            }
        }

        return roundKeys;
    }

    private int rotWord(int w) {
        return (w << 8) | (w >>> 24);
    }

    private int subWord(int w) {
        return ((SBOX[(w >>> 24) & 0xFF] & 0xFF) << 24) |
               ((SBOX[(w >>> 16) & 0xFF] & 0xFF) << 16) |
               ((SBOX[(w >>> 8) & 0xFF] & 0xFF) << 8) |
               (SBOX[w & 0xFF] & 0xFF);
    }
}
