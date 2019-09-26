package com.pmk.app.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

public class TokenUtil {
    private static AppUtil APP_UTL = new AppUtil();
    private static final String PWD_SALT = APP_UTL.getProperty("password.salt");

    public static String issueToken(String username, String uriInfo) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(uriInfo)
                .setIssuedAt(new Date())
                .setExpiration(APP_UTL.localDateTimeToDate(LocalDateTime.now().plusMinutes(getTokenExpiresIn())))
                .signWith(SignatureAlgorithm.HS256, generateEncryptionSecret())
                .compact();
    }

    public static boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(generateEncryptionSecret()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("\nTokenUtil:validateToken INVALID TOKEN Reason : >" + e + " returning false >");
            return false;
        }
    }

    public static boolean verify(String jwt, String username) {

        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(generateEncryptionSecret()).parseClaimsJws(jwt)
                    .getBody();

            return claims.getSubject().equals(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static Key generateEncryptionSecret() {
        return new SecretKeySpec(PWD_SALT.getBytes(), 0, PWD_SALT.getBytes().length, "DES");
    }

    public static String getUserEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(PWD_SALT.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    } // end: getUserEmailFromToken() method

    private static int getTokenExpiresIn() {
        String expires = APP_UTL.getProperty("token.expires.in");
        return Integer.parseInt(expires);
    }
    /**/
}
