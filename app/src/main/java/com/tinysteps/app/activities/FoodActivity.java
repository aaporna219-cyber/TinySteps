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

public class FoodActivity extends AppCompatActivity {

    private GridLayout foodGrid;
    private TextView tvSelectedFood;
    private TextView tvEnglishName;
    private TextView tvBanglaName;
    private Button btnSpeak;
    private Button btnFruits;
    private Button btnVegetables;

    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    private boolean showingFruits = true;

    private final String[] fruitEmojis = {
            "🍎", "🍌", "🥭", "🍊", "🍇",
            "🍓", "🍍", "🍉", "🍐", "🍒"
    };

    private final String[] fruitEnglish = {
            "Apple", "Banana", "Mango", "Orange", "Grapes",
            "Strawberry", "Pineapple", "Watermelon",
            "Pear", "Cherry"
    };

    private final String[] fruitBangla = {
            "আপেল", "কলা", "আম", "কমলা", "আঙুর",
            "স্ট্রবেরি", "আনারস", "তরমুজ",
            "নাশপাতি", "চেরি"
    };

    private final String[] vegetableEmojis = {
            "🥕", "🍅", "🥔", "🥒", "🍆",
            "🥦", "🌽", "🧅", "🫑", "🥬"
    };

    private final String[] vegetableEnglish = {
            "Carrot", "Tomato", "Potato", "Cucumber",
            "Eggplant", "Broccoli", "Corn", "Onion",
            "Capsicum", "Cabbage"
    };

    private final String[] vegetableBangla = {
            "গাজর", "টমেটো", "আলু", "শসা",
            "বেগুন", "ব্রকলি", "ভুট্টা", "পেঁয়াজ",
            "ক্যাপসিকাম", "বাঁধাকপি"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        foodGrid = findViewById(R.id.foodGrid);
        tvSelectedFood = findViewById(R.id.tvSelectedFood);
        tvEnglishName = findViewById(R.id.tvEnglishName);
        tvBanglaName = findViewById(R.id.tvBanglaName);

        btnSpeak = findViewById(R.id.btnSpeak);
        btnFruits = findViewById(R.id.btnFruits);
        btnVegetables = findViewById(R.id.btnVegetables);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        btnFruits.setOnClickListener(v -> {
            showingFruits = true;
            createFoodGrid();
            showFood(0);
        });

        btnVegetables.setOnClickListener(v -> {
            showingFruits = false;
            createFoodGrid();
            showFood(0);
        });

        createFoodGrid();
        showFood(0);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);

                ttsReady = result != TextToSpeech.LANG_MISSING_DATA
                        && result != TextToSpeech.LANG_NOT_SUPPORTED;
            }
        });

        btnSpeak.setOnClickListener(v -> speakFood());
    }

    private void createFoodGrid() {
        foodGrid.removeAllViews();
        foodGrid.setColumnCount(2);

        String[] emojis = showingFruits
                ? fruitEmojis : vegetableEmojis;

        String[] names = showingFruits
                ? fruitEnglish : vegetableEnglish;

        for (int i = 0; i < emojis.length; i++) {
            final int position = i;

            TextView tile = new TextView(this);

            tile.setText(emojis[i] + "\n" + names[i]);
            tile.setTextSize(19);
            tile.setTextColor(getColor(R.color.ts_text));
            tile.setGravity(Gravity.CENTER);
            tile.setBackgroundResource(R.drawable.bg_card_white);
            tile.setClickable(true);
            tile.setFocusable(true);

            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();

            params.width = 0;
            params.height = dpToPx(100);

            params.columnSpec =
                    GridLayout.spec(GridLayout.UNDEFINED, 1f);

            params.setMargins(
                    dpToPx(6), dpToPx(6),
                    dpToPx(6), dpToPx(6)
            );

            tile.setLayoutParams(params);

            tile.setOnClickListener(v -> showFood(position));

            foodGrid.addView(tile);
        }
    }

    private void showFood(int position) {

        String[] emojis = showingFruits
                ? fruitEmojis : vegetableEmojis;

        String[] english = showingFruits
                ? fruitEnglish : vegetableEnglish;

        String[] bangla = showingFruits
                ? fruitBangla : vegetableBangla;

        tvSelectedFood.setText(emojis[position]);
        tvEnglishName.setText(english[position]);
        tvBanglaName.setText(bangla[position]);
    }

    private void speakFood() {
        if (textToSpeech != null && ttsReady) {
            textToSpeech.speak(
                    tvEnglishName.getText().toString(),
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "food_speech"
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
