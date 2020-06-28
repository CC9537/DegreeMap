package com.chancellor.degreemap.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.chancellor.degreemap.models.Assessment;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.models.Mentor;
import com.chancellor.degreemap.models.Term;

import java.util.List;

import io.reactivex.Completable;

public class DegreeMapRepository implements iDegreeMapRepository {

    private DegreeMapDatabase db;

    public DegreeMapRepository(Application application) {
        this.db = DegreeMapDatabase.getDatabase(application);
    }

    @Override
    public Completable createTerm(Term term) {
        return Completable.fromAction(() -> db.termDao().createTerm(term));
    }

    @Override
    public Completable createAllTerms(Term... terms) {
        return Completable.fromAction(() -> db.termDao().createAllTerms(terms));
    }

    @Override
    public Completable updateTerm(Term term) {
        return Completable.fromAction(() -> db.termDao().updateTerm(term));
    }

    @Override
    public Completable deleteTerm(Term term) {
        return Completable.fromAction(() -> db.termDao().deleteTerm(term));
    }

    @Override
    public LiveData<List<Term>> getTermList() {
        return db.termDao().getTermList();
    }

    @Override
    public LiveData<Term> getTermById(int termId) {
        return db.termDao().getTermById(termId);
    }

    @Override
    public Completable createCourse(Course course) {
        return Completable.fromAction(() -> db.courseDao().createCourse(course));
    }

    @Override
    public Completable createAllCourses(Course... courses) {
        return Completable.fromAction(() -> db.courseDao().createAllCourses(courses));
    }

    @Override
    public Completable updateCourse(Course course) {
        return Completable.fromAction(() -> db.courseDao().updateCourse(course));
    }

    @Override
    public Completable deleteCourse(Course course) {
        return Completable.fromAction(() -> db.courseDao().deleteCourse(course));
    }

    @Override
    public LiveData<List<Course>> getCourseList() {
        return db.courseDao().getCourseList();
    }

    @Override
    public LiveData<Course> getCourseById(int courseId) {
        return db.courseDao().getCourseById(courseId);
    }

    @Override
    public LiveData<List<Course>> getCoursesByTermId(int termId) {
        return db.courseDao().getCoursesByTermId(termId);
    }

    @Override
    public Completable createAssessment(Assessment assessment) {
        return Completable.fromAction(() -> db.assessmentDao().createAssessment(assessment));
    }

    @Override
    public Completable createAllAssessments(Assessment... assessments) {
        return Completable.fromAction(() -> db.assessmentDao().createAllAssessments(assessments));
    }

    @Override
    public Completable updateAssessment(Assessment assessment) {
        return Completable.fromAction(() -> db.assessmentDao().updateAssessment(assessment));
    }

    @Override
    public Completable deleteAssessment(Assessment assessment) {
        return Completable.fromAction(() -> db.assessmentDao().deleteAssessment(assessment));
    }

    @Override
    public LiveData<List<Assessment>> getAssessmentList() {
        return db.assessmentDao().getAssessmentList();
    }

    @Override
    public LiveData<Assessment> getAssessmentById(int assessmentId) {
        return db.assessmentDao().getAssessmentById(assessmentId);
    }

    @Override
    public LiveData<List<Assessment>> getAssessmentsByCourseId(int courseId) {
        return db.assessmentDao().getAssessmentsByCourseId(courseId);
    }

    @Override
    public Completable createMentor(Mentor mentor) {
        return Completable.fromAction(() -> db.mentorDao().createMentor(mentor));
    }

    @Override
    public Completable createAllMentors(Mentor... mentors) {
        return Completable.fromAction(() -> db.mentorDao().createAllMentors(mentors));
    }

    @Override
    public Completable updateMentor(Mentor mentor) {
        return Completable.fromAction(() -> db.mentorDao().updateMentor(mentor));
    }

    @Override
    public Completable deleteMentor(Mentor mentor) {
        return Completable.fromAction(() -> db.mentorDao().deleteMentor(mentor));
    }

    @Override
    public LiveData<List<Mentor>> getMentorList() {
        return db.mentorDao().getMentorList();
    }

    @Override
    public LiveData<Mentor> getMentorById(int mentorId) {
        return db.mentorDao().getMentorById(mentorId);
    }

    @Override
    public LiveData<List<Mentor>> getMentorsByCourseId(int courseId) {
        return db.mentorDao().getMentorsByCourseId(courseId);
    }
}
