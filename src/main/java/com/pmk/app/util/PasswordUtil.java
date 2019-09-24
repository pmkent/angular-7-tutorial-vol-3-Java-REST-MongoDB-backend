package com.pmk.app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
    public static String getPasswordHash(String password) {
        return generatePasswordHash(password + new AppUtil().getProperty("password.salt"));
    }

    private static String generatePasswordHash(String password) {
        StringBuilder sb = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            char[] digits = {
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f'
            };
            for (byte b: hashedBytes) {
                sb.append(digits[(b & 0xf0) >> 4]);
                sb.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println("PwdUtl: Hashing exception >"+nsae+"<");
        }
        return sb.toString();
    }
}
