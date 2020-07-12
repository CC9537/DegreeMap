package com.chancellor.degreemap.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chancellor.degreemap.database.DegreeMapRepository;
import com.chancellor.degreemap.models.Mentor;

import java.util.List;

public class MentorViewModel extends AndroidViewModel {
    private DegreeMapRepository degreeMapRepository;

    private LiveData<List<Mentor>> mentorList;

    public MentorViewModel(@NonNull Application application) {
        super(application);
        degreeMapRepository = new DegreeMapRepository(application);
        mentorList = degreeMapRepository.getMentorList();
    }

    public LiveData<List<Mentor>> getMentorList() {
        return mentorList;
    }

//    public LiveData<Mentor> getMentorForCourse(int courseId) {
//        return degreeMapRepository.getMentorByCourseId(courseId);
//    }

    public void insertMentor(Mentor mentor) {
        degreeMapRepository.createMentor(mentor);
    }

    public void updateMentor(Mentor mentor) {
        degreeMapRepository.updateMentor(mentor);
    }

    public void deleteMentor(Mentor mentor) {
        degreeMapRepository.deleteMentor(mentor);
    }
}
