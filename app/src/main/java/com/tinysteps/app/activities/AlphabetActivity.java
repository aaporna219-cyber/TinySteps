package com.tinysteps.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tinysteps.app.R;

public class AlphabetActivity extends AppCompatActivity {

    private TextView btnBack;
    private Button btnEnglish;
    private Button btnBangla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        // Connect XML views with Java
        btnBack = findViewById(R.id.btnBack);
        btnEnglish = findViewById(R.id.btnEnglish);
        btnBangla = findViewById(R.id.btnBangla);

        // Back button
        btnBack.setOnClickListener(v -> finish());

        // English Alphabet
        btnEnglish.setOnClickListener(v -> {
            Intent intent = new Intent(
                    AlphabetActivity.this,
                    EnglishAlphabetActivity.class
            );
            startActivity(intent);
        });

        // Bangla Alphabet
        btnBangla.setOnClickListener(v -> {
            Intent intent = new Intent(
                    AlphabetActivity.this,
                    BanglaAlphabetActivity.class
            );
            startActivity(intent);
        });
    }
}
