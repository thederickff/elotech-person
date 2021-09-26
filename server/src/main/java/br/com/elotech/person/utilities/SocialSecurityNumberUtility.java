package br.com.elotech.person.utilities;

public class SocialSecurityNumberUtility {

    private SocialSecurityNumberUtility()
    {
    }

    public static boolean isValid(String ss)
    {
        String ssn = ss.replace(".", "").replace("-", "");

        if (isValidSocialSecurityNumber(ssn)) {
            int lastDigit = calculateLastDigit(ssn);
            int secondToLastDigit = calculateSecondToLastDigit(ssn);
            char[] digits = ssn.toCharArray();
            if (lastDigit == Character.getNumericValue(digits[9])
                    && secondToLastDigit == Character.getNumericValue(digits[10])) {
                return true;
            }
        }

        return false;
    }

    private static int calculateLastDigit(String ssn)
    {
        int sum = 0, digitVerifier = 0;
        int[] verifier = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        char[] digits = ssn.toCharArray();

        for (int i = 0; i < verifier.length; i++) {
            sum += verifier[i] * Character.getNumericValue(digits[i]);
        }

        if (sum % 11 >= 2) {
            digitVerifier = 11 - (sum % 11);
        }

        return digitVerifier;
    }

    private static int calculateSecondToLastDigit(String ssn)
    {
        int sum = 0, digitVerifier = 0;
        int[] verifier = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        char[] digits = ssn.toCharArray();

        for (int i = 0; i < verifier.length; i++) {
            sum += verifier[i] * Character.getNumericValue(digits[i]);
        }

        if (sum % 11 >= 2) {
            digitVerifier = 11 - (sum % 11);
        }

        return digitVerifier;
    }

    private static boolean isValidSocialSecurityNumber(String ssn)
    {
        return !(ssn.length() != 11 || ssn.equals("00000000000000")
                || ssn.equals("11111111111111") || ssn.equals("22222222222222")
                || ssn.equals("33333333333333") || ssn.equals("44444444444444")
                || ssn.equals("55555555555555") || ssn.equals("66666666666666")
                || ssn.equals("77777777777777") || ssn.equals("88888888888888")
                || ssn.equals("99999999999999"));
    }
}
