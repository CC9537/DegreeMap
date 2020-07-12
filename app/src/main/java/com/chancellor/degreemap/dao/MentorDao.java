package com.chancellor.degreemap.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.chancellor.degreemap.models.Mentor;

import java.util.List;

@Dao
public interface MentorDao {
    @Query("SELECT * FROM mentors ORDER BY mentor_name ASC")
    LiveData<List<Mentor>> getMentorList();

    @Query("SELECT * FROM mentors WHERE mentor_id = :mentorId")
    LiveData<Mentor> getMentorById(int mentorId);

//    @Query("SELECT * FROM mentors WHERE course_id_fk = :courseId")
//    LiveData<List<Mentor>> getMentorsByCourseId(int courseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createMentor(Mentor mentor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createAllMentors(Mentor... mentors);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMentor(Mentor mentor);

    @Delete
    void deleteMentor(Mentor mentor);
}
