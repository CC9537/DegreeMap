package com.chancellor.degreemap.views.CourseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.viewadapters.CourseListAdapter;
import com.chancellor.degreemap.viewmodels.CourseViewModel;
import com.chancellor.degreemap.views.MainActivity.MainActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class CourseActivity extends AppCompatActivity {
    private static final int COURSE_ADD_ACTIVITY_REQUEST_CODE = 1;
    //Add the ViewModel
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView coursesRecyclerView = findViewById(R.id.course_recycler_view);
        final CourseListAdapter courseListAdapter = new CourseListAdapter(this);
        coursesRecyclerView.setAdapter(courseListAdapter);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCourseList().observe(this, courses -> {
            courseListAdapter.setCourses(courses);
        });

        ExtendedFloatingActionButton courseAddCourseFAB = findViewById(R.id.course_addCourseFAB);
        courseAddCourseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCourseIntent = new Intent(getApplicationContext(), CourseAddActivity.class);
                startActivityForResult(addCourseIntent, COURSE_ADD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COURSE_ADD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Course newCourse = (Course) data.getSerializableExtra("Course");
            courseViewModel.insertCourse(newCourse);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivityIntent);
        super.onBackPressed();
        return true;
    }
}