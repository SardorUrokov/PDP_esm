//package com.example.pdp_esm.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Function;
//
//@Component
//public class JWTUtils {
//
//    public String jwtSingingKey = "secret";
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public boolean hasClaim(String token, String claimName) {
//        final Claims claims = extractAllClaims(token);
//        return claims.get(claimName) != null;
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
//        final Claims claims = extractAllClaims(token);
//        return claimResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token){
//        return Jwts.parser()
//                .setSigningKey(jwtSingingKey)
//                .parseClaimsJwt(token)
//                .getBody();
//    }
//
//    public Boolean isTokenExpired (String token){
//        return extractExpiration(token).before(new Date());
//    }
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDetails);
//    }
//
//    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
//        return createToken(claims, userDetails);
//    }
//
//    public String createToken(Map<String, Object> claims, UserDetails userDetails) {
//        return Jwts.builder().setClaims(claims)
//                .setSubject(userDetails.getUsername())
//                .claim("authorities", userDetails.getAuthorities())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
//                .signWith(SignatureAlgorithm.ES256, jwtSingingKey).compact();
//    }
//
//    public Boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//}
