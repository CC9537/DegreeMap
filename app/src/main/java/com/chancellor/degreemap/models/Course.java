package com.chancellor.degreemap.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.chancellor.degreemap.utilities.DateTypeConverter;

import java.io.Serializable;
import java.sql.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "courses", indices = {@Index("term_id_fk")}, foreignKeys = @ForeignKey(
        entity = Term.class,
        parentColumns = "term_id",
        childColumns = "term_id_fk",
        onDelete = CASCADE,
        onUpdate = CASCADE
))
public class Course implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    private int courseId;

    @ColumnInfo(name = "course_name")
    private String courseName;
    @ColumnInfo(name = "course_start")
    @TypeConverters(DateTypeConverter.class)
    private Date courseStart;
    @ColumnInfo(name = "course_end")
    @TypeConverters(DateTypeConverter.class)
    private Date courseEnd;
    @ColumnInfo(name = "course_status")
    private String courseStatus;
    @ColumnInfo(name = "course_notes")
    private String courseNotes;
    @ColumnInfo(name = "term_id_fk")
    private int termIdFk;
    @Embedded
    private Mentor mentor;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(Date courseStart) {
        this.courseStart = courseStart;
    }

    public Date getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(Date courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public int getTermIdFk() {
        return termIdFk;
    }

    public void setTermIdFk(int termIdFk) {
        this.termIdFk = termIdFk;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentorIdFk) {
        this.mentor = mentorIdFk;
    }

    @Override
    public String toString() {
        return courseName;
    }
}
