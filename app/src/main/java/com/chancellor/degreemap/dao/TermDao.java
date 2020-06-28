package com.chancellor.degreemap.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.chancellor.degreemap.models.Term;

import java.util.List;

@Dao
public interface TermDao {
    @Query("SELECT * FROM terms ORDER BY term_id ASC")
    LiveData<List<Term>> getTermList();

    @Query("SELECT * FROM terms WHERE term_id = :termId")
    LiveData<Term> getTermById(int termId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createTerm(Term term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createAllTerms(Term... terms);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTerm(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("DELETE FROM terms")
    void deleteAllTerms();
}
