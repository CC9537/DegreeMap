package com.chancellor.degreemap.views.TermActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Term;
import com.chancellor.degreemap.viewadapters.TermListAdapter;
import com.chancellor.degreemap.viewmodels.TermViewModel;
import com.chancellor.degreemap.views.MainActivity.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TermActivity extends AppCompatActivity {
    //Add the ViewModel
    private TermViewModel termViewModel;

    @Override
    public boolean onSupportNavigateUp() {
        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivityIntent);
        super.onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        Toolbar toolbar = findViewById(R.id.termToolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        RecyclerView termsRecyclerView = findViewById(R.id.term_recycler_view);
        final TermListAdapter termListAdapter = new TermListAdapter(this);
        termsRecyclerView.setAdapter(termListAdapter);
        termsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getTermList().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                termListAdapter.setTerms(terms);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
}