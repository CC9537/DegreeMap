package com.chancellor.degreemap.database;

import androidx.lifecycle.LiveData;

import com.chancellor.degreemap.models.Assessment;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.models.Mentor;
import com.chancellor.degreemap.models.Term;

import java.util.List;


public interface iDegreeMapRepository {
    //Terms CRUD
    void createTerm(Term term);

    void createAllTerms(Term... terms);

    void updateTerm(Term term);

    void deleteTerm(Term term);

    LiveData<List<Term>> getTermList();

    LiveData<Term> getTermById(int termId);

    //Courses CRUD
    void createCourse(Course course);

    void createAllCourses(Course... courses);

    void updateCourse(Course course);

    void deleteCourse(Course course);

    LiveData<List<Course>> getCourseList();

    LiveData<Course> getCourseById(int courseId);

    LiveData<List<Course>> getCoursesByTermId(int termId);

    //LiveData<Mentor> getMentorByCourseId(int courseId);

    //Assessment CRUD
    void createAssessment(Assessment assessment);

    void createAllAssessments(Assessment... assessments);

    void updateAssessment(Assessment assessment);

    void deleteAssessment(Assessment assessment);

    LiveData<List<Assessment>> getAssessmentList();

    LiveData<Assessment> getAssessmentById(int assessmentId);

    LiveData<List<Assessment>> getAssessmentsByCourseId(int courseId);

    //Mentor CRUD
    void createMentor(Mentor mentor);

    void createAllMentors(Mentor... mentors);

    void updateMentor(Mentor mentor);

    void deleteMentor(Mentor mentor);

    LiveData<List<Mentor>> getMentorList();

    LiveData<Mentor> getMentorById(int mentorId);

    //LiveData<List<Mentor>> getMentorsByCourseId(int courseId);
}
