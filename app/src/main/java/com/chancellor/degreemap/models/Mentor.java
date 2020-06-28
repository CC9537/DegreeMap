package com.chancellor.degreemap.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "mentors", indices = {@Index("course_id_fk")}, foreignKeys = @ForeignKey(
        entity = Course.class,
        parentColumns = "course_id",
        childColumns = "course_id_fk",
        onDelete = CASCADE,
        onUpdate = CASCADE
))
public class Mentor {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mentor_id")
    private int mentorId;
    @ColumnInfo(name = "mentor_name")
    private String mentorName;
    @ColumnInfo(name = "mentor_email")
    private String mentorEmail;
    @ColumnInfo(name = "mentor_phone")
    private String mentorPhone;

    @ColumnInfo(name = "course_id_fk")
    private int courseIdFk;

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public int getCourseIdFk() {
        return courseIdFk;
    }

    public void setCourseIdFk(int courseIdFk) {
        this.courseIdFk = courseIdFk;
    }
}
