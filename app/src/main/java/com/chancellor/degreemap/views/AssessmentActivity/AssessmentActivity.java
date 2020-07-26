package com.chancellor.degreemap.views.AssessmentActivity;

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
import com.chancellor.degreemap.models.Assessment;
import com.chancellor.degreemap.viewadapters.AssessmentListAdapter;
import com.chancellor.degreemap.viewmodels.AssessmentViewModel;
import com.chancellor.degreemap.views.MainActivity.MainActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class AssessmentActivity extends AppCompatActivity {
    private static final int ASSESSMENT_ADD_ACTIVITY_REQUEST_CODE = 1;
    //Add the ViewModel
    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView assessmentsRecyclerView = findViewById(R.id.assessment_recycler_view);
        final AssessmentListAdapter assessmentListAdapter = new AssessmentListAdapter(this);
        assessmentsRecyclerView.setAdapter(assessmentListAdapter);
        assessmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAssessmentList().observe(this, assessments -> {
            assessmentListAdapter.setAssessments(assessments);
        });

        ExtendedFloatingActionButton assessmentAddAssessmentFAB = findViewById(R.id.assessment_addAssessmentFAB);
        assessmentAddAssessmentFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addAssessmentIntent = new Intent(getApplicationContext(), AssessmentAddActivity.class);
                startActivityForResult(addAssessmentIntent, ASSESSMENT_ADD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == ASSESSMENT_ADD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Assessment newAssessment = (Assessment) data.getSerializableExtra("Assessment");
            assessmentViewModel.insertAssessment(newAssessment);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivityIntent);
        super.onBackPressed();
        return true;
    }
}