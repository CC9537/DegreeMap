package com.chancellor.degreemap.views.CourseActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.models.Mentor;
import com.chancellor.degreemap.models.Term;
import com.chancellor.degreemap.utilities.DateTypeConverter;
import com.chancellor.degreemap.viewadapters.MentorListAdapter;
import com.chancellor.degreemap.viewadapters.TermListAdapter;
import com.chancellor.degreemap.viewmodels.CourseViewModel;
import com.chancellor.degreemap.viewmodels.MentorViewModel;
import com.chancellor.degreemap.viewmodels.TermViewModel;
import com.chancellor.degreemap.views.MentorActivity.MentorAddActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CourseEditActivity extends AppCompatActivity {
    private static final int MENTOR_ADD_REQUEST_CODE = 1;
    CourseViewModel courseViewModel;
    MentorViewModel mentorViewModel;
    Course course;
    Term termForCourse;
    Mentor mentorForCourse;
    private AutoCompleteTextView dropdownTextView, dropdownMentors, dropDownTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        course = (Course) getIntent().getSerializableExtra("Course");

        EditText courseName = findViewById(R.id.courseEdit_CourseName);
        EditText courseStartDate = findViewById(R.id.courseEdit_CourseStartDate);
        EditText courseEndDate = findViewById(R.id.courseEdit_CourseEndDate);
        EditText courseNotes = findViewById(R.id.courseEdit_CourseNotes);

        courseName.setText(course.getCourseName());
        courseStartDate.setText(course.getCourseStart().toString());
        courseEndDate.setText(course.getCourseEnd().toString());
        courseNotes.setText(course.getCourseNotes());


        if (getIntent().hasExtra("Term"))
            termForCourse = (Term) getIntent().getSerializableExtra("Term");

        dropdownTextView = findViewById(R.id.courseEdit_StatusDropdownTextView);
        String[] dropdownItems = new String[]{
                "Pending",
                "In Progress",
                "Complete"
        };
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item, dropdownItems
        );
        dropdownTextView.setAdapter(dropdownAdapter);
        dropdownTextView.setText(course.getCourseStatus(), false);

        // Edit mentors to mentor dropdown
        dropdownMentors = findViewById(R.id.courseEdit_MentorDropdownTextView);
        final MentorListAdapter mentorListAdapter = new MentorListAdapter(this);
        String[] dropdownMentorsItems = new String[]{};
        ArrayList<Mentor> mentorList = new ArrayList<>();
        ArrayAdapter<Mentor> dropdownMentorsAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item, mentorList);

        //Edit the ViewModel
        mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
        mentorViewModel.getMentorList()
                .observe(this, new Observer<List<Mentor>>() {
                    @Override
                    public void onChanged(List<Mentor> mentors) {
                        for (Mentor mentor : mentors
                        ) {
                            mentorList.add(mentor);
                            if (mentor.getMentorId() == course.getMentor().getMentorId())
                                mentorForCourse = mentor;
                        }
                        dropdownMentorsAdapter.notifyDataSetChanged();
                        mentorListAdapter.setMentors(mentors);
                    }
                });
        dropdownMentors.setAdapter(dropdownMentorsAdapter);
        CourseEditActivity.MentorSelected mentorSelected = new CourseEditActivity.MentorSelected();
        dropdownMentors.setOnItemSelectedListener(mentorSelected);
        dropdownMentors.setOnItemClickListener(mentorSelected);
        dropdownMentors.setText(course.getMentor().getMentorName(), false);

        dropDownTerms = findViewById(R.id.courseEdit_TermDropdownTextView);
        final TermListAdapter termListAdapter = new TermListAdapter(this);
        String[] dropdownTermItems = new String[]{};
        ArrayList<Term> termList = new ArrayList<>();
        ArrayAdapter<Term> dropdownTermsAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item, termList);

        //Edit the ViewModel
        TermViewModel termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getTermList()
                .observe(this, new Observer<List<Term>>() {
                    @Override
                    public void onChanged(List<Term> terms) {
                        for (Term term : terms
                        ) {
                            termList.add(term);
                            if (term.getTermId() == course.getTermIdFk()) {
                                dropDownTerms.setText(term.getTermName(), false);
                                termForCourse = term;
                            }
                        }
                        dropdownTermsAdapter.notifyDataSetChanged();
                        termListAdapter.setTerms(terms);
                    }
                });
        dropDownTerms.setAdapter(dropdownTermsAdapter);
        CourseEditActivity.TermSelected termSelected = new CourseEditActivity.TermSelected();
        dropDownTerms.setOnItemClickListener(termSelected);
        dropDownTerms.setOnItemSelectedListener(termSelected);
        if (termForCourse != null)
            dropDownTerms.setText(termForCourse.getTermName(), true);

        Button mentorAddButton = findViewById(R.id.courseEdit_MentorAddButton);
        mentorAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mentorEditIntent = new Intent(getApplicationContext(), MentorAddActivity.class);
                startActivityForResult(mentorEditIntent, MENTOR_ADD_REQUEST_CODE);
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        courseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CourseEditActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        courseStartDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        courseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CourseEditActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        courseEndDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        MaterialButton courseEdit_SaveButton = findViewById(R.id.courseEdit_Save);
        courseEdit_SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                course.setCourseName(courseName.getText().toString());
                course.setCourseStart(DateTypeConverter.toDate(courseStartDate.getText().toString()));
                course.setCourseEnd(DateTypeConverter.toDate(courseEndDate.getText().toString()));
                course.setCourseStatus(dropdownTextView.getText().toString());
                course.setCourseNotes(courseNotes.getText().toString());

                if (course.getCourseName().isEmpty() ||
                        course.getCourseStart().toString().isEmpty() ||
                        course.getCourseEnd().toString().isEmpty() ||
                        course.getCourseNotes().isEmpty() ||
                        course.getCourseStatus().isEmpty() ||
                        termForCourse == null || mentorForCourse == null)
                    Snackbar.make(view, "Error! All fields requires. Can't be blank.",
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {
                    course.setMentor(mentorForCourse);
                    course.setTermIdFk(termForCourse.getTermId());
                    replyIntent.putExtra("Course", course);
                    // replyIntent.putExtra("CourseName", courseName);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MENTOR_ADD_REQUEST_CODE && resultCode == RESULT_OK) {
            Mentor newMentor = (Mentor) data.getSerializableExtra("Mentor");
            mentorViewModel.insertMentor(newMentor);
            mentorForCourse = newMentor;
            dropdownMentors.setText(newMentor.getMentorName(), true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent mainActivityIntent = new Intent(getApplicationContext(), CourseActivity.class);
        startActivity(mainActivityIntent);
        super.onBackPressed();
        return true;
    }

    class TermSelected implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            termForCourse = (Term) dropDownTerms.getAdapter().getItem(i);
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            termForCourse = (Term) dropDownTerms.getAdapter().getItem(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            termForCourse = null;
        }
    }

    class MentorSelected implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mentorForCourse = (Mentor) dropdownMentors.getAdapter().getItem(i);
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            mentorForCourse = (Mentor) dropdownMentors.getAdapter().getItem(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            mentorForCourse = null;
        }
    }
}