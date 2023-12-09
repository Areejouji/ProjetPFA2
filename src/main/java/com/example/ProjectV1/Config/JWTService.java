package com.example.ProjectV1.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private final String SECRET_KEY="635266556A586E327234753778214125442A472D4B6150645367566B59703373";
    public String extractusername(String token) {

         return extractionClaim(token, Claims::getSubject);
    }
    public<T> T extractionClaim(String token , Function<Claims,T> claimsResolver){
        final Claims claims=exctractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims exctractAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSingleInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username=extractusername(token);
        return (username.equals(userDetails.getUsername()) ) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
         return extractionClaim(token,Claims::getExpiration);
    }

    public String generateToken(
            Map<String , Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSingleInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSingleInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
