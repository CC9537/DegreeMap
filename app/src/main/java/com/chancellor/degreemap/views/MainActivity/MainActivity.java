package com.chancellor.degreemap.views.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.viewmodels.MainViewModel;
import com.chancellor.degreemap.views.AssessmentActivity.AssessmentActivity;
import com.chancellor.degreemap.views.CourseActivity.CourseActivity;
import com.chancellor.degreemap.views.MentorActivity.MentorActivity;
import com.chancellor.degreemap.views.TermActivity.TermActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Course> courseArray = new ArrayList<>();
    int courseCount, coursesPending, coursesInProgress, coursesComplete;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        courseCount = mainViewModel.countCourses();
        coursesPending = mainViewModel.countCoursesPending();
        coursesInProgress = mainViewModel.countCoursesInProgress();
        coursesComplete = mainViewModel.countCoursesComplete();


        int progress = courseCount == 0 ? 0 : (coursesComplete * 100 / courseCount);
        int secondaryProgress = courseCount == 0 ? 0 : ((coursesComplete + coursesInProgress) * 100) / courseCount;
        ProgressBar progressBar = findViewById(R.id.courseProgress);
        progressBar.setMax(100);
        progressBar.setMin(0);
        progressBar.setProgress(progress, true);
        progressBar.setSecondaryProgress(secondaryProgress);

        TextView tvCourseCount = findViewById(R.id.main_coursesCount);
        TextView tvCoursePendingCount = findViewById(R.id.main_CoursesPending);
        TextView tvCourseInProgressCount = findViewById(R.id.main_coursesInProgress);
        TextView tvCourseCompleteCount = findViewById(R.id.main_coursesComplete);

        tvCoursePendingCount.setText("Number of courses pending: " + coursesPending);
        tvCourseInProgressCount.setText("Number of courses in progress: " + coursesInProgress);
        tvCourseCompleteCount.setText("Number of courses complete: " + coursesComplete);
        tvCourseCount.setText("Total number of courses: " + courseCount);


        mainViewModel.getCourseList().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                for (Course course : courses) {
                    courseArray.add(course);
                }
            }
        });

        CardView cardTerms = findViewById(R.id.main_cardView_terms);
        cardTerms.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent termsActivityIntent = new Intent(getApplicationContext(), TermActivity.class);
                startActivity(termsActivityIntent);
            }
        }));

        CardView cardCourses = findViewById(R.id.main_cardView_courses);
        cardCourses.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseActivityIntent = new Intent(getApplicationContext(), CourseActivity.class);
                startActivity(courseActivityIntent);
            }
        }));

        CardView cardAssessments = findViewById(R.id.main_cardView_assessments);
        cardAssessments.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent assessmentActivityIntent = new Intent(getApplicationContext(), AssessmentActivity.class);
                startActivity(assessmentActivityIntent);
            }
        }));

        CardView cardMentors = findViewById(R.id.main_cardView_mentors);
        cardMentors.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mentorActivityIntent = new Intent(getApplicationContext(), MentorActivity.class);
                startActivity(mentorActivityIntent);
            }
        }));

    }
}