package com.example.ProjectV1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Document("Messages")
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @MongoId
    private String message_Id;
    @NotNull(message="the message topic must not be null")
    @NotEmpty(message="the message topic  must not be empty")
    private String topic;
    @NotNull(message="the message content must not be null")
    @NotEmpty(message="the message content must not be empty")
    private String content;
    @NotNull(message="the message sender must not be null")
    @NotEmpty(message="the message sender must not be empty")
    private String sender_username;
    @NotNull(message="message reciever must not be null")
    @NotEmpty(message="the message reciever must not be empty")
    private String reciever_username;


}
