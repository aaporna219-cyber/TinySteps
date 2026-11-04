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

public class ShapesActivity extends AppCompatActivity {

    private GridLayout shapeGrid;
    private TextView tvSelectedShape;
    private TextView tvEnglishName;
    private TextView tvBanglaName;
    private Button btnSpeak;

    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    private final String[] shapes = {
            "●", "■", "▲", "▰", "★", "♥"
    };

    private final String[] englishNames = {
            "Circle", "Square", "Triangle",
            "Rectangle", "Star", "Heart"
    };

    private final String[] banglaNames = {
            "বৃত্ত", "বর্গক্ষেত্র", "ত্রিভুজ",
            "আয়তক্ষেত্র", "তারা", "হৃদয়"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes);

        shapeGrid = findViewById(R.id.shapeGrid);
        tvSelectedShape = findViewById(R.id.tvSelectedShape);
        tvEnglishName = findViewById(R.id.tvEnglishName);
        tvBanglaName = findViewById(R.id.tvBanglaName);
        btnSpeak = findViewById(R.id.btnSpeak);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        createShapeGrid();
        showShape(0);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);

                ttsReady = result != TextToSpeech.LANG_MISSING_DATA
                        && result != TextToSpeech.LANG_NOT_SUPPORTED;
            }
        });

        btnSpeak.setOnClickListener(v -> speakShape());
    }

    private void createShapeGrid() {
        shapeGrid.removeAllViews();
        shapeGrid.setColumnCount(2);

        for (int i = 0; i < shapes.length; i++) {
            final int position = i;

            TextView tile = new TextView(this);

            tile.setText(shapes[i] + "\n" + englishNames[i]);
            tile.setTextSize(21);
            tile.setTextColor(getColor(R.color.ts_primary));
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
            tile.setOnClickListener(v -> showShape(position));

            shapeGrid.addView(tile);
        }
    }

    private void showShape(int position) {
        tvSelectedShape.setText(shapes[position]);
        tvEnglishName.setText(englishNames[position]);
        tvBanglaName.setText(banglaNames[position]);
    }

    private void speakShape() {
        if (textToSpeech != null && ttsReady) {
            textToSpeech.speak(
                    tvEnglishName.getText().toString(),
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "shape_speech"
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
