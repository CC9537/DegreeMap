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
import com.chancellor.degreemap.viewadapters.TermListAdapter;
import com.chancellor.degreemap.viewmodels.TermViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

public class TermAddActivity extends AppCompatActivity {
    TermViewModel termViewModel;
    EditText termName;
    EditText termStart;
    EditText termEnd;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        termName = findViewById(R.id.termAdd_TermName);
        termStart = findViewById(R.id.termAdd_TermStartDate);
        termEnd = findViewById(R.id.termAdd_TermEndDate);

        setEditableFalse(termStart);
        setEditableFalse(termEnd);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        final TermListAdapter termListAdapter = new TermListAdapter(this);
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getTermList().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                termListAdapter.setTerms(terms);
            }
        });

        termStart.setOnClickListener(new View.OnClickListener() {
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
                        termStart.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        termEnd.setOnClickListener(new View.OnClickListener() {
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
                        termEnd.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        MaterialButton fab = findViewById(R.id.termAdd_Save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                String termNameVal = termName.getText().toString();
                String termStartVal = termStart.getText().toString();
                String termEndVal = termEnd.getText().toString();


                if (termNameVal.isEmpty() || termStartVal.isEmpty() || termEndVal.isEmpty())
                    Snackbar.make(view, "Error! Name, Start and End Date can't be blank.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {
                    replyIntent.putExtra("termName", termNameVal);
                    replyIntent.putExtra("termStart", termStartVal);
                    replyIntent.putExtra("termEnd", termEndVal);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    private void setEditableFalse(EditText editText) {
        editText.setFocusable(false);
        editText.setClickable(false);
        editText.setCursorVisible(false);
    }
}