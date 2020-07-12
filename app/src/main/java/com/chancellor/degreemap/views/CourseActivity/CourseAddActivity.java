package com.chancellor.degreemap.views.CourseActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.database.DegreeMapRepository;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.models.Mentor;
import com.chancellor.degreemap.viewadapters.MentorListAdapter;
import com.chancellor.degreemap.viewmodels.CourseViewModel;
import com.chancellor.degreemap.viewmodels.MentorViewModel;

import java.util.ArrayList;
import java.util.List;

public class CourseAddActivity extends AppCompatActivity {
    CourseViewModel courseViewModel;
    Course course;
    Mentor mentorForCourse;
    private DegreeMapRepository degreeMapRepository;
    private AutoCompleteTextView dropdownTextView, dropdownMentors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        degreeMapRepository = new DegreeMapRepository(getApplication());

        dropdownTextView = findViewById(R.id.courseAdd_StatusDropdownTextView);
        String[] dropdownItems = new String[]{
                "Pending",
                "In Progress",
                "Complete"
        };
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item, dropdownItems
        );
        dropdownTextView.setAdapter(dropdownAdapter);

        // Add mentors to mentor dropdown
        dropdownMentors = findViewById(R.id.courseAdd_MentorDropdownTextView);
        final MentorListAdapter mentorListAdapter = new MentorListAdapter(this);
        String[] dropdownMentorsItems = new String[]{};
        ArrayList<String> mentorList = new ArrayList<>();
        ArrayAdapter<String> dropdownMentorsAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item, mentorList);

        //Add the ViewModel
        MentorViewModel mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
        mentorViewModel.getMentorList()
                .observe(this, new Observer<List<Mentor>>() {
                    @Override
                    public void onChanged(List<Mentor> mentors) {
                        for (Mentor mentor : mentors
                        ) {
                            mentorList.add(mentor.getMentorName());
                        }
                        dropdownMentorsAdapter.notifyDataSetChanged();
                        mentorListAdapter.setMentors(mentors);
                    }
                });
        dropdownMentors.setAdapter(dropdownMentorsAdapter);

    }
}