package com.gateway;

import com.gateway.user.RegisterRequest;
import com.gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeyCloakUserSyncFilter implements WebFilter {
    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");//optional
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        RegisterRequest request = getUserDetails(token);

        if(userId == null){
            userId= request.getKeycloakId();
        }

        return null;
    }

    private RegisterRequest getUserDetails(String token) {//for extracting the token
        try {
            String tokenWithoutBearer = token.replace("Bearer","").trim();
            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            RegisterRequest request = new RegisterRequest();
            request.setEmail(claims.getStringClaim("email"));
            request.setKeycloakId(claims.getStringClaim("sub"));
            request.setFirstName(claims.getStringClaim("given_name"));
            request.setLastName(claims.getStringClaim("family_name"));

            request.setPassword("Testing@123");



        }catch (ParseException e){
            throw new RuntimeException(e);
        }
        return null;
    }
}
