package com.example.ProjectV1.Controller;

import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.Service.MessageService;
import com.example.ProjectV1.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    @Autowired
    private MessageService messageService;
    @PostMapping("/message/save")
    public ResponseEntity<?> saveMessage( @RequestBody Message message) throws ObjectCollectionException {
        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/message/save").toUriString());
        return ResponseEntity.created(uri).body(messageService.saveMessage(message));
    }
    @GetMapping("/message/id/{id}")
    public ResponseEntity<?> getMessagebyId(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(messageService.getMessageById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/message/sender/{sender}")
    public ResponseEntity<?> getMessagebySender(@PathVariable("sender") String sender) {
        try {
            return new ResponseEntity<>(messageService.getMessagesBySender(sender), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/message/reciever/{reciever}")
    public ResponseEntity<?> getMessagebyReciever(@PathVariable("reciever") String reciever) {
        try {
            return new ResponseEntity<>(messageService.getMessageByReciever(reciever), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages() {
        return new ResponseEntity<>(messageService.getAllMessages(),HttpStatus.OK);
    }
    @DeleteMapping("/message/delete/{id}")
    public ResponseEntity<?> DeleteMessage(@PathVariable("id") String id){
        if (id.isBlank() || id.isEmpty()) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        try {
            messageService.deleteMessage(id);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } catch (ObjectCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
