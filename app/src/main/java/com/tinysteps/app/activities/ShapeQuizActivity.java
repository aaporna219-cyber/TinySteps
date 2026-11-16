package com.tinysteps.app.activities;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tinysteps.app.R;

public class ShapeQuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvScore, tvPicture;
    private TextView tvQuestionText, tvFeedback;
    private TextView tvFinalScore, tvResultMessage;
    private TextView btnBack;

    private Button[] optionButtons;
    private Button btnNext, btnPlayAgain;

    private LinearLayout quizContent, resultContainer;

    private final String[] pictures = {
            "🔵", "🟦", "🔺", "⭐", "❤️", "▭"
    };

    private final String[] questions = {
            "What is this shape?",
            "What is this shape?",
            "What is this shape?",
            "What is this shape?",
            "What is this shape?",
            "What is this shape?"
    };

    private final String[][] options = {
            {"Circle", "Square", "Triangle", "Star"},
            {"Triangle", "Square", "Circle", "Heart"},
            {"Circle", "Rectangle", "Triangle", "Star"},
            {"Heart", "Circle", "Star", "Square"},
            {"Square", "Heart", "Triangle", "Circle"},
            {"Circle", "Star", "Rectangle", "Square"}
    };

    private final int[] correctAnswers = {0, 1, 2, 2, 1, 2};

    private int currentQuestion = 0;
    private int score = 0;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_quiz);

        initializeViews();
        setupClickListeners();
        loadQuestion();
    }

    private void initializeViews() {

        btnBack = findViewById(R.id.btnShapeQuizBack);

        tvQuestion = findViewById(R.id.tvShapeQuestion);
        tvScore = findViewById(R.id.tvShapeScore);
        tvPicture = findViewById(R.id.tvShapePicture);
        tvQuestionText = findViewById(R.id.tvShapeQuestionText);
        tvFeedback = findViewById(R.id.tvShapeFeedback);

        tvFinalScore = findViewById(R.id.tvShapeFinalScore);
        tvResultMessage = findViewById(R.id.tvShapeResultMessage);

        quizContent = findViewById(R.id.shapeQuizContent);
        resultContainer = findViewById(R.id.shapeResultContainer);

        optionButtons = new Button[]{
                findViewById(R.id.btnShapeOption1),
                findViewById(R.id.btnShapeOption2),
                findViewById(R.id.btnShapeOption3),
                findViewById(R.id.btnShapeOption4)
        };

        btnNext = findViewById(R.id.btnShapeNext);
        btnPlayAgain = findViewById(R.id.btnShapePlayAgain);
    }

    private void setupClickListeners() {

        btnBack.setOnClickListener(v -> finish());

        for (int i = 0; i < optionButtons.length; i++) {

            final int selectedOption = i;

            optionButtons[i].setOnClickListener(v ->
                    checkAnswer(selectedOption)
            );
        }

        btnNext.setOnClickListener(v -> {

            if (currentQuestion < questions.length - 1) {
                currentQuestion++;
                loadQuestion();
            } else {
                showResult();
            }
        });

        btnPlayAgain.setOnClickListener(v -> restartQuiz());
    }

    private void loadQuestion() {

        answered = false;

        tvQuestion.setText(
                "Question " + (currentQuestion + 1) +
                        " of " + questions.length
        );

        tvScore.setText("⭐ Score: " + score);

        tvPicture.setText(pictures[currentQuestion]);
        tvQuestionText.setText(questions[currentQuestion]);
        tvFeedback.setText("Choose the correct shape! 😊");

        btnNext.setVisibility(View.GONE);

        for (int i = 0; i < optionButtons.length; i++) {

            optionButtons[i].setText(options[currentQuestion][i]);
            optionButtons[i].setEnabled(true);

            setButtonBackground(optionButtons[i], Color.WHITE);
        }
    }

    private void checkAnswer(int selectedOption) {

        if (answered) {
            return;
        }

        answered = true;

        int correctOption = correctAnswers[currentQuestion];

        for (Button button : optionButtons) {
            button.setEnabled(false);
        }

        if (selectedOption == correctOption) {

            score++;

            tvFeedback.setText("Correct! Excellent! 🎉");

            setButtonBackground(
                    optionButtons[selectedOption],
                    Color.rgb(119, 220, 192)
            );

        } else {

            tvFeedback.setText(
                    "Oops! Correct answer: " +
                            options[currentQuestion][correctOption] +
                            " 😊"
            );

            setButtonBackground(
                    optionButtons[selectedOption],
                    Color.rgb(255, 180, 154)
            );

            setButtonBackground(
                    optionButtons[correctOption],
                    Color.rgb(119, 220, 192)
            );
        }

        tvScore.setText("⭐ Score: " + score);

        if (currentQuestion == questions.length - 1) {
            btnNext.setText("See Result 🎉");
        } else {
            btnNext.setText("Next Question ➜");
        }

        btnNext.setVisibility(View.VISIBLE);
    }

    private void showResult() {

        quizContent.setVisibility(View.GONE);
        resultContainer.setVisibility(View.VISIBLE);

        tvFinalScore.setText(score + " / " + questions.length);

        if (score == 6) {

            tvResultMessage.setText(
                    "Amazing! You are a Shape Star! 🏆"
            );

        } else if (score >= 4) {

            tvResultMessage.setText(
                    "Great job! Keep learning! ⭐"
            );

        } else {

            tvResultMessage.setText(
                    "Good try! Practice makes you better! 💖"
            );
        }
    }

    private void restartQuiz() {

        currentQuestion = 0;
        score = 0;
        answered = false;

        resultContainer.setVisibility(View.GONE);
        quizContent.setVisibility(View.VISIBLE);

        loadQuestion();
    }

    private void setButtonBackground(Button button, int color) {

        GradientDrawable background = new GradientDrawable();

        background.setColor(color);
        background.setCornerRadius(18f);
        background.setStroke(
                2,
                Color.rgb(235, 230, 245)
        );

        button.setBackground(background);
    }
}
