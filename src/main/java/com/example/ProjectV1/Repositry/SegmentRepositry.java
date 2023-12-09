package com.example.ProjectV1.Repositry;

import com.example.ProjectV1.model.Segment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SegmentRepositry extends MongoRepository<Segment,String> {
    @Query("{'from_Record_Id': ?0}")
    List<Segment> findByRecord(String Record_id);

}
