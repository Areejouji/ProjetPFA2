package com.example.ProjectV1.Service;

import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.model.AnalysisData;

public interface AnalysisDataService {

    public AnalysisData saveData(AnalysisData data);
    public void deleteData(String id) throws ObjectCollectionException;
    public AnalysisData updateData(String id ,AnalysisData info ) throws ObjectCollectionException;
}
