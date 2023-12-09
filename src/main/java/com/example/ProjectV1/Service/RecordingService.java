package com.example.ProjectV1.Service;

import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.model.AnalysisData;
import com.example.ProjectV1.model.Doctor;
import com.example.ProjectV1.model.Recording;
import com.example.ProjectV1.model.Segment;

import java.util.ArrayList;
import java.util.List;

public interface RecordingService {

    public Recording saveRecording(String FilePath) throws ObjectCollectionException;
    public Recording getRecording(String id) throws ObjectCollectionException;
    public List<Recording> getAllRecord();
    public Segment analyzesegment(String segment_id , AnalysisData Data) throws ObjectCollectionException;
    public List<Segment> getSegmentsPerRecord(String id);
    public Segment ReanalyzeSegment(String segment_id,AnalysisData data) throws ObjectCollectionException;
    public void deleteRecord(String id) throws ObjectCollectionException;
    public Doctor assignRecordingtoDoctor(String Record_id, String Doctor_Id);
    //public String getRecordingStatus(String id) throws ObjectCollectionException;
    ArrayList<String> getAllRecordingIds();

    Segment getSegment(String segment_id);

}
