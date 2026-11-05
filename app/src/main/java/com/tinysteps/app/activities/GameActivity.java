package com.tinysteps.app.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tinysteps.app.R;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {

    private LinearLayout pictureContainer;
    private LinearLayout wordContainer;

    private TextView tvScore;
    private TextView tvAttempts;
    private TextView tvFeedback;

    private Button btnPlayAgain;
    private TextView btnBack;

    private final String[] animalNames = {
            "Elephant", "Lion", "Rabbit", "Monkey"
    };

    private final String[] animalEmojis = {
            "🐘", "🦁", "🐰", "🐵"
    };

    private int selectedPicture = -1;
    private int selectedWord = -1;

    private int score = 0;
    private int attempts = 0;
    private int matchedPairs = 0;

    private final boolean[] matchedPictures = new boolean[4];
    private final boolean[] matchedWords = new boolean[4];

    private final ArrayList<Integer> shuffledWords = new ArrayList<>();

    private final ArrayList<TextView> pictureCards = new ArrayList<>();
    private final ArrayList<TextView> wordCards = new ArrayList<>();

    private boolean gameFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        pictureContainer = findViewById(R.id.pictureContainer);
        wordContainer = findViewById(R.id.wordContainer);

        tvScore = findViewById(R.id.tvScore);
        tvAttempts = findViewById(R.id.tvAttempts);
        tvFeedback = findViewById(R.id.tvFeedback);

        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        btnPlayAgain.setOnClickListener(v -> startNewGame());

        startNewGame();
    }

    private void startNewGame() {

        score = 0;
        attempts = 0;
        matchedPairs = 0;

        selectedPicture = -1;
        selectedWord = -1;
        gameFinished = false;

        pictureContainer.removeAllViews();
        wordContainer.removeAllViews();

        pictureCards.clear();
        wordCards.clear();
        shuffledWords.clear();

        for (int i = 0; i < 4; i++) {
            matchedPictures[i] = false;
            matchedWords[i] = false;
            shuffledWords.add(i);
        }

        Collections.shuffle(shuffledWords);

        tvFeedback.setText("Match each animal with its name! 🐾");
        btnPlayAgain.setVisibility(View.GONE);

        updateCounters();
        createCards();
    }

    private void createCards() {

        for (int i = 0; i < 4; i++) {

            final int animalIndex = i;

            TextView pictureCard = createCard(
                    animalEmojis[i],
                    "#E5F7F1"
            );

            pictureCard.setTextSize(34);
            pictureCard.setOnClickListener(v ->
                    selectPicture(animalIndex)
            );

            pictureContainer.addView(pictureCard);
            pictureCards.add(pictureCard);
        }

        for (int i = 0; i < 4; i++) {

            final int wordIndex = shuffledWords.get(i);

            TextView wordCard = createCard(
                    animalNames[wordIndex],
                    "#FFF0D8"
            );

            wordCard.setTextSize(17);
            wordCard.setOnClickListener(v ->
                    selectWord(wordIndex)
            );

            wordContainer.addView(wordCard);
            wordCards.add(wordCard);
        }
    }

    private TextView createCard(String text, String backgroundColor) {

        TextView card = new TextView(this);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        85
                );

        params.setMargins(4, 7, 4, 7);

        card.setLayoutParams(params);
        card.setText(text);
        card.setTextColor(Color.rgb(48, 48, 74));
        card.setGravity(Gravity.CENTER);
        card.setTypeface(null, Typeface.BOLD);

        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.parseColor(backgroundColor));
        background.setCornerRadius(20);

        card.setBackground(background);
        card.setClickable(true);
        card.setFocusable(true);

        return card;
    }

    private void selectPicture(int index) {

        if (gameFinished || matchedPictures[index]) {
            return;
        }

        selectedPicture = index;

        pictureCards.get(index).setBackgroundColor(
                Color.rgb(190, 180, 255)
        );

        checkMatch();
    }

    private void selectWord(int index) {

        if (gameFinished || matchedWords[index]) {
            return;
        }

        selectedWord = index;

        int cardPosition = shuffledWords.indexOf(index);

        wordCards.get(cardPosition).setBackgroundColor(
                Color.rgb(190, 180, 255)
        );

        checkMatch();
    }

    private void checkMatch() {

        if (selectedPicture == -1 || selectedWord == -1) {
            return;
        }

        attempts++;

        if (selectedPicture == selectedWord) {

            score++;
            matchedPairs++;

            matchedPictures[selectedPicture] = true;
            matchedWords[selectedWord] = true;

            pictureCards.get(selectedPicture).setBackgroundColor(
                    Color.rgb(170, 235, 200)
            );

            int wordPosition = shuffledWords.indexOf(selectedWord);

            wordCards.get(wordPosition).setBackgroundColor(
                    Color.rgb(170, 235, 200)
            );

            pictureCards.get(selectedPicture).setEnabled(false);
            wordCards.get(wordPosition).setEnabled(false);

            tvFeedback.setText("Great job! Correct match! ⭐");

            Toast.makeText(
                    this,
                    "Correct! 🎉",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            tvFeedback.setText("Oops! Try another match. 😊");

            resetUnmatchedCardColors();
        }

        updateCounters();

        selectedPicture = -1;
        selectedWord = -1;

        if (matchedPairs == 4) {
            finishGame();
        }
    }

    private void resetUnmatchedCardColors() {

        for (int i = 0; i < 4; i++) {

            if (!matchedPictures[i]) {
                pictureCards.get(i).setBackgroundColor(
                        Color.rgb(229, 247, 241)
                );
            }

            if (!matchedWords[i]) {

                int position = shuffledWords.indexOf(i);

                wordCards.get(position).setBackgroundColor(
                        Color.rgb(255, 240, 216)
                );
            }
        }
    }

    private void updateCounters() {

        tvScore.setText("⭐ Score: " + score + "/4");
        tvAttempts.setText("🎯 Attempts: " + attempts);
    }

    private void finishGame() {

        gameFinished = true;

        tvFeedback.setText(
                "Amazing! You matched all animals! 🏆"
        );

        btnPlayAgain.setVisibility(View.VISIBLE);
    }
}
