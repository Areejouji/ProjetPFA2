package com.example.ProjectV1.Repositry;

import com.example.ProjectV1.model.AnalysisData;
import com.example.ProjectV1.model.Doctor;
import com.example.ProjectV1.model.Segment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisDataRepositry extends MongoRepository<AnalysisData,String> {
    @Query("{'analyzed_by': ?0}")
    public Segment findByDoctor(Doctor doctor);

}
