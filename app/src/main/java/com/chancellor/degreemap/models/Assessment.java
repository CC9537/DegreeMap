package com.chancellor.degreemap.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.chancellor.degreemap.utilities.DateTypeConverter;

import java.io.Serializable;
import java.sql.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "assessments", indices = {@Index("course_id_fk")}, foreignKeys = @ForeignKey(
        entity = Course.class,
        parentColumns = "course_id",
        childColumns = "course_id_fk",
        onDelete = CASCADE,
        onUpdate = CASCADE
))
public class Assessment implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessment_id")
    private int assessmentId;
    @ColumnInfo(name = "assessment_name")
    private String assessmentName;
    @ColumnInfo(name = "assessment_due_date")
    @TypeConverters(DateTypeConverter.class)
    private Date assessmentDueDate;
    @ColumnInfo(name = "assessment_type")
    private String assessmentType;
    @ColumnInfo(name = "assessment_info")
    private String assessmentInfo;
    @ColumnInfo(name = "course_id_fk")
    private int courseIdFk;

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public Date getAssessmentDueDate() {
        return assessmentDueDate;
    }

    public void setAssessmentDueDate(Date assessmentDueDate) {
        this.assessmentDueDate = assessmentDueDate;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentInfo() {
        return assessmentInfo;
    }

    public void setAssessmentInfo(String assessmentInfo) {
        this.assessmentInfo = assessmentInfo;
    }

    public int getCourseIdFk() {
        return courseIdFk;
    }

    public void setCourseIdFk(int courseIdFk) {
        this.courseIdFk = courseIdFk;
    }
}
