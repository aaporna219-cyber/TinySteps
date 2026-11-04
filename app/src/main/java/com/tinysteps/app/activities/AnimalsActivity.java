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

public class AnimalsActivity extends AppCompatActivity {

    private GridLayout animalGrid;
    private TextView tvSelectedAnimal;
    private TextView tvEnglishName;
    private TextView tvBanglaName;
    private Button btnSpeak;

    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    private final String[] animals = {
            "🐘", "🦁", "🐱", "🐶", "🐰",
            "🦒", "🐒", "🐼", "🐯", "🐻"
    };

    private final String[] englishNames = {
            "Elephant", "Lion", "Cat", "Dog", "Rabbit",
            "Giraffe", "Monkey", "Panda", "Tiger", "Bear"
    };

    private final String[] banglaNames = {
            "হাতি", "সিংহ", "বিড়াল", "কুকুর", "খরগোশ",
            "জিরাফ", "বানর", "পান্ডা", "বাঘ", "ভালুক"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);

        animalGrid = findViewById(R.id.animalGrid);
        tvSelectedAnimal = findViewById(R.id.tvSelectedAnimal);
        tvEnglishName = findViewById(R.id.tvEnglishName);
        tvBanglaName = findViewById(R.id.tvBanglaName);
        btnSpeak = findViewById(R.id.btnSpeak);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        createAnimalGrid();
        showAnimal(0);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);

                ttsReady = result != TextToSpeech.LANG_MISSING_DATA
                        && result != TextToSpeech.LANG_NOT_SUPPORTED;
            }
        });

        btnSpeak.setOnClickListener(v -> speakAnimal());
    }

    private void createAnimalGrid() {
        animalGrid.removeAllViews();
        animalGrid.setColumnCount(2);

        for (int i = 0; i < animals.length; i++) {
            final int position = i;

            TextView tile = new TextView(this);

            tile.setText(animals[i] + "\n" + englishNames[i]);
            tile.setTextSize(18);
            tile.setTextColor(getColor(R.color.ts_text));
            tile.setGravity(Gravity.CENTER);
            tile.setBackgroundResource(R.drawable.bg_card_mint);
            tile.setClickable(true);
            tile.setFocusable(true);

            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();

            params.width = 0;
            params.height = dpToPx(105);
            params.columnSpec =
                    GridLayout.spec(GridLayout.UNDEFINED, 1f);

            params.setMargins(
                    dpToPx(6), dpToPx(6),
                    dpToPx(6), dpToPx(6)
            );

            tile.setLayoutParams(params);
            tile.setOnClickListener(v -> showAnimal(position));

            animalGrid.addView(tile);
        }
    }

    private void showAnimal(int position) {
        tvSelectedAnimal.setText(animals[position]);
        tvEnglishName.setText(englishNames[position]);
        tvBanglaName.setText(banglaNames[position]);
    }

    private void speakAnimal() {
        if (textToSpeech != null && ttsReady) {
            String name = tvEnglishName.getText().toString();

            textToSpeech.speak(
                    name,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "animal_speech"
            );
        } else {
            Toast.makeText(
                    this,
                    "English voice is not available.",
                    Toast.LENGTH_SHORT
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
