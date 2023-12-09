package com.example.ProjectV1.Repositry;

import com.example.ProjectV1.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepositry extends MongoRepository<Doctor,String> {
    @Query("{'user_Id_Doctor': ?0}")
    public Doctor findDoctorByuserId(String user_Id);


}
