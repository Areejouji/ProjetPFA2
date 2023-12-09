package com.example.ProjectV1.Repositry;

import com.example.ProjectV1.model.Recording;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RecordingRepositry extends MongoRepository<Recording,String> {

    @Query("{'status': ?0}")
    List<Recording> findByStatus(String status);
    @Query("{'added_At': ?0}")
    List<Recording> findByaddtime(String adddate);




}
