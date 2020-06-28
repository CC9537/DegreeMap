package com.chancellor.degreemap.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.chancellor.degreemap.utilities.DateTypeConverter;

import java.sql.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "courses", indices = {@Index("term_id_fk")}, foreignKeys = @ForeignKey(
        entity = Term.class,
        parentColumns = "term_id",
        childColumns = "term_id_fk",
        onDelete = CASCADE,
        onUpdate = CASCADE
))
public class Course {
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
    @ColumnInfo(name = "course_alert_date")
    @TypeConverters(DateTypeConverter.class)
    private Date courseAlertDate;

    @ColumnInfo(name = "term_id_fk")
    private int termIdFk;

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

    public Date getCourseAlertDate() {
        return courseAlertDate;
    }

    public void setCourseAlertDate(Date courseAlertDate) {
        this.courseAlertDate = courseAlertDate;
    }

    public int getTermIdFk() {
        return termIdFk;
    }

    public void setTermIdFk(int termIdFk) {
        this.termIdFk = termIdFk;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", courseStart=" + courseStart +
                ", courseEnd=" + courseEnd +
                ", courseStatus='" + courseStatus + '\'' +
                ", courseNotes='" + courseNotes + '\'' +
                ", termId=" + termIdFk +
                '}';
    }
}
