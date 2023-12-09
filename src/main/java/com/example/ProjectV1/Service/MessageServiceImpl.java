package com.example.ProjectV1.Service;

import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.Repositry.MessageRepositry;
import com.example.ProjectV1.Repositry.UserRepositry;
import com.example.ProjectV1.model.Message;
import com.example.ProjectV1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
     private MessageRepositry messageRepositry;
    @Autowired
    private UserRepositry userRepositry;
    @Override
    public Message saveMessage(Message message) throws ObjectCollectionException {
        User sender=userRepositry.findByUsername(message.getSender_username());
        User reciever=userRepositry.findByUsername(message.getReciever_username());
        if(sender!=null&&reciever!=null)
        {  if(!sender.equals(reciever)){
              messageRepositry.save(message);}
            else {
             throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(reciever.getUsername()));}
        }
        return message;
    }

    @Override
    public Message getMessageById(String id) throws ObjectCollectionException {
        Optional<Message> message=messageRepositry.findById(id);
        if(message.isPresent()){
            return message.get();
        }
        else
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages=messageRepositry.findAll();
        if(messages!=null)
            return messages;
        else
            return new ArrayList<>();
    }


    @Override
    public void deleteMessage(String id) throws ObjectCollectionException {
        Optional<Message> message=messageRepositry.findById(id);
        if(message.isPresent())
            messageRepositry.deleteById(id);
        else
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
    }

    @Override
    public List<Message> getMessagesBySender(String id) {
        List<Message> messages=messageRepositry.findBySender_Id(id);
        if(messages!=null)
            return messages;
        else
            return new ArrayList<>();
    }

    @Override
    public List<Message> getMessageByReciever(String id) {
        List<Message> messages=messageRepositry.findByReciever_Id(id);
        if(messages!=null)
            return messages;
        else
            return new ArrayList<>();

    }
}
