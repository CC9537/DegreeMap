package com.chancellor.degreemap.views.MentorActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Mentor;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class MentorAddActivity extends AppCompatActivity {
    Mentor mentor = new Mentor();
    EditText mentorName;
    EditText mentorPhone;
    EditText mentorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mentorName = findViewById(R.id.mentorAdd_MentorName);
        mentorPhone = findViewById(R.id.mentorAdd_MentorPhone);
        mentorEmail = findViewById(R.id.mentorAdd_MentorEmail);

        MaterialButton mentorAdd_saveButton = findViewById(R.id.mentorAdd_Save);
        mentorAdd_saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                mentor.setMentorName(mentorName.getText().toString());
                mentor.setMentorPhone(mentorPhone.getText().toString());
                mentor.setMentorEmail(mentorEmail.getText().toString());

                if (mentor.getMentorName().isEmpty() ||
                        mentor.getMentorPhone().isEmpty() ||
                        mentor.getMentorEmail().isEmpty())
                    Snackbar.make(view, "Error! Name, Phone and Email can't be blank.",
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {
                    replyIntent.putExtra("Mentor", mentor);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent termActivityIntent = new Intent(getApplicationContext(), MentorActivity.class);
        startActivity(termActivityIntent);
        super.onBackPressed();
        return true;
    }
}