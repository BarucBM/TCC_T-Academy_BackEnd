package com.TCC.infra.security;

import com.TCC.domain.user.TokenResponseDTO;
import com.TCC.domain.user.User;
import com.TCC.infra.security.exceptions.UnauthorizedException;
import com.TCC.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenService {

    private final UserRepository userRepository;

    private Set<String> blacklist = new HashSet<>();

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${auth.jwt.token.expiration}")
    private Integer tokenExpiration;

    @Value("${auth.jwt.refresh-token.expiration}")
    private Integer refreshTokenExpiration;

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String buildToken(User user, Integer expiration) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("tcc-api")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(generateExpirationDate(expiration))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating auth token", exception);
        }
    }

    public TokenResponseDTO generateToken(User user) {
        return TokenResponseDTO
                .builder()
                .token(buildToken(user, this.tokenExpiration))
                .refreshToken(buildToken(user, this.refreshTokenExpiration))
                .build();
    }

    public TokenResponseDTO generateRefreshToken(String refreshToken) {
        User user = (User) userRepository.findByEmail(validateToken(refreshToken));

        if (user == null) {
            throw new UnauthorizedException("Unauthorized");
        }

        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return TokenResponseDTO
                .builder()
                .token(buildToken(user, this.tokenExpiration))
                .refreshToken(buildToken(user, this.refreshTokenExpiration))
                .build();
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("tcc-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "Invalid Token";
        }
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        return authHeader != null ? authHeader.replace("Bearer ", "") : null;
    }

    public String getUserIdFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("tcc-api").build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getClaim("id").asString();
    }

    public void addTokenToBlacklist(String token) {
        blacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }

    private Instant generateExpirationDate(Integer expiration) {
        return LocalDateTime.now()
                .plusHours(expiration)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}