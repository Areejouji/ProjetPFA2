package com.example.ProjectV1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.Collection;

@Document(value="Segments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Segment {
    @Id
    @MongoId
    private String segment_Id;
    private ArrayList<Double> annotations;
    private ArrayList<String> segment_Data=new ArrayList<>();//data_id
    private String from_Record_Id;

public Segment(ArrayList<Double>annotations,String from_Record_Id){
    this.annotations=annotations;
    this.from_Record_Id=from_Record_Id;

}

}
