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

public class MentorEditActivity extends AppCompatActivity {
    Mentor mentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set the mentor details to mentor received from intent.
        mentor = (Mentor) getIntent().getSerializableExtra("Mentor");

        EditText mentorName = findViewById(R.id.mentorEdit_MentorName);
        EditText mentorPhone = findViewById(R.id.mentorEdit_MentorPhone);
        EditText mentorEmail = findViewById(R.id.mentorEdit_MentorEmail);

        mentorName.setText(mentor.getMentorName());
        mentorPhone.setText(mentor.getMentorPhone());
        mentorEmail.setText(mentor.getMentorEmail());


        MaterialButton mentorEdit_saveButton = findViewById(R.id.mentorEdit_Save);
        mentorEdit_saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if (mentorName.getText().toString().isEmpty() ||
                        mentorPhone.getText().toString().isEmpty() ||
                        mentorEmail.getText().toString().isEmpty())
                    Snackbar.make(view, "Error! Name, Phone and Email can't be blank.",
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {
                    mentor.setMentorName(mentorName.getText().toString());
                    mentor.setMentorPhone(mentorPhone.getText().toString());
                    mentor.setMentorEmail(mentorEmail.getText().toString());
                    replyIntent.putExtra("Mentor", mentor);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent mainActivityIntent = new Intent(getApplicationContext(), MentorActivity.class);
        startActivity(mainActivityIntent);
        super.onBackPressed();
        return true;
    }
}