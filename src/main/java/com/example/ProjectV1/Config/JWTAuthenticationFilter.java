package com.example.ProjectV1.Config;

import com.example.ProjectV1.Repositry.TokenRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenRepositry tokenRepositry;
    @Override
    protected void doFilterInternal
            (@NonNull HttpServletRequest request,
             @NonNull HttpServletResponse response,
             @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        final String jwt;
        final String username;
        if(authHeader==null||!authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return;
        }
        jwt=authHeader.substring(7);
        username=jwtService.extractusername(jwt);//get the username from the token
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
            var isTokenValid=tokenRepositry.findToken(jwt)
                    .map(t->!t.isExpired()&&!t.isRevoked())
                    .orElse(false);
            if(jwtService.isTokenValid(jwt,userDetails) && isTokenValid){
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
              SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        filterChain.doFilter(request,response);
    }
}
