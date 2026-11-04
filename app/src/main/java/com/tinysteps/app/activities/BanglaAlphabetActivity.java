package com.tinysteps.app.activities;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tinysteps.app.R;

import java.util.Locale;

public class BanglaAlphabetActivity extends AppCompatActivity {

    private GridLayout alphabetGrid;
    private TextView tvSelectedLetter, tvWord, tvPicture;
    private Button btnSpeak;

    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    private final String[] letters = {
            "অ", "আ", "ই", "ঈ", "উ", "ঊ",
            "ঋ", "এ", "ঐ", "ও", "ঔ"
    };

    private final String[] words = {
            "অজগর", "আম", "ইলিশ", "ঈগল", "উট",
            "ঊষা", "ঋষি", "একতারা", "ঐক্য", "ওল", "ঔষধ"
    };

    private final String[] pictures = {
            "🐍", "🥭", "🐟", "🦅", "🐪",
            "🌅", "🧘", "🎸", "🤝", "🥔", "💊"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangla_alphabet);

        alphabetGrid = findViewById(R.id.alphabetGrid);
        tvSelectedLetter = findViewById(R.id.tvSelectedLetter);
        tvWord = findViewById(R.id.tvWord);
        tvPicture = findViewById(R.id.tvPicture);
        btnSpeak = findViewById(R.id.btnSpeak);
        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        createAlphabetGrid();
        showLetter(0);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(
                        Locale.forLanguageTag("bn-BD")
                );

                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    ttsReady = false;
                    Toast.makeText(
                            this,
                            "Bangla voice is not available on this emulator.",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    ttsReady = true;
                }
            } else {
                ttsReady = false;
                Toast.makeText(
                        this,
                        "Text-to-Speech could not start.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        btnSpeak.setOnClickListener(v -> speakSelectedLetter());
    }

    private void createAlphabetGrid() {
        alphabetGrid.removeAllViews();
        alphabetGrid.setColumnCount(4);

        for (int i = 0; i < letters.length; i++) {
            final int position = i;

            TextView letterTile = new TextView(this);
            letterTile.setText(letters[i]);
            letterTile.setTextSize(26);
            letterTile.setTextColor(getColor(R.color.ts_text));
            letterTile.setGravity(Gravity.CENTER);
            letterTile.setBackgroundResource(R.drawable.bg_card_mint);
            letterTile.setClickable(true);
            letterTile.setFocusable(true);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = dpToPx(68);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(dpToPx(5), dpToPx(5),
                    dpToPx(5), dpToPx(5));

            letterTile.setLayoutParams(params);

            letterTile.setOnClickListener(v -> showLetter(position));

            alphabetGrid.addView(letterTile);
        }
    }

    private void showLetter(int position) {
        tvSelectedLetter.setText(letters[position]);
        tvWord.setText(letters[position] + " — " + words[position]);
        tvPicture.setText(pictures[position]);
    }

    private void speakSelectedLetter() {
        if (textToSpeech != null && ttsReady) {
            String selectedText = tvSelectedLetter.getText().toString()
                    + "। "
                    + tvWord.getText().toString();

            textToSpeech.speak(
                    selectedText,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "bangla_alphabet"
            );
        } else {
            Toast.makeText(
                    this,
                    "Bangla voice is not ready or not supported.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private int dpToPx(int dp) {
        return Math.round(
                dp * getResources().getDisplayMetrics().density
        );
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