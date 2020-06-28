package com.chancellor.degreemap.database;

import androidx.lifecycle.LiveData;

import com.chancellor.degreemap.models.Assessment;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.models.Mentor;
import com.chancellor.degreemap.models.Term;

import java.util.List;

import io.reactivex.Completable;

public interface iDegreeMapRepository {
    //Terms CRUD
    Completable createTerm(Term term);

    Completable createAllTerms(Term... terms);

    Completable updateTerm(Term term);

    Completable deleteTerm(Term term);

    LiveData<List<Term>> getTermList();

    LiveData<Term> getTermById(int termId);

    //Courses CRUD
    Completable createCourse(Course course);

    Completable createAllCourses(Course... courses);

    Completable updateCourse(Course course);

    Completable deleteCourse(Course course);

    LiveData<List<Course>> getCourseList();

    LiveData<Course> getCourseById(int courseId);

    LiveData<List<Course>> getCoursesByTermId(int termId);

    //Assessment CRUD
    Completable createAssessment(Assessment assessment);

    Completable createAllAssessments(Assessment... assessments);

    Completable updateAssessment(Assessment assessment);

    Completable deleteAssessment(Assessment assessment);

    LiveData<List<Assessment>> getAssessmentList();

    LiveData<Assessment> getAssessmentById(int assessmentId);

    LiveData<List<Assessment>> getAssessmentsByCourseId(int courseId);

    //Mentor CRUD
    Completable createMentor(Mentor mentor);

    Completable createAllMentors(Mentor... mentors);

    Completable updateMentor(Mentor mentor);

    Completable deleteMentor(Mentor mentor);

    LiveData<List<Mentor>> getMentorList();

    LiveData<Mentor> getMentorById(int mentorId);

    LiveData<List<Mentor>> getMentorsByCourseId(int courseId);
}
