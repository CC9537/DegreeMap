package com.chancellor.degreemap.views.AssessmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Assessment;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.viewadapters.CourseListAdapter;
import com.chancellor.degreemap.viewmodels.AssessmentViewModel;
import com.chancellor.degreemap.viewmodels.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

public class AssessmentDetailsActivity extends AppCompatActivity {
    private static final int ASSESSMENT_EDIT_ACTIVITY_REQUEST_CODE = 1;
    AssessmentViewModel assessmentViewModel;
    Assessment assessment;
    Course course;
    private AutoCompleteTextView dropdownTextView, dropdownCourses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        // Set the assessment details to assessment received from intent.
        assessment = (Assessment) getIntent().getSerializableExtra("Assessment");

        EditText assessmentName = findViewById(R.id.assessmentDetails_AssessmentName);
        EditText assessmentDueDate = findViewById(R.id.assessmentDetails_AssessmentDueDate);
        EditText assessmentType = findViewById(R.id.assessmentDetails_TypeDropdownTextView);
        EditText assessmentNotes = findViewById(R.id.assessmentDetails_AssessmentNotes);
        AutoCompleteTextView dropdownCourses = findViewById(R.id.assessmentDetails_CourseDropdownTextView);

        assessmentName.setText(assessment.getAssessmentName());
        assessmentDueDate.setText(assessment.getAssessmentDueDate().toString());
        assessmentType.setText(assessment.getAssessmentType());
        assessmentNotes.setText(assessment.getAssessmentInfo());


        // Add courses to course dropdown
        final CourseListAdapter courseListAdapter = new CourseListAdapter(this);
        Course[] dropdownCoursesItems = new Course[]{};
        ArrayList<Course> courseList = new ArrayList<>();
        ArrayAdapter<Course> dropdownCoursesAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item, courseList);

        //Add the ViewModel
        CourseViewModel courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCourseList()
                .observe(this, new Observer<List<Course>>() {
                    @Override
                    public void onChanged(List<Course> courses) {
                        for (Course course : courses
                        ) {
                            courseList.add(course);

                            if (course.getCourseId() == assessment.getCourseIdFk())
                                dropdownCourses.setText(course.getCourseName());
                        }
                        dropdownCoursesAdapter.notifyDataSetChanged();
                        courseListAdapter.setCourses(courses);
                    }
                });
        dropdownCourses.setAdapter(dropdownCoursesAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assessment_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.assessmentDetails_EditAssessmentMenu:
                Intent editAssessmentIntent = new Intent(getApplicationContext(), AssessmentEditActivity.class);
                editAssessmentIntent.putExtra("Assessment", assessment);
                startActivityForResult(editAssessmentIntent, ASSESSMENT_EDIT_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.assessmentDetails_DeleteAssessmentMenu:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                assessmentViewModel.deleteAssessment(assessment);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                        Intent returnTo = new Intent(getApplicationContext(), AssessmentActivity.class);
                        startActivity(returnTo);
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(AssessmentDetailsActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ASSESSMENT_EDIT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Assessment assessmentToEdit = (Assessment) data.getSerializableExtra("Assessment");
            assessmentViewModel.updateAssessment(assessmentToEdit);
            Intent returnTo = new Intent(getApplicationContext(), AssessmentActivity.class);
            startActivity(returnTo);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent assessmentActivityIntent = new Intent(getApplicationContext(), AssessmentActivity.class);
        startActivity(assessmentActivityIntent);
        super.onBackPressed();
        return true;
    }
}