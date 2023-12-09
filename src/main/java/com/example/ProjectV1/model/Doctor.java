package com.example.ProjectV1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(value="Doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {


    @MongoId
    @Id
    private String doctor_Id;
    @NotNull(message="the doctor's firstname must not be null")
    @NotEmpty(message="the doctor's firstname must not be empty")
    private String doctor_firstname;
    @NotNull(message="the doctor's lastname must not be null")
    @NotEmpty(message="the doctor's lastname must not be empty")
    private String doctor_lastname;
    @NotNull(message="the user's id must not be null")
    @NotEmpty(message="the user's id must not be empty")
    private String user_Id_Doctor;
    private ArrayList<String>concerned_records =new ArrayList<>(); //Records_id

}