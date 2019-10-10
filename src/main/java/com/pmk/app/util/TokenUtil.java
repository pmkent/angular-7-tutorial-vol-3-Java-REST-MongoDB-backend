package com.pmk.app.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
        } catch (ExpiredJwtException e) {
            System.out.println(" Token expired (ExpiredJwtException) : >"+e+"<");
        } catch(Exception e){
            System.out.println("Some other JWT parsing Exception : >"+e+"<");
        }
        return false;
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
    }

    private static int getTokenExpiresIn() {
        String expires = APP_UTL.getProperty("token.expires.in");
        return Integer.parseInt(expires);
    }
}
