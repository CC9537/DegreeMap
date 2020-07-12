package com.chancellor.degreemap.views.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.views.AssessmentActivity.AssessmentActivity;
import com.chancellor.degreemap.views.CourseActivity.CourseActivity;
import com.chancellor.degreemap.views.MentorActivity.MentorActivity;
import com.chancellor.degreemap.views.TermActivity.TermActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

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