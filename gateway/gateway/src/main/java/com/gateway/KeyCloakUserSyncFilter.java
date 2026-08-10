package com.gateway;

import com.gateway.user.RegisterRequest;
import com.gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import java.text.ParseException;
import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeyCloakUserSyncFilter implements WebFilter {
    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");//optional
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        RegisterRequest registerRequest = getUserDetails(token);

        if(userId == null){
            userId= registerRequest.getKeycloakId();
        }
        if(userId != null && token != null){
            String finalUserId = userId;
            userService.validateUser(userId)
                    .flatMap(exist->{
                        if(!exist){
                            if(registerRequest!=null){
                                return userService.registerUser(registerRequest)
                                        .then(Mono.empty());
                            }else{
                                return Mono.empty();
                            }
                        }else{
                            log.info("user already exists , Skipping sync");
                            return Mono.empty();
                        }
                    })
                    .then(
                            Mono.defer(()->{
                                ServerHttpRequest mutateRequest = (ServerHttpRequest) exchange.getRequest().mutate()
                                        .header("X-User-ID", finalUserId)
                                        .build();
                                return chain.filter(exchange.mutate().request((Consumer<org.springframework.http.server.reactive.ServerHttpRequest.Builder>) mutateRequest).build());
                            }
                    ));

        }

        return chain.filter(exchange);
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

            return request;

        }catch (ParseException e){
            throw new RuntimeException(e);
        }

    }
}
