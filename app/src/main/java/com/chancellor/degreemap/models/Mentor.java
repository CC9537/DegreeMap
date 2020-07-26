package com.chancellor.degreemap.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "mentors")
public class Mentor implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mentor_id")
    private int mentorId;
    @ColumnInfo(name = "mentor_name")
    private String mentorName;
    @ColumnInfo(name = "mentor_email")
    private String mentorEmail;
    @ColumnInfo(name = "mentor_phone")
    private String mentorPhone;

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

    @Override
    public String toString() {
        return mentorName;
    }
}
