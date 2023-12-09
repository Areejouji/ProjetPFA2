package com.example.ProjectV1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("AnalysisData")
public class AnalysisData {
        @Id
        @MongoId
        private String data_id;
        private String analyzed_by; // doctor_id
        private Date analyzed_at;
        private String annotation;
        private Date updated_At;
        private String forSegment;//segment_id
        AnalysisData(String annotation){
                this.annotation=annotation;
        }

}
