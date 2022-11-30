package com.revature.reimbursement.utils;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.util.Properties;

public class JwtConfig {

    private final int expiration = 60 * 60 * 1000;
    private final SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;

    private final Key signingKey;
    private final Properties properties = new Properties();

    public JwtConfig() {
        try {
            properties.load(new FileReader("src/main/resources/db.properties"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        byte[] saltyBytes = DatatypeConverter.parseBase64Binary(properties.getProperty("salt"));
        signingKey = new SecretKeySpec(saltyBytes, sigAlg.getJcaName());
    }

    public int getExpiration() {
        return expiration;
    }

    public SignatureAlgorithm getSigAlg() {
        return sigAlg;
    }

    public Key getSigningKey() {
        return signingKey;
    }
}
