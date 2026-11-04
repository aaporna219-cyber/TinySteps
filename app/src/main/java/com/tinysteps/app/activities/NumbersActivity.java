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

public class NumbersActivity extends AppCompatActivity {

    private GridLayout numberGrid;
    private TextView tvSelectedNumber;
    private TextView tvEnglishName;
    private TextView tvBanglaName;
    private Button btnSpeak;

    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    private final String[] englishNames = {
            "One", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
            "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty"
    };

    private final String[] banglaNumbers = {
            "এক", "দুই", "তিন", "চার", "পাঁচ",
            "ছয়", "সাত", "আট", "নয়", "দশ",
            "এগারো", "বারো", "তেরো", "চৌদ্দ", "পনেরো",
            "ষোলো", "সতেরো", "আঠারো", "উনিশ", "বিশ"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        numberGrid = findViewById(R.id.numberGrid);
        tvSelectedNumber = findViewById(R.id.tvSelectedNumber);
        tvEnglishName = findViewById(R.id.tvEnglishName);
        tvBanglaName = findViewById(R.id.tvBanglaName);
        btnSpeak = findViewById(R.id.btnSpeak);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        createNumberGrid();
        showNumber(0);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);

                ttsReady = result != TextToSpeech.LANG_MISSING_DATA
                        && result != TextToSpeech.LANG_NOT_SUPPORTED;
            }
        });

        btnSpeak.setOnClickListener(v -> speakNumber());
    }

    private void createNumberGrid() {
        numberGrid.removeAllViews();
        numberGrid.setColumnCount(4);

        for (int i = 0; i < 20; i++) {
            final int position = i;

            TextView tile = new TextView(this);
            tile.setText(String.valueOf(i + 1));
            tile.setTextSize(23);
            tile.setTextColor(getColor(R.color.ts_text));
            tile.setGravity(Gravity.CENTER);
            tile.setBackgroundResource(R.drawable.bg_card_mint);
            tile.setClickable(true);
            tile.setFocusable(true);

            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();

            params.width = 0;
            params.height = dpToPx(65);
            params.columnSpec =
                    GridLayout.spec(GridLayout.UNDEFINED, 1f);

            params.setMargins(
                    dpToPx(5), dpToPx(5),
                    dpToPx(5), dpToPx(5)
            );

            tile.setLayoutParams(params);
            tile.setOnClickListener(v -> showNumber(position));

            numberGrid.addView(tile);
        }
    }

    private void showNumber(int position) {
        tvSelectedNumber.setText(String.valueOf(position + 1));
        tvEnglishName.setText(englishNames[position]);
        tvBanglaName.setText(banglaNumbers[position]);
    }

    private void speakNumber() {
        if (textToSpeech != null && ttsReady) {
            textToSpeech.speak(
                    englishNames[Integer.parseInt(
                            tvSelectedNumber.getText().toString()
                    ) - 1],
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "number_speech"
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
