package com.example.ProjectV1.Controller;

import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.Service.RecordingService;
import com.example.ProjectV1.model.AnalysisData;
import com.example.ProjectV1.model.Recording;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class RecordingController {
    @Autowired
    RecordingService recordingService;
    @PostMapping("/recording/save/{filename}")
    public ResponseEntity<Recording> SaveRecording(@Valid @PathVariable("filename") String filename) throws ObjectCollectionException {   URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/recording/save").toUriString());
        return ResponseEntity.created(uri).body(recordingService.saveRecording(filename));
    }
    @GetMapping("/recordings/")
    public ResponseEntity<Collection<Recording>> GetAllRecordings(){
        return ResponseEntity.ok().body(recordingService.getAllRecord());
    }
    @GetMapping("/recording/{id}")
    public ResponseEntity<?> GetRecording(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(recordingService.getRecording(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/segperrecording/{id}")
    public ResponseEntity<?> GetSegmentsPerRecording(@PathVariable("id") String id)
    {
        try {
            return new ResponseEntity<>(recordingService.getSegmentsPerRecord(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/recording/segment/{id}")
    public ResponseEntity<?> GetSegment(@PathVariable("id") String id)
    {
        try {
            return new ResponseEntity<>(recordingService.getSegment(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/recording/analyze/{id}")
    public ResponseEntity<?> AnalyzeSegement(@PathVariable("id") String id, @RequestBody AnalysisData data)
    {
        try {
            return new ResponseEntity<>(recordingService.analyzesegment(id,data), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping ("/recording/reanalyze/{id}")
    public ResponseEntity<?> ReanalyzeSegment(@PathVariable("id") String id ,@RequestBody AnalysisData data)
    {
        try {
            return new ResponseEntity<>(recordingService.ReanalyzeSegment(id,data), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/recording/delete/{id}")
    public ResponseEntity<?> DeleteRecording(@PathVariable("id") String id)
    {    if (id.isBlank() || id.isEmpty()) {
        throw new IllegalArgumentException("The given id must not be null");
    }
        try {
            recordingService.deleteRecord(id);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } catch (ObjectCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    }



