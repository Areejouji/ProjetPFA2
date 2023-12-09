package com.example.ProjectV1.Config;

import com.example.ProjectV1.Repositry.TokenRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    @Autowired
    private TokenRepositry tokenRepositry;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        final String authHeader=request.getHeader("Authorization");
        final String jwt;
        if(authHeader==null||!authHeader.startsWith("Bearer ")){
            return;
        }
        jwt=authHeader.substring(7);
        var storedToken=tokenRepositry.findToken(jwt)
                .orElse(null);
        if(storedToken!=null)
        {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepositry.save(storedToken);
        }


    }
}
