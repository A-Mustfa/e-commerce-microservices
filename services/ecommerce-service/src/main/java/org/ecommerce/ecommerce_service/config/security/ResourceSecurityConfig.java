package org.ecommerce.ecommerce_service.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@ComponentScan(basePackages = "org.ecommerce.ecommerce_service")
public class ResourceSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/api/v1/customer/create","/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**").permitAll().anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt
                -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("ROLE_");
        converter.setAuthoritiesClaimName("role");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtConverter;
    }
//    @Bean
//    public RedisTemplate<String, Long> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Long> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new GenericToStringSerializer<>(Long.class));
//        return template;
//    }
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(
//            RedisConnectionFactory connectionFactory) {
//
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//
//        Jackson2JsonRedisSerializer<Object> serializer =
//                new Jackson2JsonRedisSerializer<>(Object.class);
//
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(serializer);
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(serializer);
//
//        return template;
//    }
}
