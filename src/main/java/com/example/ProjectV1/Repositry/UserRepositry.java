package com.example.ProjectV1.Repositry;

import com.example.ProjectV1.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositry extends MongoRepository<User,String> {
    @Query("{'username': ?0}")
    public User findByUsername(String username);
    @Query(value = "{'username': ?0}",fields = "{'tokens':0 , 'authorities':0}")
    public  User findUserById(String id);
    @Query(value="{'username': ?0 }",delete=true)
    public void deleteByUsername(String username);


}
