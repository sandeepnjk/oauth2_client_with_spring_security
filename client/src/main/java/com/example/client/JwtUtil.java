package com.example.client;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;






import java.util.Base64;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;



@Component
public class JwtUtil {

    private long accessTokenValidity = 5;
    
    private final String nonpcksprivkey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDLePf2smB3Rq+m"
    		+ "GkykXOa7f+jOZb7qbbTrq01BgMeIdJAT8W8ClR1jFyE/Wdp5wDYVGBUUcBmLHCfX"
    		+ "aN/W85tuxfQurFVqvtoBS4eG6iyFIdJDjjllVPbRmnwgHIp7bCatZOt9pG2H0Xr+"
    		+ "o3sXc/EDMI7RbSSySkHZota8NDODC9hvIcuDO2EkOhHTgWSV/7I/FqqXbcccl3CV"
    		+ "dcgV2QIQnYZYUQltU6/AEa7HJoPAyp67tr54kBtFrFoxw19CTHYyzPAuC80AXGCp"
    		+ "A7a/tjNMPGvkoH2zQirtAbvW0Q1pkmRrz8s86AmhjB2fYTfBckD7eTK5+1CbmNHq"
    		+ "Bo62eLbZAgMBAAECggEAS2QwJIVlfux/CsG14jTV/WVggpFqkvxBTA012+52gFYI"
    		+ "SIttwXw03lFkrU+Cxxf0Mg6xSNhcbpHpJUSjo8axsfLBDkYmhB3hMxekSNYd1tp4"
    		+ "STW6iJRZu8JM+az5ls+06Qx6wRwaxXdV5mH2qvL3u2HTmT+imDkxew1+GVASQhyJ"
    		+ "QpqGNoRAbXzldWKBM7kMVZ36OjytVy3AGi8AoV33JAPbnbA3J7NQziegsfWp+L1F"
    		+ "GNkZENuqaqrgtm2taWzjJdbdO1DCZQZozCVSx/OZjy8CUkmVXNWNLq3RbW4GD23o"
    		+ "FnpTFTVm7w5/Ls+/T9c8gLGmtet3LKZKGaEjIGyGAQKBgQDsSjCMP5MAC88ui5fe"
    		+ "AAwiTDlhysCh5aikg81fN+n7kahGZod8YsrgYj8AP3X1ObLPWvNzhuyDG7iNyY8W"
    		+ "y57ymCIb3Eg4c1jUkbsXDtx7GAJcpu9/Sf0WVtgzQvgfSjAdXrVnP9iK8FHDvgZh"
    		+ "hH4UuGAo4ahlXdrYQQADtKQEwQKBgQDccfz431+7hRhiSBznKW+yqw6fzaWyRda3"
    		+ "J1TyoWoPUfEx4sF0wKCmyLUUlZoPYfXVcvpd7l+teh0BKGMijVyd1iBFPJWhE9It"
    		+ "45/3zQllsPfuvFvYzV7THbnzvOpnez1iZ5AMQoz1PHo0H/dz6fVnGYcV+Saj2N4x"
    		+ "/9xFTkRAGQKBgHG0bdI/kYyXIjbiw8z3tt8WURKnD7WYn2OzbTOh433EpPZjxlTr"
    		+ "8XaysGB9KVU9U7eGq9pvFgctYs0QLkQo7i8NWHSRt0s67oc0LZS1+RFfB1vwlRVq"
    		+ "1FhYqCda1a2w9KBdf+vUhG6voJkSK13n9QghhVyG4pMM7A/ej5Q47CjBAoGAbyOk"
    		+ "55AugqsCdSJoN/8DCnAFSnnyrONa8jIJON46S4kDciQhZoxrMksESGF4L4My3q8+"
    		+ "HLc+U4a6Msg62sOsSwMWd/GoenI0x9/32YXs0/EZvqoGIFLCGOvmwUSgZRWUCBWw"
    		+ "YQ34ZPddoQPwzObKS3VqTqCzY23F8C1Pzz07WZkCgYEA4BazwXY9vEMd3rW0nRKp"
    		+ "w2uih+5Q1aeNQCuq4jMW5FDEg6f8yXIU6/OUN3eo74i4Ku6RtCDkSf+IHeFd1lzm"
    		+ "mTf5IoS2zzviA2Vo2n0g2BW+o74oYm5eJ9uMBUiKzcod/v/tK0dsaF1gD1ZKg1dq"
    		+ "diK1qKzOr4yamMCnc24Lnd8=";


    public String createToken() throws NoSuchAlgorithmException, InvalidKeySpecException {
    	SignatureAlgorithm signatureAlgorithm  = SignatureAlgorithm.RS384;
       byte[] data = Base64.getDecoder().decode((nonpcksprivkey.getBytes()));  
    	PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PrivateKey  key = fact.generatePrivate(spec);
        
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000000000);
        int rand_int2 = rand.nextInt(1000000000);
        String jti = String.valueOf(rand_int1) + String.valueOf(rand_int2);
        //Claims claims = Jwts.claims().setSubject("92808912-9b67-4654-96c0-4cd7ae07405d");
        //claims.setIssuer("92808912-9b67-4654-96c0-4cd7ae07405d");
        // 276756b3-f288-48fb-a500-b0ad0f946d27
        // 92808912-9b67-4654-96c0-4cd7ae07405d  non pord
        //claims.setAudience("https://fhir.epic.com/interconnect-fhir-oauth/oauth2/token");
       // claims.setId(jti);
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        Date issuedAt = new Date(tokenCreateTime.getTime());
        return Jwts.builder()
        		.setHeaderParam("typ", "jwt")
        		.setHeaderParam("alg", signatureAlgorithm)
                //.setClaims(claims)
                .setSubject("92808912-9b67-4654-96c0-4cd7ae07405d")
                .setIssuer("92808912-9b67-4654-96c0-4cd7ae07405d")
                .setAudience("https://fhir.epic.com/interconnect-fhir-oauth/oauth2/token")
                .setId(jti)
                .setExpiration(tokenValidity)
                .setIssuedAt(issuedAt)
                .setNotBefore(issuedAt)
                .signWith(signatureAlgorithm, key)
                .compact();
    }
    
    public PublicKey generateJwtKeyDecryption(String jwtPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] keyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(jwtPublicKey);
        X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(keyBytes);
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    public boolean validateJwtToken(String authToken,String jwtPublicKey) {
        try {
            Jwts.parser().setSigningKey(generateJwtKeyDecryption(jwtPublicKey)).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: {}"+ e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: {}"+ e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: {}"+ e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: {}"+ e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: {}"+ e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("no such algorithm exception");
        } catch (InvalidKeySpecException e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }

        return false;
    }


}
