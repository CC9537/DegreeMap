package com.chancellor.degreemap.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chancellor.degreemap.database.DegreeMapRepository;
import com.chancellor.degreemap.models.Term;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private DegreeMapRepository degreeMapRepository;

    private LiveData<List<Term>> termList;

    public TermViewModel(@NonNull Application application) {
        super(application);
        degreeMapRepository = new DegreeMapRepository(application);
        termList = degreeMapRepository.getTermList();
    }

    public LiveData<List<Term>> getTermList() {
        return termList;
    }

    public void insertTerm(Term term) {
        degreeMapRepository.createTerm(term);
    }

    public void updateTerm(Term term) {
        degreeMapRepository.updateTerm(term);
    }

    public void deleteTerm(Term term) {
        degreeMapRepository.deleteTerm(term);
    }
}
