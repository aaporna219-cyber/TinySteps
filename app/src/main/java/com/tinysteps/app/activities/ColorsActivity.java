
package com.tinysteps.app.activities;

import android.content.Intent;
import android.graphics.Color;
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

public class ColorsActivity extends AppCompatActivity {

    private GridLayout colorGrid;
    private TextView tvSelectedColor;
    private TextView tvEnglishName;
    private TextView tvBanglaName;
    private Button btnSpeak;

    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    private final String[] colorNames = {
            "Red", "Blue", "Green", "Yellow",
            "Orange", "Purple", "Pink", "Black"
    };

    private final String[] banglaNames = {
            "লাল", "নীল", "সবুজ", "হলুদ",
            "কমলা", "বেগুনি", "গোলাপি", "কালো"
    };

    private final String[] colorCodes = {
            "#F44336", "#2196F3", "#4CAF50", "#FFEB3B",
            "#FF9800", "#9C27B0", "#E91E63", "#212121"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        colorGrid = findViewById(R.id.colorGrid);
        tvSelectedColor = findViewById(R.id.tvSelectedColor);
        tvEnglishName = findViewById(R.id.tvEnglishName);
        tvBanglaName = findViewById(R.id.tvBanglaName);
        btnSpeak = findViewById(R.id.btnSpeak);

        Button btnBack = findViewById(R.id.btnBack);
        Button btnShapes = findViewById(R.id.btnShapes);

        btnBack.setOnClickListener(v -> finish());

        btnShapes.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ColorsActivity.this,
                    ShapesActivity.class
            );
            startActivity(intent);
        });

        createColorGrid();
        showColor(0);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);

                ttsReady = result != TextToSpeech.LANG_MISSING_DATA
                        && result != TextToSpeech.LANG_NOT_SUPPORTED;
            }
        });

        btnSpeak.setOnClickListener(v -> speakColor());
    }

    private void createColorGrid() {
        colorGrid.removeAllViews();
        colorGrid.setColumnCount(2);

        for (int i = 0; i < colorNames.length; i++) {
            final int position = i;

            TextView tile = new TextView(this);

            tile.setText("●  " + colorNames[i]);
            tile.setTextSize(20);
            tile.setTextColor(Color.parseColor(colorCodes[i]));
            tile.setGravity(Gravity.CENTER);
            tile.setBackgroundResource(R.drawable.bg_card_white);
            tile.setClickable(true);
            tile.setFocusable(true);

            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();

            params.width = 0;
            params.height = dpToPx(85);
            params.columnSpec =
                    GridLayout.spec(GridLayout.UNDEFINED, 1f);

            params.setMargins(
                    dpToPx(6), dpToPx(6),
                    dpToPx(6), dpToPx(6)
            );

            tile.setLayoutParams(params);
            tile.setOnClickListener(v -> showColor(position));

            colorGrid.addView(tile);
        }
    }

    private void showColor(int position) {
        tvSelectedColor.setText("●");
        tvSelectedColor.setTextColor(
                Color.parseColor(colorCodes[position])
        );

        tvEnglishName.setText(colorNames[position]);
        tvBanglaName.setText(banglaNames[position]);
    }

    private void speakColor() {
        if (textToSpeech != null && ttsReady) {
            textToSpeech.speak(
                    tvEnglishName.getText().toString(),
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "color_speech"
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
