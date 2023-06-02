package ru.circledevs.tasks.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtService {

    private String secret = "mcdmcdsIIOFEBfejbfjsdfsefBBBEFB";
    private Long expirationToken = (long) 8.64e+7;

    public String generateJwtToken(UserDetails user){
        String username = user.getUsername();
        List<String> authorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Map<String, Object> claims = new HashMap<>(Map.of("authority", authorities));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationToken))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUsername(String token) {
        return parser(token).getSubject();
    }

    public List<GrantedAuthority> getAuthority(String token) {
        Claims parserClaims = parser(token);
        List<String> authority = (List<String>) parserClaims.get("authority");
        List<SimpleGrantedAuthority> authority1 = authority.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authority1.stream()
                .map(it -> (GrantedAuthority) it)
                .collect(Collectors.toList());
    }

    private Claims parser(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
