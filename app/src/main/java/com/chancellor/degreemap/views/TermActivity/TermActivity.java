package com.chancellor.degreemap.views.TermActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Term;
import com.chancellor.degreemap.utilities.DateTypeConverter;
import com.chancellor.degreemap.viewadapters.TermListAdapter;
import com.chancellor.degreemap.viewmodels.TermViewModel;
import com.chancellor.degreemap.views.MainActivity.MainActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class TermActivity extends AppCompatActivity {
    private static final int TERM_ADD_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "TermActivity";
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
        Toolbar toolbar = findViewById(R.id.mainToolbar);
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
        termViewModel.getTermList().observe(this, terms -> {
            Log.d(TAG, "onChanged: Called " + terms.toString());
            termListAdapter.setTerms(terms);
        });

        ExtendedFloatingActionButton termAddTermFAB = findViewById(R.id.term_addTermFAB);
        termAddTermFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTermIntent = new Intent(getApplicationContext(), TermAddActivity.class);
                startActivityForResult(addTermIntent, TERM_ADD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + " : " + data);

        if (requestCode == TERM_ADD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: In return from addTermActivity");
            Term newTerm = new Term();
            newTerm.setTermName(data.getStringExtra("termName"));
            newTerm.setTermStart(DateTypeConverter.toDate(data.getStringExtra("termStart")));
            newTerm.setTermEnd(DateTypeConverter.toDate(data.getStringExtra("termEnd")));
            termViewModel.insertTerm(newTerm);
        }

    }

}