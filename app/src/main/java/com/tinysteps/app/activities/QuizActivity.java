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

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestionNumber, tvScore, tvAnimal,
            tvQuestion, tvFeedback, tvFinalScore, tvResultMessage;

    private Button btnOption1, btnOption2, btnOption3, btnOption4;
    private Button btnNext, btnPlayAgain;
    private TextView btnBack;

    private LinearLayout quizContent, resultContainer;

    private int currentQuestion = 0;
    private int score = 0;
    private boolean answered = false;

    private final String[] animals = {
            "🐘", "🦁", "🐰", "🐵", "🦒"
    };

    private final String[] questions = {
            "What is this animal?",
            "Which animal is called the King of the Jungle?",
            "Which animal has long ears?",
            "Which animal loves bananas?",
            "Which animal has a long neck?"
    };

    private final String[][] options = {
            {"Elephant", "Lion", "Rabbit", "Monkey"},
            {"Rabbit", "Lion", "Elephant", "Giraffe"},
            {"Monkey", "Giraffe", "Rabbit", "Lion"},
            {"Elephant", "Monkey", "Lion", "Rabbit"},
            {"Lion", "Rabbit", "Giraffe", "Elephant"}
    };

    private final int[] correctAnswers = {0, 1, 2, 1, 2};

    private Button[] optionButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initializeViews();

        optionButtons = new Button[]{
                btnOption1, btnOption2, btnOption3, btnOption4
        };

        btnBack.setOnClickListener(v -> finish());
        btnNext.setOnClickListener(v -> showNextQuestion());
        btnPlayAgain.setOnClickListener(v -> restartQuiz());

        loadQuestion();
    }

    private void initializeViews() {

        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvScore = findViewById(R.id.tvQuizScore);
        tvAnimal = findViewById(R.id.tvQuizAnimal);
        tvQuestion = findViewById(R.id.tvQuizQuestion);
        tvFeedback = findViewById(R.id.tvQuizFeedback);

        tvFinalScore = findViewById(R.id.tvFinalScore);
        tvResultMessage = findViewById(R.id.tvResultMessage);

        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);

        btnNext = findViewById(R.id.btnNextQuestion);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnBack = findViewById(R.id.btnQuizBack);

        quizContent = findViewById(R.id.quizContent);
        resultContainer = findViewById(R.id.resultContainer);
    }

    private void loadQuestion() {

        answered = false;

        tvQuestionNumber.setText(
                "Question " + (currentQuestion + 1) + " of 5"
        );

        tvScore.setText("⭐ Score: " + score);

        tvAnimal.setText(animals[currentQuestion]);
        tvQuestion.setText(questions[currentQuestion]);

        tvFeedback.setText("Choose the correct answer! 😊");

        btnNext.setVisibility(View.GONE);

        for (int i = 0; i < optionButtons.length; i++) {

            Button button = optionButtons[i];

            button.setText(options[currentQuestion][i]);
            button.setEnabled(true);
            button.setBackground(createBackground("#FFFFFF"));

            final int selectedAnswer = i;

            button.setOnClickListener(v ->
                    checkAnswer(selectedAnswer)
            );
        }
    }

    private void checkAnswer(int selectedAnswer) {

        if (answered) {
            return;
        }

        answered = true;

        int correctAnswer = correctAnswers[currentQuestion];

        for (Button button : optionButtons) {
            button.setEnabled(false);
        }

        if (selectedAnswer == correctAnswer) {

            score++;

            optionButtons[selectedAnswer].setBackground(
                    createBackground("#B9EBCB")
            );

            tvFeedback.setText("Excellent! Correct answer! ⭐");

        } else {

            optionButtons[selectedAnswer].setBackground(
                    createBackground("#FFD6D6")
            );

            optionButtons[correctAnswer].setBackground(
                    createBackground("#B9EBCB")
            );

            tvFeedback.setText(
                    "Good try! The correct answer is " +
                            options[currentQuestion][correctAnswer] + ". 😊"
            );
        }

        tvScore.setText("⭐ Score: " + score);

        btnNext.setVisibility(View.VISIBLE);

        if (currentQuestion == questions.length - 1) {
            btnNext.setText("See My Result 🏆");
        } else {
            btnNext.setText("Next Question ➜");
        }
    }

    private void showNextQuestion() {

        if (!answered) {
            return;
        }

        currentQuestion++;

        if (currentQuestion < questions.length) {
            loadQuestion();
        } else {
            showResult();
        }
    }

    private void showResult() {

        quizContent.setVisibility(View.GONE);
        resultContainer.setVisibility(View.VISIBLE);

        tvFinalScore.setText(score + " / 5");

        if (score == 5) {
            tvResultMessage.setText(
                    "Amazing! You are an Animal Expert! 🏆"
            );
        } else if (score >= 3) {
            tvResultMessage.setText(
                    "Great job! Keep learning and growing! ⭐"
            );
        } else {
            tvResultMessage.setText(
                    "Good try! Practice makes you better! 💛"
            );
        }
    }

    private void restartQuiz() {

        currentQuestion = 0;
        score = 0;

        resultContainer.setVisibility(View.GONE);
        quizContent.setVisibility(View.VISIBLE);

        btnNext.setText("Next Question ➜");

        loadQuestion();
    }

    private GradientDrawable createBackground(String color) {

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor(color));
        drawable.setCornerRadius(dpToPx(15));
        drawable.setStroke(dpToPx(1), Color.parseColor("#E5E5E5"));

        return drawable;
    }

    private int dpToPx(int dp) {

        return (int) (dp * getResources()
                .getDisplayMetrics().density + 0.5f);
    }
}