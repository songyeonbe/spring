package com.jocoos.spring.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private static String RSA_PUBLIC_KEY_STR = "-----BEGIN PUBLIC KEY-----\n" +
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCx2BGXU2Wt2UR3C9dWZ/1yNo71\n" +
            "sK+XJg8+Qni+CwbG4ToAISjYluKe/prIRSdFtoa3eGw8VsCWGt/QQFzlwvgb/VI5\n" +
            "kqAqEx9/nGGhVaDqXPpq2NbFH3186jNbDEur3Kvb8CtL34ovMY8nqzQlDCTL4VpE\n" +
            "4jMLEzG0McwvVhvdnwIDAQAB\n" +
            "-----END PUBLIC KEY-----";
    private static String RSA_PRIVATE_KEY_STR = "-----BEGIN PRIVATE KEY-----\n" +
            "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALHYEZdTZa3ZRHcL\n" +
            "11Zn/XI2jvWwr5cmDz5CeL4LBsbhOgAhKNiW4p7+mshFJ0W2hrd4bDxWwJYa39BA\n" +
            "XOXC+Bv9UjmSoCoTH3+cYaFVoOpc+mrY1sUffXzqM1sMS6vcq9vwK0vfii8xjyer\n" +
            "NCUMJMvhWkTiMwsTMbQxzC9WG92fAgMBAAECgYEAnqte7ns6oYPR4MCQzAeViRwC\n" +
            "KlQMJTeQrASUQ0IFNtVlcQ4kqjDcWexgXkPMiRcTVIe9UYw44PNzMhLqjxeHUbv1\n" +
            "3Vwrm7kMPfebkSxp5kXjvsqK64btm6IpM1ipBivQ6oKXiwyTpDWsQeTly3iN6d7A\n" +
            "udMJq22rC7O8G51wSEECQQDkJ5xGvFF2wTdgLMh3SMde2quc4iJRtFFn+iSK1RYA\n" +
            "7Ne1CZWChMO+QsIu9W3EJvbw+gzlZyBzPjEce5pI/uUhAkEAx4yRP1Q5DUHHnuX6\n" +
            "f3RTPeW5Plaw0Jv0E6o4x1Rchqr3lrqE0rSIXySySsbQ9mJESI4rkCX864R9zddC\n" +
            "e+eqvwJBAKgbB2qXFu8JLmQOmyZI2Z1qQUg21lKdKd4S0Rn6J1xHJiieBytD53Yo\n" +
            "eitfaqE8lHa5xwbAFYjUKhtydxuRtAECQQC1/Iumvu/9GCWu07zdqkAUbkLACJ3e\n" +
            "0qwJly+LEnQD2T5N97MU2tKYOsZjjeibG0l9BjPSleKRBFcbeBrJF6HPAkBocGwC\n" +
            "O/J8U3l2PhIOmzXSmaWGYd33uDPkvnbC86wFYCMwI38MHfhGrtmG9amnFMSVZjF+\n" +
            "HbRanBNQ25kWWwZE\n" +
            "-----END PRIVATE KEY-----";

    private static RSAPublicKey RSA_PUBLIC_KEY;
    private static RSAPrivateKey RSA_PRIVATE_KEY;

    @PostConstruct
    protected void readPrivateKey() throws IOException, GeneralSecurityException {
        final KeyFactory keyPairGenerator = KeyFactory.getInstance("RSA");

        try (PemReader publicKeyPemReader = new PemReader(new StringReader(RSA_PUBLIC_KEY_STR))) {
            final PemObject publicKeyPemObject =  publicKeyPemReader.readPemObject();
            RSA_PUBLIC_KEY = (RSAPublicKey) keyPairGenerator.generatePublic(new X509EncodedKeySpec(publicKeyPemObject.getContent()));

        }

        try (PemReader privateKeyPemReader = new PemReader(new StringReader(RSA_PRIVATE_KEY_STR))) {
            final PemObject privateKeyPemObject = privateKeyPemReader.readPemObject();
            RSA_PRIVATE_KEY =  (RSAPrivateKey) keyPairGenerator.generatePrivate(new PKCS8EncodedKeySpec(privateKeyPemObject.getContent()));
        }

    }


    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(RSA_PRIVATE_KEY).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    // Token Generator
    public String createToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.RS256, RSA_PRIVATE_KEY).compact();
    }


    // validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
