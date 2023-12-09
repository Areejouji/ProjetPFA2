package com.example.ProjectV1.Repositry;

import com.example.ProjectV1.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepositry extends MongoRepository<Message,String> {
    @Query("{'sender_username': ?0}")
    public List<Message> findBySender_Id(String id);
    @Query("{'reciever_username': ?0}")
    public List<Message> findByReciever_Id(String id);
    @Query("{'status': ?0}")
    public List<Message> findByStatus(String stat);

}
