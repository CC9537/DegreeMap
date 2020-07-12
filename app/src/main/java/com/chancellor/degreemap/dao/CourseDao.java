package com.chancellor.degreemap.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.chancellor.degreemap.models.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM courses ORDER BY course_id")
    LiveData<List<Course>> getCourseList();

    @Query("SELECT * FROM courses WHERE course_id = :courseId")
    LiveData<Course> getCourseById(int courseId);

    @Query("SELECT * FROM courses WHERE term_id_fk = :termId")
    LiveData<List<Course>> getCoursesByTermId(int termId);

//    @Query("SELECT mentor_id_fk FROM courses WHERE course_id = :courseId LIMIT 1;")
//    int getMentorIdByCourseId(int courseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createCourse(Course course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createAllCourses(Course... courses);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);
}
