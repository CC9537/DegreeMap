package com.chancellor.degreemap.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chancellor.degreemap.database.DegreeMapRepository;
import com.chancellor.degreemap.models.Course;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private DegreeMapRepository degreeMapRepository;
    private LiveData<List<Course>> courseList;


    public CourseViewModel(@NonNull Application application) {
        super(application);
        degreeMapRepository = new DegreeMapRepository(application);
        courseList = degreeMapRepository.getCourseList();
    }

    public LiveData<List<Course>> getCourseList() {
        return courseList;
    }

    public LiveData<List<Course>> getCoursesByTermId(int termId) {
        return degreeMapRepository.getCoursesByTermId(termId);
    }

    public void insertCourse(Course course) {
        degreeMapRepository.createCourse(course);
    }

    public void updateCourse(Course course) {
        degreeMapRepository.updateCourse(course);
    }

    public void deleteCourse(Course course) {
        degreeMapRepository.deleteCourse(course);
    }
}
