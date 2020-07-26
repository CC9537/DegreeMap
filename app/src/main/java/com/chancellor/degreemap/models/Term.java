package com.chancellor.degreemap.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.chancellor.degreemap.utilities.DateTypeConverter;

import java.io.Serializable;
import java.sql.Date;

@Entity(tableName = "terms", indices = {@Index("term_id")})
public class Term implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "term_id")
    private int termId;

    @ColumnInfo(name = "term_name")
    private String termName;
    @ColumnInfo(name = "term_start")
    @TypeConverters(DateTypeConverter.class)
    private Date termStart;
    @ColumnInfo(name = "term_end")
    @TypeConverters(DateTypeConverter.class)
    private Date termEnd;

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Date getTermStart() {
        return termStart;
    }

    public void setTermStart(Date termStart) {
        this.termStart = termStart;
    }

    public Date getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(Date termEnd) {
        this.termEnd = termEnd;
    }

    @Override
    public String toString() {
        return termName;
    }
}
