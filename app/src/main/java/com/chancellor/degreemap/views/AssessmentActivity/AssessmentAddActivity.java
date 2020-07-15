package com.chancellor.degreemap.views.AssessmentActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Assessment;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.utilities.DateTypeConverter;
import com.chancellor.degreemap.viewadapters.CourseListAdapter;
import com.chancellor.degreemap.viewmodels.CourseViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AssessmentAddActivity extends AppCompatActivity {
    Assessment assessment = new Assessment();
    EditText assessmentName;
    EditText assessmentDueDate;
    EditText assessmentType;
    EditText assessmentNotes;
    private AutoCompleteTextView dropdownTextView, dropdownCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assessmentName = findViewById(R.id.assessmentAdd_AssessmentName);
        assessmentDueDate = findViewById(R.id.assessmentAdd_AssessmentDueDate);
        assessmentType = findViewById(R.id.assessmentAdd_TypeDropdownTextView);
        assessmentNotes = findViewById(R.id.assessmentAdd_AssessmentNotes);

        dropdownTextView = findViewById(R.id.assessmentAdd_TypeDropdownTextView);
        String[] dropdownItems = new String[]{
                "Objective Assessment",
                "Performance Assessment"
        };
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item, dropdownItems
        );
        dropdownTextView.setAdapter(dropdownAdapter);

        // Add courses to course dropdown
        dropdownCourses = findViewById(R.id.assessmentAdd_CourseDropdownTextView);
        final CourseListAdapter courseListAdapter = new CourseListAdapter(this);
        String[] dropdownCoursesItems = new String[]{};
        ArrayList<String> courseList = new ArrayList<>();
        ArrayAdapter<String> dropdownCoursesAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item, courseList);

        //Add the ViewModel
        CourseViewModel courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCourseList()
                .observe(this, new Observer<List<Course>>() {
                    @Override
                    public void onChanged(List<Course> courses) {
                        for (Course course : courses
                        ) {
                            courseList.add(course.getCourseName());
                        }
                        dropdownCoursesAdapter.notifyDataSetChanged();
                        courseListAdapter.setCourses(courses);
                    }
                });
        dropdownCourses.setAdapter(dropdownCoursesAdapter);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        assessmentDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AssessmentAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Month returned is 0 based, so add 1
                        month = month + 1;
                        String date = year + "-" +
                                //Format with leading zero (if needed), no library helper
                                ("00" + String.valueOf(month)).substring(String.valueOf(month).length())
                                + "-" +
                                //Format with leading zero (if needed), no library helper
                                ("00" + String.valueOf(dayOfMonth)).substring(String.valueOf(dayOfMonth).length());
                        assessmentDueDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        MaterialButton assessmentAdd_SaveButton = findViewById(R.id.assessmentAdd_Save);
        assessmentAdd_SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                assessment.setAssessmentName(assessmentName.getText().toString());
                assessment.setAssessmentDueDate(DateTypeConverter.toDate(assessmentDueDate.getText().toString()));
                assessment.setAssessmentType(assessmentType.getText().toString());
                assessment.setAssessmentInfo(assessmentNotes.getText().toString());

                String courseName = dropdownCourses.getText().toString();

                if (assessment.getAssessmentName().isEmpty() ||
                        assessment.getAssessmentDueDate().toString().isEmpty() ||
                        assessment.getAssessmentInfo().isEmpty())
                    Snackbar.make(view, "Error! Name, Due Date and Notes can't be blank.",
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {
                    replyIntent.putExtra("Assessment", assessment);
                    replyIntent.putExtra("CourseName", courseName);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent termActivityIntent = new Intent(getApplicationContext(), AssessmentActivity.class);
        startActivity(termActivityIntent);
        super.onBackPressed();
        return true;
    }
}