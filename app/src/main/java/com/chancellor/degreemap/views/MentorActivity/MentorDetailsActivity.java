package com.chancellor.degreemap.views.MentorActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Mentor;
import com.chancellor.degreemap.viewmodels.MentorViewModel;

public class MentorDetailsActivity extends AppCompatActivity {
    private static final int MENTOR_EDIT_ACTIVITY_REQUEST_CODE = 1;
    MentorViewModel mentorViewModel;
    Mentor mentor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);

        // Set the mentor details to mentor received from intent.
        mentor = (Mentor) getIntent().getSerializableExtra("Mentor");

        EditText mentorName = findViewById(R.id.mentorDetails_MentorName);
        EditText mentorPhone = findViewById(R.id.mentorDetails_MentorPhone);
        EditText mentorEmail = findViewById(R.id.mentorDetails_MentorEmail);

        mentorName.setText(mentor.getMentorName());
        mentorPhone.setText(mentor.getMentorPhone());
        mentorEmail.setText(mentor.getMentorEmail());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mentor_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mentorDetails_EditMentorMenu:
                Intent editMentorIntent = new Intent(getApplicationContext(), MentorEditActivity.class);
                editMentorIntent.putExtra("Mentor", mentor);
                startActivityForResult(editMentorIntent, MENTOR_EDIT_ACTIVITY_REQUEST_CODE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MENTOR_EDIT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Mentor mentorToEdit = (Mentor) data.getSerializableExtra("Mentor");
            mentorViewModel.updateMentor(mentorToEdit);
            Intent returnTo = new Intent(getApplicationContext(), MentorActivity.class);
            startActivity(returnTo);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent mainActivityIntent = new Intent(getApplicationContext(), MentorActivity.class);
        startActivity(mainActivityIntent);
        super.onBackPressed();
        return true;
    }

}