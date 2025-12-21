package org.ecommerce.authserver.services;

import lombok.RequiredArgsConstructor;
import org.ecommerce.authserver.utils.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder tokenEncoder;

    public String generate(Authentication auth){

        Instant now = Instant.now();

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Long userId = userDetails.getId();

        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claimSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .claim("role", scope)
                .claim("email", userDetails.getUsername())
                .claim("userId", userId)
                .subject(auth.getName())
                .build();

        return tokenEncoder.encode(JwtEncoderParameters.from(claimSet)).getTokenValue();
    }

    }