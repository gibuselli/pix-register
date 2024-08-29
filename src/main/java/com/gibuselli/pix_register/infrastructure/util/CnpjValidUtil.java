package com.gibuselli.pix_register.infrastructure.util;

public class CnpjValidUtil {

    public static boolean isCnpjValid(String cnpj) {
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") ||
                cnpj.equals("22222222222222") || cnpj.equals("33333333333333") ||
                cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
                cnpj.equals("66666666666666") || cnpj.equals("77777777777777") ||
                cnpj.equals("88888888888888") || cnpj.equals("99999999999999")) {

            return false;
        }

        int[] weights = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum = 0;

        for (int i = 0; i < 12; i++) {
            sum += (cnpj.charAt(i) - '0') * weights[i];
        }

        int firstVerifier = sum % 11;
        firstVerifier = firstVerifier < 2 ? 0 : 11 - firstVerifier;

        if (firstVerifier != (cnpj.charAt(12) - '0')) {
            return false;
        }

        sum = 0;

        int[] newWeights = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        for (int i = 0; i < 13; i++) {
            sum += (cnpj.charAt(i) - '0') * newWeights[i];
        }

        int secondVerifier = sum % 11;
        secondVerifier = secondVerifier < 2 ? 0 : 11 - secondVerifier;

        return secondVerifier == (cnpj.charAt(13) - '0');
    }
}
