package com.example.ProjectV1.Service;

import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.Repositry.AnalysisDataRepositry;
import com.example.ProjectV1.Repositry.DoctorRepositry;
import com.example.ProjectV1.Repositry.RecordingRepositry;
import com.example.ProjectV1.Repositry.SegmentRepositry;
import com.example.ProjectV1.model.AnalysisData;
import com.example.ProjectV1.model.Doctor;
import com.example.ProjectV1.model.Recording;
import com.example.ProjectV1.model.Segment;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordingServiceImpl implements  RecordingService{

    @Autowired
    private DoctorRepositry doctorRepositry;
    @Autowired
    private SegmentRepositry segmentRepositry;
    @Autowired
    private RecordingRepositry recordingRepositry;
    @Autowired
    private AnalysisDataService analysisDataService;
    @Autowired
    private AnalysisDataRepositry analysisDataRepositry;
    @Autowired
    private DoctorService doctorService;
    @Override
    public Recording saveRecording(String Filename) throws ObjectCollectionException {
            String csvPath="C:/Users/LENOVO/Desktop/PFA Files/"+Filename+".csv";
            ArrayList<Double> firstColumn=new ArrayList<>();
            ArrayList<ArrayList<Double>> dividedSecondColumn=new ArrayList<>();
            ArrayList<String> secondline =null;
            ArrayList<Double> secondColumn=new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))){
            String[] nextLine;
            boolean firstLine=true;
            String[] header=null;
            //ArrayList<String> secondline =null;
            //ArrayList<Double> secondColumn=new ArrayList<>();
            int Chunksize=1250;
            int lineNumber=1;
            int j=1;
            while (( nextLine = reader.readNext())!=null){
                if(firstLine){
                    header=nextLine;
                    firstLine=false;
                } else{
                    if(lineNumber==2){
                        secondline=new ArrayList<>(Arrays.asList(nextLine));

                    } else{
                        firstColumn.add(Double.parseDouble(nextLine[0]));
                         j++;
                        log.info(String.valueOf(j));
                        if(!(Strings.isEmpty(nextLine[1]))){
                             secondColumn.add(Double.parseDouble(nextLine[1]));
                    }}


                }
                lineNumber++;
            }
            if (firstColumn.size()>0){
                for(int i=0;i<firstColumn.size();i+=Chunksize){
                    dividedSecondColumn.add(new ArrayList<>(firstColumn.subList(i,Math.min(i+Chunksize,firstColumn.size()))));
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        Recording Record=new Recording(secondline.get(0),secondColumn);
        Record.setAdded_At(new Date(System.currentTimeMillis()));
        int i=0;
        while(i<dividedSecondColumn.size()){
        Segment segment=new Segment(dividedSecondColumn.get(i),Record.getRecord_Id());
        segmentRepositry.save(segment);
        Record.getSegments().add(segment.getSegment_Id());
        i++;




    }   Record.setStatus("not analyzed");
        Record.setConcerned_Doctor(doctorService.getAllDoctorId());
        recordingRepositry.save(Record);
     return Record;}

    @Override
    public Recording getRecording(String id) throws ObjectCollectionException {

        Optional<Recording> record=recordingRepositry.findById(id);
        if(record.isPresent()){
            return record.get();
        }
        else{
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
        }
    }

    @Override
    public List<Recording> getAllRecord() {

      List<Recording> records=recordingRepositry.findAll();
      if(records!=null){
          return records;
      }
      else return new ArrayList<>();
    }

    @Override
    public Segment analyzesegment(String segment_id, AnalysisData Data) throws ObjectCollectionException {
             Optional<Segment> AnalyzedSegment=segmentRepositry.findById(segment_id);
             if(AnalyzedSegment.isPresent()){
                 Data.setForSegment(segment_id);
                 analysisDataService.saveData(Data);
                 AnalyzedSegment.get().getSegment_Data().add(Data.getData_id());
                  segmentRepositry.save(AnalyzedSegment.get());
                 }
             Optional<Recording> record=recordingRepositry.findById(AnalyzedSegment.get().getFrom_Record_Id());
             //record.get().setStatus(getRecordingStatus(record.get().getRecord_Id()));
             return AnalyzedSegment.get();
    }

    @Override
    public List<Segment> getSegmentsPerRecord(String id) {
        List<Segment> segments = segmentRepositry.findByRecord(id);
        if (segments != null) {
            return segments;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Segment ReanalyzeSegment(String segment_id,AnalysisData Data) throws ObjectCollectionException {
        Optional<Segment> segment=segmentRepositry.findById(segment_id);
        Optional<AnalysisData> info1 =null;
        if(segment.isPresent())
        {   ArrayList<String> oldData = segment.get().getSegment_Data();
            if (!oldData.isEmpty() || oldData != null) {
                for (String info : oldData){
                    info1=analysisDataRepositry.findById(info);
                    if (info1.get().getAnalyzed_by().equals(Data.getAnalyzed_by())) {
                        oldData.remove(info1.get().getData_id());
                        oldData.add(Data.getData_id());
                    }
                    break;
        }}}
        else {
                throw  new ObjectCollectionException(ObjectCollectionException.NotFoundException(segment_id));
        }
        return segmentRepositry.save(segment.get());
    }


    @Override
    public void deleteRecord(String id) throws ObjectCollectionException {
        Optional<Recording> record=recordingRepositry.findById(id);
        if(record.isPresent())
        {
            recordingRepositry.deleteById(id);
        }
        else {
            throw  new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
        }

    }

    @Override
    public Doctor assignRecordingtoDoctor(String Record_id, String Doctor_Id) {
          Optional<Recording> record=recordingRepositry.findById(Record_id);
          Optional<Doctor> doctor= Optional.ofNullable(doctorRepositry.findDoctorByuserId(Doctor_Id));
          if(record.isPresent()){
             if(doctor.isPresent()) {
               record.get().getConcerned_Doctor().add(doctor.get().getDoctor_Id());
               recordingRepositry.save(record.get());
          }}
          return doctor.get();
    }

    @Override
    public ArrayList<String> getAllRecordingIds() {
        List<Recording> recordings=recordingRepositry.findAll();
        ArrayList ids=new ArrayList<>();
        if(ids!=null)
        {
            for(Recording id:recordings){
                ids.add(id.getRecord_Id());
            }
        }
        return ids;

    }

    @Override
    public Segment getSegment(String seg_id) {
      return segmentRepositry.findById(seg_id).get();
    }

    /*@Override
    public String getRecordingStatus(String id) throws ObjectCollectionException {
        Optional<Recording> recordingOptional=recordingRepositry.findById(id);
        Integer analyzed=0;
        Integer notAnalyzed=0;
        if(!recordingOptional.isPresent()){
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
        }
        else {
            Recording recording=recordingOptional.get();
            List<String> segments=recording.getSegments();
            for(String seg:segments){
                if(seg.getSegment_Data().isEmpty())
                {
                    notAnalyzed++;
                }
                else
                {
                    analyzed++;
                }

            }
            if(notAnalyzed==segments.size())
            {
                return "Not Analyzed";
            }
            else if(analyzed==segments.size())
            {
                return "Analyzed";
            }
            else {
                return "Analyzing";
            }
        }

    }*/
}
