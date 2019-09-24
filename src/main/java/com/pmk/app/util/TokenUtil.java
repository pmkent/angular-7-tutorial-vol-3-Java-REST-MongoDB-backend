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
        //System.out.println("TokenUtil.issueToken() uname >"+username+"< URI info >"+uriInfo+"<");
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(uriInfo)
                .setIssuedAt(new Date())
                .setExpiration(APP_UTL.localDateTimeToDate(LocalDateTime.now().plusMinutes(getTokenExpiresIn())))
                .signWith(SignatureAlgorithm.HS256, generateEncryptionSecret()) // TODO Test .signWith(SignatureAlgorithm.HS512, generateEncryptionSecret())
                .compact();
    }

    /* 2019-8-31 */
    public static boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(generateEncryptionSecret()).parseClaimsJws(token);
            //System.out.println("TokenUtil:validateToken VALID returning true");
            return true;
        } catch (Exception e) {
            System.out.println("\nTokenUtil:validateToken INVALID TOKEN Reason : >" + e + " returning false >");
            return false;
        }
    }
    /**/
    public static boolean verify(String jwt, String username) {

        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(generateEncryptionSecret()).parseClaimsJws(jwt)
                    .getBody();

            return claims.getSubject().equals(username);
        } catch (Exception e) { // ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**/
    private static Key generateEncryptionSecret() {
        return new SecretKeySpec(PWD_SALT.getBytes(), 0, PWD_SALT.getBytes().length, "DES");
    }

    // TUTORIAL 2 STARTS
    /**/
    public static String refreshToken(String token) {
        if (token == null) return null;
        String username, uriInfo;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(PWD_SALT.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();
            username =  claims.getSubject();
            uriInfo = claims.getIssuer();
            //System.out.println("TokenUtl:refreshToken Old token validated! Returning new token");
        } catch (Exception e) {
            System.out.println("\n\nTokenUtl:reIssueToken OOPS exception >"+e+"<\n");
            return null;
        }

        return issueToken(username,uriInfo);
    } // end: refreshToken() method

    /* 2018-1-31 */
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

    /* 2019-1-29 */
    public static int getTokenExpiresIn() {
        String expires = APP_UTL.getProperty("token.expires.in");
        //System.out.println("");
        return Integer.parseInt(expires);
    }
    /**/
}
