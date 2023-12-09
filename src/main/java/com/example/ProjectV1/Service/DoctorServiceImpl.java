package com.example.ProjectV1.Service;

import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.Repositry.DoctorRepositry;
import com.example.ProjectV1.Validator.ObjectValidator;
import com.example.ProjectV1.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.lang.String.join;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepositry DoctorRepo;
    @Autowired
    private  ObjectValidator<Doctor> doctorObjectValidator;
    @Autowired
    private RecordingService recordingService;

    @Override
    public Doctor saveDoctor(Doctor doctor)
    {   doctorObjectValidator.validate(doctor);
        doctor.setConcerned_records(recordingService.getAllRecordingIds());
        return DoctorRepo.save(doctor);
    }

    @Override
    public Optional<Doctor> getDoctor(String id) {
        return DoctorRepo.findById(id);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors=DoctorRepo.findAll();
        if (doctors!=null)
            return doctors;
        else
            return new ArrayList<>();
    }

    @Override
    public void updateDoctor(String id,Doctor doctor) throws ObjectCollectionException {

        Optional<Doctor> DoctorWithId = DoctorRepo.findById(id);
        if(DoctorWithId.isPresent())
        {

            Doctor UpdateDoctor = DoctorWithId.get();
            UpdateDoctor.setDoctor_firstname(doctor.getDoctor_firstname());
            UpdateDoctor.setDoctor_lastname(doctor.getDoctor_lastname());
            UpdateDoctor.setConcerned_records(new ArrayList<>());
            DoctorRepo.save(UpdateDoctor);

        } else
        {
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
        }


    }

    @Override
    public void deleteDoctor(String id) throws ObjectCollectionException {
        Optional<Doctor> DoctorOptional=DoctorRepo.findById(id);
        if(!DoctorOptional.isPresent()){
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
        } else {
            DoctorRepo.deleteById(id);

        }

    }

    @Override
    public ArrayList<String> getAllDoctorId() {
        List<Doctor> doctors=DoctorRepo.findAll();
        ArrayList<String> ids=new ArrayList<>();
        if (doctors!=null)
        {
            for(Doctor element:doctors)
            {
                ids.add(element.getDoctor_Id());
            }

    }
     return ids;
   } }
