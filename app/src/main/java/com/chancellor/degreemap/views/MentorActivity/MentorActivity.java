package com.chancellor.degreemap.views.MentorActivity;

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
import com.chancellor.degreemap.models.Mentor;
import com.chancellor.degreemap.viewadapters.MentorListAdapter;
import com.chancellor.degreemap.viewmodels.MentorViewModel;
import com.chancellor.degreemap.views.MainActivity.MainActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MentorActivity extends AppCompatActivity {

    private static final int MENTOR_ADD_ACTIVITY_REQUEST_CODE = 1;
    //Add the ViewModel
    private MentorViewModel mentorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView mentorsRecyclerView = findViewById(R.id.mentor_recycler_view);
        final MentorListAdapter mentorListAdapter = new MentorListAdapter(this);
        mentorsRecyclerView.setAdapter(mentorListAdapter);
        mentorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
        mentorViewModel.getMentorList().observe(this, mentors -> {
            mentorListAdapter.setMentors(mentors);
        });

        ExtendedFloatingActionButton mentorAddMentorFAB = findViewById(R.id.mentor_addMentorFAB);
        mentorAddMentorFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMentorIntent = new Intent(getApplicationContext(), MentorAddActivity.class);
                startActivityForResult(addMentorIntent, MENTOR_ADD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MENTOR_ADD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Mentor newMentor = (Mentor) data.getSerializableExtra("Mentor");
            mentorViewModel.insertMentor(newMentor);
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