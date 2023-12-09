package com.example.ProjectV1.Service;

import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.model.Message;

import java.util.Collection;
import java.util.List;

public interface MessageService {
    public Message saveMessage(Message message) throws ObjectCollectionException;
    public Message getMessageById(String id) throws ObjectCollectionException;
    public List<Message> getAllMessages();
    public void deleteMessage(String id) throws ObjectCollectionException;
    public List<Message> getMessagesBySender(String id);
    public List<Message> getMessageByReciever(String id);
}
