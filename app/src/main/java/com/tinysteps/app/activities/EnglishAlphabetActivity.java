package com.tinysteps.app.activities;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tinysteps.app.R;

import java.util.Locale;

public class EnglishAlphabetActivity extends AppCompatActivity {

    private GridLayout alphabetGrid;
    private TextView tvSelectedLetter;
    private TextView tvWord;
    private TextView tvPicture;
    private Button btnSpeak;

    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    private String selectedWord = "Apple";

    private final String[] letters = {
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"
    };

    private final String[] words = {
            "Apple", "Ball", "Cat", "Dog", "Elephant",
            "Fish", "Giraffe", "House", "Ice Cream",
            "Jug", "Kite", "Lion", "Mango", "Nest",
            "Orange", "Parrot", "Queen", "Rabbit",
            "Sun", "Tiger", "Umbrella", "Van",
            "Watch", "Xylophone", "Yak", "Zebra"
    };

    private final String[] pictures = {
            "🍎", "⚽", "🐱", "🐶", "🐘",
            "🐟", "🦒", "🏠", "🍦", "🫗",
            "🪁", "🦁", "🥭", "🪺", "🍊",
            "🦜", "👑", "🐰", "☀️", "🐯",
            "☂️", "🚐", "⌚", "🎵", "🐂", "🦓"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_alphabet);

        alphabetGrid = findViewById(R.id.alphabetGrid);
        tvSelectedLetter = findViewById(R.id.tvSelectedLetter);
        tvWord = findViewById(R.id.tvWord);
        tvPicture = findViewById(R.id.tvPicture);
        btnSpeak = findViewById(R.id.btnSpeak);

        TextView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        initializeTextToSpeech();

        createAlphabetGrid();

        showLetter(0);

        btnSpeak.setOnClickListener(v -> speakSelectedWord());
    }

    private void initializeTextToSpeech() {

        textToSpeech = new TextToSpeech(
                this,
                status -> {

                    if (status == TextToSpeech.SUCCESS) {

                        int result = textToSpeech.setLanguage(Locale.US);

                        ttsReady = result != TextToSpeech.LANG_MISSING_DATA
                                && result != TextToSpeech.LANG_NOT_SUPPORTED;

                        if (!ttsReady) {
                            Toast.makeText(
                                    this,
                                    "English voice is not available.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    } else {
                        Toast.makeText(
                                this,
                                "Text to Speech initialization failed.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }

    private void createAlphabetGrid() {

        for (int i = 0; i < letters.length; i++) {

            final int index = i;

            TextView letterView = new TextView(this);

            letterView.setText(letters[i]);
            letterView.setTextSize(24);
            letterView.setTextColor(
                    getResources().getColor(R.color.ts_text)
            );
            letterView.setGravity(Gravity.CENTER);
            letterView.setBackgroundResource(R.drawable.bg_card_mint);
            letterView.setClickable(true);
            letterView.setFocusable(true);

            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();

            params.width = 0;
            params.height = 75;

            params.columnSpec =
                    GridLayout.spec(
                            GridLayout.UNDEFINED,
                            1f
                    );

            params.setMargins(6, 6, 6, 6);

            letterView.setLayoutParams(params);

            letterView.setOnClickListener(v -> showLetter(index));

            alphabetGrid.addView(letterView);
        }
    }

    private void showLetter(int index) {

        tvSelectedLetter.setText(letters[index]);

        tvWord.setText(letters[index] + " for " + words[index]);

        tvPicture.setText(pictures[index]);

        selectedWord = words[index];
    }

    private void speakSelectedWord() {

        if (textToSpeech != null && ttsReady) {

            textToSpeech.speak(
                    letters[getSelectedIndex()] + " for " + selectedWord,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "alphabet_speech"
            );

        } else {
            Toast.makeText(
                    this,
                    "Voice is still loading. Please try again.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private int getSelectedIndex() {

        String currentLetter = tvSelectedLetter.getText().toString();

        for (int i = 0; i < letters.length; i++) {

            if (letters[i].equals(currentLetter)) {
                return i;
            }
        }

        return 0;
    }

    @Override
    protected void onDestroy() {

        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }
}
