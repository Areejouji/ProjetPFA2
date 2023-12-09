package com.example.ProjectV1.Repositry;

import com.example.ProjectV1.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface RoleRepositry extends MongoRepository<Role,String> {
    @Query("{'role_Name': ?0}")
    public Role findByRolename(String rolename);


}
