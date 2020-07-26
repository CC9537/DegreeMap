package com.chancellor.degreemap.views.AssessmentActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class AssessmentEditActivity extends AppCompatActivity {
    Assessment assessment;
    EditText assessmentName;
    EditText assessmentDueDate;
    EditText assessmentType;
    EditText assessmentNotes;
    Course course;
    private AutoCompleteTextView dropdownTextView, dropdownCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assessmentName = findViewById(R.id.assessmentEdit_AssessmentName);
        assessmentDueDate = findViewById(R.id.assessmentEdit_AssessmentDueDate);
        assessmentType = findViewById(R.id.assessmentEdit_TypeDropdownTextView);
        assessmentNotes = findViewById(R.id.assessmentEdit_AssessmentNotes);
        dropdownCourses = findViewById(R.id.assessmentEdit_CourseDropdownTextView);

        assessment = (Assessment) getIntent().getSerializableExtra("Assessment");

        assessmentName.setText(assessment.getAssessmentName());
        assessmentDueDate.setText(assessment.getAssessmentDueDate().toString());
        assessmentType.setText(assessment.getAssessmentType());
        assessmentNotes.setText(assessment.getAssessmentInfo());


        dropdownTextView = findViewById(R.id.assessmentEdit_TypeDropdownTextView);
        String[] dropdownItems = new String[]{
                "Objective Assessment",
                "Performance Assessment"
        };
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item, dropdownItems
        );
        dropdownTextView.setAdapter(dropdownAdapter);

        // Edit courses to course dropdown
        final CourseListAdapter courseListAdapter = new CourseListAdapter(this);
        Course[] dropdownCoursesItems = new Course[]{};
        ArrayList<Course> courseList = new ArrayList<>();
        ArrayAdapter<Course> dropdownCoursesAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item, courseList);

        //Edit the ViewModel
        CourseViewModel courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCourseList()
                .observe(this, new Observer<List<Course>>() {
                    @Override
                    public void onChanged(List<Course> courses) {
                        for (Course course : courses
                        ) {
                            if (course.getCourseId() == assessment.getCourseIdFk())
                                dropdownCourses.setText(course.getCourseName(), false);
                            courseList.add(course);
                        }
                        dropdownCoursesAdapter.notifyDataSetChanged();
                        courseListAdapter.setCourses(courses);
                    }
                });
        dropdownCourses.setAdapter(dropdownCoursesAdapter);
        AssessmentEditActivity.CourseSelected courseSelected = new AssessmentEditActivity.CourseSelected();
        dropdownCourses.setOnItemSelectedListener(courseSelected);
        dropdownCourses.setOnItemClickListener(courseSelected);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        assessmentDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AssessmentEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Month returned is 0 based, so edit 1
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

        MaterialButton assessmentEdit_SaveButton = findViewById(R.id.assessmentEdit_Save);
        assessmentEdit_SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                assessment.setAssessmentName(assessmentName.getText().toString());
                assessment.setAssessmentDueDate(DateTypeConverter.toDate(assessmentDueDate.getText().toString()));
                assessment.setAssessmentType(assessmentType.getText().toString());
                assessment.setAssessmentInfo(assessmentNotes.getText().toString());


                if (assessment.getAssessmentName().isEmpty() ||
                        assessment.getAssessmentDueDate().toString().isEmpty() ||
                        assessment.getAssessmentInfo().isEmpty() || courseSelected == null)
                    Snackbar.make(view, "Error! Name, Due Date, Notes and Course can't be blank.",
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {
                    assessment.setCourseIdFk(course.getCourseId());
                    replyIntent.putExtra("Assessment", assessment);
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

    class CourseSelected implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnFocusChangeListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            course = (Course) dropdownCourses.getAdapter().getItem(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            course = null;
        }

        @Override
        public void onFocusChange(View view, boolean b) {

        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            course = (Course) dropdownCourses.getAdapter().getItem(i);
        }
    }
}