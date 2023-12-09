package com.example.ProjectV1.authentication;

import com.example.ProjectV1.Config.JWTService;
import com.example.ProjectV1.Repositry.TokenRepositry;
import com.example.ProjectV1.Token.Token;
import com.example.ProjectV1.Token.TokenType;
import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.Repositry.UserRepositry;
import com.example.ProjectV1.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticatinServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepositry userRepositry;


    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;
    @Autowired
    private TokenRepositry tokenRepositry;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws ObjectCollectionException {

          authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(
                          request.getUsername(),
                          request.getPassword()
                  )
          );
          var user=userRepositry.findByUsername(request.getUsername());
          if(user==null)
          {
              throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(request.getUsername()));
          }
         else {

             revokeAllUserToken(user);
         var jwtToken= jwtService.generateToken(user);
         Token savedtoken=saveUserToken(user,jwtToken);
         user.getTokens().add(savedtoken);
         userRepositry.save(user);
         return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();}



    }
    private void revokeAllUserToken(User user){
        var validtoken=tokenRepositry.findAllValidTokenByUser(user.getUser_Id(),false,false);
        if(validtoken.isEmpty()){
            return;
        }
        validtoken.forEach(t->{
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepositry.saveAll(validtoken);
    }
    private Token saveUserToken(User user, String jwtToken){
    var token=new Token();
                token.setUser_Token(user.getUser_Id());
                token.setToken(jwtToken);
                token.setTokenType(TokenType.BEARER);
                token.setExpired(false);
                token.setRevoked(false);
                return tokenRepositry.save(token);}


}
