package com.example.ProjectV1.Service;

import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.Repositry.AnalysisDataRepositry;
import com.example.ProjectV1.model.AnalysisData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service

public class AnalysisDataServiceImpl implements AnalysisDataService {
    @Autowired
    private AnalysisDataRepositry analysisDataRepositry;
    @Override
    public AnalysisData saveData(AnalysisData data) {
        data.setAnalyzed_at(new Date(System.currentTimeMillis()));
        return analysisDataRepositry.save(data);

    }

    @Override
    public void deleteData(String id) throws ObjectCollectionException {
        Optional<AnalysisData> data=analysisDataRepositry.findById(id);
        if (data.isPresent()){
            analysisDataRepositry.delete(data.get());}
        else {
           throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
        }

    }

    @Override
    public AnalysisData updateData(String id,AnalysisData info) throws ObjectCollectionException {
        Optional<AnalysisData> data=analysisDataRepositry.findById(id);
        if (data.isPresent()){
            data.get().setAnalyzed_by(info.getAnalyzed_by());
            data.get().setUpdated_At(new Date(System.currentTimeMillis()));
            //data.get().setDiagnostic(info.getDiagnostic());

        }
        else{
            throw new ObjectCollectionException(ObjectCollectionException.NotFoundException(id));
        }
        return analysisDataRepositry.save(data.get());
    }
}
