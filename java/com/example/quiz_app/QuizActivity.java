package com.example.quiz_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView welcomeTextView, questionTextView, progressTextView;
    private ProgressBar progressBar;
    private Button[] answerButtons = new Button[3];
    private Button submitButton, nextButton;

    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private int selectedAnswerIndex = -1;  // To keep track of the selected answer
    private String userName;
    private List<Question> quizQuestions;


    private Question[] questions = {
            new Question("Which method is used to start an activity?", new String[]{"startActivity", "beginActivity", "launchActivity"}, 0),
            new Question("Which layout is used to arrange elements in a single column or row?", new String[]{"LinearLayout", "RelativeLayout", "ConstraintLayout"}, 0),
            new Question("Which method is used to retrieve a view by its ID?", new String[]{"findViewById", "getViewById", "retrieveViewById"}, 0),
            new Question("Which class is used to create a new thread?", new String[]{"Thread", "AsyncTask", "Handler"}, 0),
            new Question("Which method is called when an activity is first created?", new String[]{"onCreate", "onStart", "onResume"}, 0),
            new Question("Which component is used to display a list of items?", new String[]{"ListView", "RecyclerView", "GridView"}, 1),
            new Question("Which XML attribute is used to set the text of a TextView?", new String[]{"android:text", "android:label", "android:caption"}, 0),
            new Question("Which class is used for logging messages?", new String[]{"Log", "Logger", "Logcat"}, 0),
            new Question("Which method is used to inflate a layout in a fragment?", new String[]{"onCreateView", "onViewCreated", "onStartView"}, 0),
            new Question("Which component is used to create a clickable button?", new String[]{"Button", "ClickButton", "PushButton"}, 0)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize views
        welcomeTextView = findViewById(R.id.welcomeTextView);
        questionTextView = findViewById(R.id.questionTextView);
        progressTextView = findViewById(R.id.progressTextView);
        progressBar = findViewById(R.id.progressBar);
        answerButtons[0] = findViewById(R.id.answerButton1);
        answerButtons[1] = findViewById(R.id.answerButton2);
        answerButtons[2] = findViewById(R.id.answerButton3);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);

        // Get the user name from the intent
        userName = getIntent().getStringExtra("USER_NAME");
        welcomeTextView.setText("Welcome, " + userName + "!");

        // Randomly select 5 questions from the set of 10
        List<Question> questionList = new ArrayList<>();
        Collections.addAll(questionList, questions);
        Collections.shuffle(questionList);
        quizQuestions = questionList.subList(0, 5);

        // Load the first question and update progress
        loadQuestion();
        updateProgress();

        // Set onClickListeners for answer buttons
        for (int i = 0; i < answerButtons.length; i++) {
            final int index = i;
            answerButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedAnswerIndex = index;
                    resetButtonColors();
                    // Do not change color on selection
                }
            });
        }

        // Set onClickListener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                submitButton.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
            }
        });

        // Set onClickListener for the next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextQuestion();
                submitButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.GONE);
            }
        });
    }

    // Load the current question
    private void loadQuestion() {
        if (currentQuestionIndex < quizQuestions.size()) {
            Question question = quizQuestions.get(currentQuestionIndex);
            questionTextView.setText(question.getText());
            for (int i = 0; i < answerButtons.length; i++) {
                answerButtons[i].setText(question.getAnswers()[i]);
                answerButtons[i].setBackgroundColor(Color.LTGRAY);
            }
            selectedAnswerIndex = -1;  // Reset selected answer index
        } else {
            showFinalScore();
        }
    }

    // Check the selected answer and highlight the correct/incorrect answers
    private void checkAnswer() {
        if (selectedAnswerIndex == -1) {
            // No answer selected
            return;
        }

        Question question = quizQuestions.get(currentQuestionIndex);
        if (selectedAnswerIndex == question.getCorrectAnswerIndex()) {
            correctAnswers++;
            answerButtons[selectedAnswerIndex].setBackgroundColor(Color.GREEN);
        } else {
            answerButtons[selectedAnswerIndex].setBackgroundColor(Color.RED);
            answerButtons[question.getCorrectAnswerIndex()].setBackgroundColor(Color.GREEN);
        }
    }

    // Update the progress bar
    private void updateProgress() {
        int progress = ((currentQuestionIndex) * 100) / quizQuestions.size();
        progressBar.setProgress(progress);
        progressTextView.setText((currentQuestionIndex + 1) + "/" + quizQuestions.size());
    }

    // Load the next question or show the final score if all questions are answered
    private void loadNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < quizQuestions.size()) {
            loadQuestion();
            updateProgress();
        } else {
            showFinalScore();
        }
    }

    // Show the final score
    private void showFinalScore() {
        Intent intent = new Intent(this, FinalScoreActivity.class);
        intent.putExtra("SCORE", correctAnswers);
        intent.putExtra("USER_NAME", userName);
        startActivity(intent);
        finish();
    }

    // Reset the colors of the answer buttons
    private void resetButtonColors() {
        for (Button button : answerButtons) {
            button.setBackgroundColor(Color.LTGRAY);
        }
    }
}
