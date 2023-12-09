package com.example.ProjectV1.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    public static final String[] ENDPOINTS_WHITELIST = {
            "/css/**",
            "/",
            "/api/authentication/login",
            "/home",
            "/api/user/save/admin",
            "/api/user/save/doctor",
            "/api/user/doctor}",
             "/ecg",
             "/user/**",
            "/recording/save/**" ,
            "/recording/segment/**",
            "/text/{filename}"};
    @Autowired
    private JWTAuthenticationFilter JwtAuthFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private LogoutHandler logoutHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(request ->
                        request.antMatchers(ENDPOINTS_WHITELIST).permitAll()
                                .anyRequest().authenticated())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(JwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/authentication/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()));
                
        return http.build();
    }
}
