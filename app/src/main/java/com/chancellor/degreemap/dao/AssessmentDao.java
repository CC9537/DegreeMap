package com.chancellor.degreemap.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.chancellor.degreemap.models.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao {
    @Query("SELECT * FROM assessments ORDER BY assessment_id ASC")
    LiveData<List<Assessment>> getAssessmentList();

    @Query("SELECT * FROM assessments WHERE assessment_id = :assessmentId")
    LiveData<Assessment> getAssessmentById(int assessmentId);

    @Query("SELECT * FROM assessments WHERE course_id_fk = :courseId")
    LiveData<List<Assessment>> getAssessmentsByCourseId(int courseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createAssessment(Assessment assessment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createAllAssessments(Assessment... assessments);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);
}
