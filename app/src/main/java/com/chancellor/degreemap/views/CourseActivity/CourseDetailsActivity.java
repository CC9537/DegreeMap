package com.chancellor.degreemap.views.CourseActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
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
import com.chancellor.degreemap.utilities.AlertReceiver;
import com.chancellor.degreemap.utilities.DateTypeConverter;
import com.chancellor.degreemap.viewadapters.AssessmentListAdapter;
import com.chancellor.degreemap.viewmodels.AssessmentViewModel;
import com.chancellor.degreemap.viewmodels.CourseViewModel;
import com.chancellor.degreemap.views.TermActivity.TermActivity;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {
    private static final int COURSE_EDIT_ACTIVITY_REQUEST_CODE = 1;
    CourseViewModel courseViewModel;
    Course course;
    Mentor mentorForCourse;
    EditText courseNotes, courseName, courseStartDate, courseEndDate;
    private DegreeMapRepository degreeMapRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        degreeMapRepository = new DegreeMapRepository(getApplication());


        course = (Course) getIntent().getSerializableExtra("Course");


        courseName = findViewById(R.id.courseDetails_CourseName);
        courseStartDate = findViewById(R.id.courseDetails_CourseStartDate);
        courseEndDate = findViewById(R.id.courseDetails_CourseEndDate);
        courseNotes = findViewById(R.id.courseDetails_CourseNotes);
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
            case R.id.courseDetails_DeleteCourseMenu:
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
                return true;
            case R.id.courseDetails_Share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, courseNotes.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Notes from " + courseName.getText().toString());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.courseDetails_AlertStart:
                Date alertDateStart = DateTypeConverter.toDate(courseStartDate.getText().toString());
                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTime(alertDateStart);

                Intent notificationIntentStart = new Intent(getApplicationContext(), AlertReceiver.class);
                notificationIntentStart.putExtra("title", course.getCourseName());
                notificationIntentStart.putExtra("type", "course");
                notificationIntentStart.putExtra("status", "starting");
                notificationIntentStart.putExtra("notification_id", course.getCourseId() + 1000);

                PendingIntent senderStart = PendingIntent.getBroadcast(getApplicationContext(), course.getCourseId() + 1000, notificationIntentStart, 0);
                AlarmManager alarmManagerStart = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManagerStart.set(AlarmManager.RTC_WAKEUP, calendarStart.getTimeInMillis(), senderStart);
                return true;
            case R.id.courseDetails_AlertEnd:
                Date alertDateEnd = DateTypeConverter.toDate(courseEndDate.getText().toString());
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTime(alertDateEnd);

                Intent notificationIntentEnd = new Intent(getApplicationContext(), AlertReceiver.class);
                notificationIntentEnd.putExtra("title", course.getCourseName());
                notificationIntentEnd.putExtra("type", "course");
                notificationIntentEnd.putExtra("status", "ending");
                notificationIntentEnd.putExtra("notification_id", course.getCourseId() + 2000);

                PendingIntent senderEnd = PendingIntent.getBroadcast(getApplicationContext(), course.getCourseId() + 2000, notificationIntentEnd, 0);
                AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, calendarEnd.getTimeInMillis(), senderEnd);
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