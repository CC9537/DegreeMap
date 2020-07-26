package com.chancellor.degreemap.views.CourseActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.database.DegreeMapRepository;
import com.chancellor.degreemap.models.Assessment;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.models.Mentor;
import com.chancellor.degreemap.viewadapters.AssessmentListAdapter;
import com.chancellor.degreemap.viewmodels.AssessmentViewModel;
import com.chancellor.degreemap.viewmodels.CourseViewModel;
import com.chancellor.degreemap.views.TermActivity.TermActivity;

import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {
    private static final int COURSE_EDIT_ACTIVITY_REQUEST_CODE = 1;
    CourseViewModel courseViewModel;
    Course course;
    Mentor mentorForCourse;
    private DegreeMapRepository degreeMapRepository;
//    private TextView statusTextView, mentorNameTextView, mentorPhoneTextView, mentorEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        degreeMapRepository = new DegreeMapRepository(getApplication());


//        String[] dropdownItems = new String[]{
//                "Pending",
//                "In Progress",
//                "Complete"
//        };
//        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(
//                getApplicationContext(), R.layout.dropdown_item, dropdownItems
//        );
//        dropdownTextView.setAdapter(dropdownAdapter);

        // Add mentors to mentor dropdown

//        final MentorListAdapter mentorListAdapter = new MentorListAdapter(this);
//        String[] dropdownMentorsItems = new String[]{};
//        ArrayList<String> mentorList = new ArrayList<>();
//        ArrayAdapter<String> dropdownMentorsAdapter = new ArrayAdapter<>(
//                getApplicationContext(), R.layout.dropdown_item, mentorList);
//
//
//        //Add the ViewModel
//        MentorViewModel mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
//        mentorViewModel.getMentorList()
//                .observe(this, new Observer<List<Mentor>>() {
//                    @Override
//                    public void onChanged(List<Mentor> mentors) {
//                        for (Mentor mentor : mentors
//                        ) {
//                            mentorList.add(mentor.getMentorName());
//                        }
//                        dropdownMentorsAdapter.notifyDataSetChanged();
//                        mentorListAdapter.setMentors(mentors);
//                    }
//                });
//        mentorNameTextView, mentorPhoneTextView, mentorEmailTextView.setAdapter(dropdownMentorsAdapter);

        // Set the course details to the course received from intent
        course = (Course) getIntent().getSerializableExtra("Course");
        //   mentorForCourse = mentorViewModel.getMentorForCourse(course.getCourseId()).getValue();
//                .observe(this, new Observer<Mentor>() {
//                    @Override
//                    public void onChanged(Mentor mentor) {
//                        mentorForCourse = mentor;
//                    }
//                });

        EditText courseName = findViewById(R.id.courseDetails_CourseName);
        EditText courseStartDate = findViewById(R.id.courseDetails_CourseStartDate);
        EditText courseEndDate = findViewById(R.id.courseDetails_CourseEndDate);
//        AutoCompleteTextView courseStatus = findViewById(R.id.courseDetails_StatusTextView);
//        AutoCompleteTextView courseMentor = findViewById(R.id.courseDetails_MentorDropdownTextView);
        EditText courseNotes = findViewById(R.id.courseDetails_CourseNotes);
        TextView statusTextView = findViewById(R.id.courseDetails_StatusTextView);
        TextView mentorNameTextView = findViewById(R.id.courseDetails_MentorNameTextView);
        TextView mentorPhoneTextView = findViewById(R.id.courseDetails_MentorPhoneTextView);
        TextView mentorEmailTextView = findViewById(R.id.courseDetails_MentorEmailTextView);

        courseName.setText(course.getCourseName());
        courseStartDate.setText(course.getCourseStart().toString());
        courseEndDate.setText(course.getCourseEnd().toString());
        statusTextView.setText(course.getCourseStatus());
        courseNotes.setText(course.getCourseNotes());
        mentorNameTextView.setText(course.getMentor().getMentorName());
        mentorPhoneTextView.setText(course.getMentor().getMentorPhone());
        mentorEmailTextView.setText(course.getMentor().getMentorEmail());

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        RecyclerView courseDetailsAssessmentRecyclerView = findViewById(R.id.courseDetails_AssessmentRecyclerView);
        final AssessmentListAdapter assessmentListAdapter = new AssessmentListAdapter(this);
        courseDetailsAssessmentRecyclerView.setAdapter(assessmentListAdapter);
        courseDetailsAssessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Add the ViewModel
        AssessmentViewModel assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAssessmentsByCourseId(course.getCourseId())
                .observe(this, new Observer<List<Assessment>>() {
                    @Override
                    public void onChanged(List<Assessment> assessments) {
                        assessmentListAdapter.setAssessments(assessments);
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.courseDetails_EditCourseMenu:
                Intent editCourseIntent = new Intent(getApplicationContext(), CourseEditActivity.class);
                editCourseIntent.putExtra("Course", course);
                startActivityForResult(editCourseIntent, COURSE_EDIT_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.termDetails_DeleteTermMenu:
//                if (numCoursesAssignedThisTerm > 0)
//                    Snackbar.make(getWindow().getDecorView(), "Error! Can't delete term when courses exist.", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                else {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                courseViewModel.deleteCourse(course);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                        Intent returnTo = new Intent(getApplicationContext(), TermActivity.class);
                        startActivity(returnTo);
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailsActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
//                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COURSE_EDIT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Course courseToEdit = (Course) data.getSerializableExtra("Course");
            courseViewModel.updateCourse(courseToEdit);
            Intent returnTo = new Intent(getApplicationContext(), CourseActivity.class);
            startActivity(returnTo);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent termActivityIntent = new Intent(getApplicationContext(), CourseActivity.class);
        startActivity(termActivityIntent);
        super.onBackPressed();
        return true;
    }
}