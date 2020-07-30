package com.chancellor.degreemap.views.TermActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Term;
import com.chancellor.degreemap.utilities.DateTypeConverter;
import com.chancellor.degreemap.viewadapters.TermListAdapter;
import com.chancellor.degreemap.viewmodels.TermViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

public class TermAddActivity extends AppCompatActivity {
    TermViewModel termViewModel;
    EditText termName;
    EditText termStartDate;
    EditText termEndDate;
    Term term = new Term();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        termName = findViewById(R.id.termAdd_TermName);
        termStartDate = findViewById(R.id.termAdd_TermStartDate);
        termEndDate = findViewById(R.id.termAdd_TermEndDate);

        final TermListAdapter termListAdapter = new TermListAdapter(this);
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getTermList().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                termListAdapter.setTerms(terms);
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        termStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TermAddActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        termStartDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        termEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TermAddActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        termEndDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        MaterialButton termAdd_SaveButton = findViewById(R.id.termAdd_Save);
        termAdd_SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if (termName.getText().toString().isEmpty() ||
                        termStartDate.getText().toString().isEmpty() ||
                        termEndDate.getText().toString().isEmpty())
                    Snackbar.make(view, "Error! Name, Start and End Date can't be blank.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {
                    term.setTermName(termName.getText().toString());
                    term.setTermStart(DateTypeConverter.toDate(termStartDate.getText().toString()));
                    term.setTermEnd(DateTypeConverter.toDate(termEndDate.getText().toString()));
                    replyIntent.putExtra("Term", term);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent mainActivityIntent = new Intent(getApplicationContext(), TermActivity.class);
        startActivity(mainActivityIntent);
        super.onBackPressed();
        return true;
    }
}