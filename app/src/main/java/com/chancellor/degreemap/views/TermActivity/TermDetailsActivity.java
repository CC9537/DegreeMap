package com.chancellor.degreemap.views.TermActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
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
import com.chancellor.degreemap.viewmodels.CourseViewModel;
import com.chancellor.degreemap.viewmodels.TermViewModel;
import com.chancellor.degreemap.views.CourseActivity.CourseAddActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    private static final int COURSE_ADD_ACTIVITY_REQUEST_CODE = 1;
    private static final int TERM_EDIT_ACTIVITY_REQUEST_CODE = 2;

    TermViewModel termViewModel;
    int numCoursesAssignedThisTerm = 0;
    Term term;
    private CourseViewModel courseViewModel;

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

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

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
                        numCoursesAssignedThisTerm = courseListAdapter.getItemCount();
                    }
                });

        ExtendedFloatingActionButton addCourseFAB = findViewById(R.id.termDetails_AddCourseFAB);
        addCourseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTermIntent = new Intent(getApplicationContext(), CourseAddActivity.class);
                addTermIntent.putExtra("Term", term);
                startActivityForResult(addTermIntent, COURSE_ADD_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TERM_EDIT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Term termToEdit = (Term) data.getSerializableExtra("Term");
            termViewModel.updateTerm(termToEdit);
            Intent returnTo = new Intent(getApplicationContext(), TermActivity.class);
            startActivity(returnTo);
        }

        if (requestCode == COURSE_ADD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Course newCourse = (Course) data.getSerializableExtra("Course");
            courseViewModel.insertCourse(newCourse);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.term_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.termDetails_EditTermMenu:
                Intent editTermIntent = new Intent(getApplicationContext(), TermEditActivity.class);
                editTermIntent.putExtra("Term", term);
                startActivityForResult(editTermIntent, TERM_EDIT_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.termDetails_DeleteTermMenu:
                if (numCoursesAssignedThisTerm > 0)
                    Snackbar.make(getWindow().getDecorView(), "Error! Can't delete term when courses exist.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    termViewModel.deleteTerm(term);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                            Intent returnTo = new Intent(getApplicationContext(), TermActivity.class);
                            startActivity(returnTo);
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(TermDetailsActivity.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent termActivityIntent = new Intent(getApplicationContext(), TermActivity.class);
        startActivity(termActivityIntent);
        super.onBackPressed();
        return true;
    }
}