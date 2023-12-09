package com.example.ProjectV1.Controller;

import ai.picovoice.leopard.*;
import ai.picovoice.leopard.LeopardException;
import ai.picovoice.leopard.LeopardTranscript;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController

public class SpeechToTextController {
    final String accessKey = "qVgfuUK03WmITXB3MKvW694v45L2bv/NZ/9ZoUbhVU2zM6WnjkPtxQ==";
    @PostMapping("/text/{filename}")
    ResponseEntity<?> Text(@PathVariable("filename") String AUDIO_PATH) {
        String AUDIO="C:/Users/LENOVO/Desktop/PFA Files/"+AUDIO_PATH+".mp3";
        try {
            Leopard leopard = new Leopard.Builder()
                    .setAccessKey("qVgfuUK03WmITXB3MKvW694v45L2bv/NZ/9ZoUbhVU2zM6WnjkPtxQ==").
                    setModelPath("C:/Users/LENOVO/Desktop/PFA Files/M1-leopard-v1.2.0-23-05-07--17-13-45.pv").
                            build();
            LeopardTranscript result = leopard.processFile(AUDIO);
            return new ResponseEntity<>(result.getTranscriptString(),HttpStatus.OK);
        } catch (LeopardException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }}