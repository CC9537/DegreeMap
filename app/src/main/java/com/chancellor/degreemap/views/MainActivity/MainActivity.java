package com.chancellor.degreemap.views.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.views.TermActivity.TermActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.termToolbar);
        setSupportActionBar(toolbar);

        Button btnTerms = findViewById(R.id.btnTerms);
        btnTerms.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent termsActivityIntent = new Intent(getApplicationContext(), TermActivity.class);
                startActivity(termsActivityIntent);
            }
        }));

    }
}