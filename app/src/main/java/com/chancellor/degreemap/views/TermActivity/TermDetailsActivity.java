package com.chancellor.degreemap.views.TermActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.models.Term;
import com.chancellor.degreemap.viewadapters.CourseListAdapter;
import com.chancellor.degreemap.viewadapters.TermListAdapter;
import com.chancellor.degreemap.viewmodels.CourseViewModel;
import com.chancellor.degreemap.viewmodels.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    private static final int COURSE_ADD_ACTIVITY_REQUEST_CODE = 1;
    private static final int TERM_EDIT_ACTIVITY_REQUEST_CODE = 2;
    private static final String TAG = "TermDetailsActivity";
    TermViewModel termViewModel;
    Term term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set the term details to term received from intent.
        term = (Term) getIntent().getSerializableExtra("Term");

        EditText termName = findViewById(R.id.termDetails_TermName);
        EditText termStartDate = findViewById(R.id.termDetails_TermStartDate);
        EditText termEndDate = findViewById(R.id.termDetails_TermEndDate);

        termName.setText(term.getTermName());
        termStartDate.setText(term.getTermStart().toString());
        termEndDate.setText(term.getTermEnd().toString());

        final TermListAdapter termListAdapter = new TermListAdapter(this);
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getTermList().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                termListAdapter.setTerms(terms);
            }
        });

        RecyclerView termDetailsCourseRecyclerView = findViewById(R.id.termDetails_CourseRecyclerView);
        final CourseListAdapter courseListAdapter = new CourseListAdapter(this);
        termDetailsCourseRecyclerView.setAdapter(courseListAdapter);
        termDetailsCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Add the ViewModel
        CourseViewModel courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCoursesByTermId(term.getTermId())
                .observe(this, new Observer<List<Course>>() {
                    @Override
                    public void onChanged(List<Course> courses) {
                        courseListAdapter.setCourses(courses);
                    }
                });

        // Uncomment after adding CourseAddActivity
//        ExtendedFloatingActionButton addCourseFAB = findViewById(R.id.termDetails_AddCourseFAB);
//        addCourseFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent addTermIntent = new Intent(getApplicationContext(), CourseAddActivity.class);
//                startActivityForResult(addTermIntent, COURSE_ADD_ACTIVITY_REQUEST_CODE);
//            }
//        });

        FloatingActionButton editTermFAB = findViewById(R.id.termDetails_EditTerm);
        editTermFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editTermIntent = new Intent(getApplicationContext(), TermEditActivity.class);
                editTermIntent.putExtra("Term", term);
                startActivityForResult(editTermIntent, TERM_EDIT_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TERM_EDIT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Term termToEdit = (Term) data.getSerializableExtra("Term");
            Log.d(TAG, "onActivityResult: " + termToEdit.toString());
            termViewModel.updateTerm(termToEdit);
            Intent returnTo = new Intent(getApplicationContext(), TermActivity.class);
            startActivity(returnTo);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent termActivityIntent = new Intent(getApplicationContext(), TermActivity.class);
        startActivity(termActivityIntent);
        super.onBackPressed();
        return true;
    }
}