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

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Assessment;
import com.chancellor.degreemap.utilities.DateTypeConverter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AssessmentEditActivity extends AppCompatActivity {
    Assessment assessment;
    private AutoCompleteTextView dropdownTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText assessmentName = findViewById(R.id.assessmentEdit_AssessmentName);
        EditText assessmentDueDate = findViewById(R.id.assessmentEdit_AssessmentDueDate);
        EditText assessmentType = findViewById(R.id.assessmentEdit_TypeDropdownTextView);
        EditText assessmentNotes = findViewById(R.id.assessmentEdit_AssessmentNotes);

        dropdownTextView = findViewById(R.id.assessmentAdd_TypeDropdownTextView);
        String[] dropdownItems = new String[]{
                "Objective Assessment",
                "Performance Assessment"
        };
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item, dropdownItems
        );
        dropdownTextView.setAdapter(dropdownAdapter);

        // Set the assessment details to assessment received from intent.
        assessment = (Assessment) getIntent().getSerializableExtra("Assessment");

        assessmentName.setText(assessment.getAssessmentName());
        assessmentDueDate.setText(assessment.getAssessmentDueDate().toString());
        assessmentType.setText(assessment.getAssessmentType());
        assessmentNotes.setText(assessment.getAssessmentInfo());

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
                        assessment.getAssessmentInfo().isEmpty())
                    Snackbar.make(view, "Error! Name, Due Date and Notes can't be blank.",
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {
                    replyIntent.putExtra("Assessment", assessment);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent assessmentActivityIntent = new Intent(getApplicationContext(), AssessmentActivity.class);
        startActivity(assessmentActivityIntent);
        super.onBackPressed();
        return true;
    }
}