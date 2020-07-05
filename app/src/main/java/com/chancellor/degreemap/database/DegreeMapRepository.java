package com.chancellor.degreemap.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.chancellor.degreemap.models.Assessment;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.models.Mentor;
import com.chancellor.degreemap.models.Term;

import java.util.List;


public class DegreeMapRepository implements iDegreeMapRepository {

    private DegreeMapDatabase db;

    public DegreeMapRepository(Application application) {
        this.db = DegreeMapDatabase.getDatabase(application);
    }

    @Override
    public void createTerm(Term term) {
        db.databaseWriteExecutor.execute(() -> {
            db.termDao().createTerm(term);
        });
    }

    @Override
    public void createAllTerms(Term... terms) {
        db.databaseWriteExecutor.execute(() -> db.termDao().createAllTerms(terms));
    }

    @Override
    public void updateTerm(Term term) {
        db.databaseWriteExecutor.execute(() -> db.termDao().updateTerm(term));
    }

    @Override
    public void deleteTerm(Term term) {
        db.databaseWriteExecutor.execute(() -> db.termDao().deleteTerm(term));
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
    public void createCourse(Course course) {
        db.databaseWriteExecutor.execute(() -> db.courseDao().createCourse(course));
    }

    @Override
    public void createAllCourses(Course... courses) {
        db.databaseWriteExecutor.execute(() -> db.courseDao().createAllCourses(courses));
    }

    @Override
    public void updateCourse(Course course) {
        db.databaseWriteExecutor.execute(() -> db.courseDao().updateCourse(course));
    }

    @Override
    public void deleteCourse(Course course) {
        db.databaseWriteExecutor.execute(() -> db.courseDao().deleteCourse(course));
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
    public void createAssessment(Assessment assessment) {
        db.databaseWriteExecutor.execute(() -> db.assessmentDao().createAssessment(assessment));
    }

    @Override
    public void createAllAssessments(Assessment... assessments) {
        db.databaseWriteExecutor.execute(() -> db.assessmentDao().createAllAssessments(assessments));
    }

    @Override
    public void updateAssessment(Assessment assessment) {
        db.databaseWriteExecutor.execute(() -> db.assessmentDao().updateAssessment(assessment));
    }

    @Override
    public void deleteAssessment(Assessment assessment) {
        db.databaseWriteExecutor.execute(() -> db.assessmentDao().deleteAssessment(assessment));
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
    public void createMentor(Mentor mentor) {
        db.databaseWriteExecutor.execute(() -> db.mentorDao().createMentor(mentor));
    }

    @Override
    public void createAllMentors(Mentor... mentors) {
        db.databaseWriteExecutor.execute(() -> db.mentorDao().createAllMentors(mentors));
    }

    @Override
    public void updateMentor(Mentor mentor) {
        db.databaseWriteExecutor.execute(() -> db.mentorDao().updateMentor(mentor));
    }

    @Override
    public void deleteMentor(Mentor mentor) {
        db.databaseWriteExecutor.execute(() -> db.mentorDao().deleteMentor(mentor));
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
