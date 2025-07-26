package com.example.libreria_universitaria.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        // Genera una chiave sicura per l'algoritmo HS256
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // Codifica la chiave in una stringa Base64 URL-safe
        String secretString = Base64.getUrlEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated JWT Secret Key (Base64 URL-safe): " + secretString);
    }
}
