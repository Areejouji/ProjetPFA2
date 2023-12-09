package com.example.ProjectV1.Repositry;

import com.example.ProjectV1.Token.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface TokenRepositry  extends MongoRepository<Token,String> {
    @Query("{'user_Token': ?0,'expired': ?1,'revoked':?2}")
    ArrayList<Token> findAllValidTokenByUser(String id,Boolean expire,Boolean revoke);
    @Query("{'token': ?0}")
    Optional<Token> findToken(String token);

}
