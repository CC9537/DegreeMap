package com.chancellor.degreemap.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chancellor.degreemap.database.DegreeMapRepository;
import com.chancellor.degreemap.models.Assessment;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private DegreeMapRepository degreeMapRepository;

    private LiveData<List<Assessment>> assessmentList;

    public AssessmentViewModel(@NonNull Application application, int courseId) {
        super(application);
        degreeMapRepository = new DegreeMapRepository(application);
        assessmentList = degreeMapRepository.getAssessmentsByCourseId(courseId);
    }

    public LiveData<List<Assessment>> getAssessmentList() {
        return assessmentList;
    }

    public void insertAssessment(Assessment assessment) {
        degreeMapRepository.createAssessment(assessment);
    }

    public void updateAssessment(Assessment assessment) {
        degreeMapRepository.updateAssessment(assessment);
    }

    public void deleteAssessment(Assessment assessment) {
        degreeMapRepository.deleteAssessment(assessment);
    }
}
