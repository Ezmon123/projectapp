package com.cezary.projectboard.security;

import com.cezary.projectboard.domain.User;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j
@Component
public class JwtTokenProvider {
    //Generate Token

    public String generatedToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime() + SecurityConstans.EXPIRATION_TIME);
        String userId = Long.toString(user.getId());
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", Long.toString(user.getId()));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstans.SECRET)
                .compact();
    }

    //Validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstans.SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT Signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT Token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT Token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT Token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }

    //Get user id from token
    public Long getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(SecurityConstans.SECRET).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");
        return Long.parseLong(id);
    }
}
