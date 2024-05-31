package com.example.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FinalScoreActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private Button newQuizButton;
    private Button finishButton;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        // Initialize views
        scoreTextView = findViewById(R.id.scoreTextView);
        newQuizButton = findViewById(R.id.newQuizButton);
        finishButton = findViewById(R.id.finishButton);

        // Get the score and user name
        int score = getIntent().getIntExtra("SCORE", 0);
        userName = getIntent().getStringExtra("USER_NAME");
        scoreTextView.setText("Your Score: " + score);

        // Set onClickListener for the new quiz button
        newQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalScoreActivity.this, QuizActivity.class);
                intent.putExtra("USER_NAME", userName);
                startActivity(intent);
                finish();
            }
        });

        // Set onClickListener for the finish button
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();  // Close the app
            }
        });
    }
}
