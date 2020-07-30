package com.chancellor.degreemap.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chancellor.degreemap.database.DegreeMapRepository;
import com.chancellor.degreemap.models.Course;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private DegreeMapRepository degreeMapRepository;
    private LiveData<List<Course>> courseList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        degreeMapRepository = new DegreeMapRepository(application);
        courseList = degreeMapRepository.getCourseList();
    }

    public LiveData<List<Course>> getCourseList() {
        return courseList;
    }

    public int countCourses() {
        return degreeMapRepository.countCourses();
    }

    public int countCoursesPending() {
        return degreeMapRepository.countCoursesPending();
    }

    public int countCoursesInProgress() {
        return degreeMapRepository.countCoursesInProgress();
    }

    public int countCoursesComplete() {
        return degreeMapRepository.countCoursesComplete();
    }
}
