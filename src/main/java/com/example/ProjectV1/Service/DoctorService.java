package com.example.ProjectV1.Service;

import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.model.Doctor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DoctorService {

    Doctor saveDoctor(Doctor doctor);
    Optional<Doctor> getDoctor(String id);
    List<Doctor> getAllDoctors();
    void updateDoctor(String id,Doctor doctor) throws ObjectCollectionException;
    void deleteDoctor(String id) throws ObjectCollectionException;
    ArrayList<String> getAllDoctorId();

}
