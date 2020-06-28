package com.chancellor.degreemap.views.TermActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.viewadapters.CourseListAdapter;
import com.chancellor.degreemap.viewmodels.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    //Add the ViewModel
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        EditText termName = findViewById(R.id.termDetails_TermName);
        EditText termStartDate = findViewById(R.id.termDetails_TermStartDate);
        EditText termEndDate = findViewById(R.id.termDetails_TermEndDate);
        setEditableFalse(termName);
        setEditableFalse(termStartDate);
        setEditableFalse(termEndDate);

        termName.setText(getIntent().getStringExtra("termName"));
        termStartDate.setText(getIntent().getStringExtra("termStart"));
        termEndDate.setText(getIntent().getStringExtra("termEnd"));

        RecyclerView termDetailsCourseRecyclerView = findViewById(R.id.termDetails_CourseRecyclerView);
        final CourseListAdapter courseListAdapter = new CourseListAdapter(this);
        termDetailsCourseRecyclerView.setAdapter(courseListAdapter);
        termDetailsCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCoursesByTermId(getIntent().getIntExtra("termID", 0))
                .observe(this, new Observer<List<Course>>() {
                    @Override
                    public void onChanged(List<Course> courses) {
                        courseListAdapter.setCourses(courses);
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

    private void setEditableFalse(EditText editText) {
        editText.setFocusable(false);
        editText.setClickable(false);
        editText.setCursorVisible(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent termActivityIntent = new Intent(getApplicationContext(), TermActivity.class);
        startActivity(termActivityIntent);
        super.onBackPressed();
        return true;
    }
}