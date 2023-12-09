package com.example.ProjectV1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(value="Recordings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recording {
    @Id
    private String record_Id;
    private ArrayList<Double> raw_PPG ;
    private ArrayList<String> Segments = new ArrayList<>() ; //Segment_id
    private List<String> concerned_Doctor; //Doctor_Id
    private Date added_At;
    private String status;

    public Recording(String record_Id,ArrayList<Double> raw_PPG){
        this.record_Id=record_Id;
        this.raw_PPG=raw_PPG;
    }

}
