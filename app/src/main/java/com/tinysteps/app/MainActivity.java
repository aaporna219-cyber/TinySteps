package com.tinysteps.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.tinysteps.app.activities.AlphabetActivity;
import com.tinysteps.app.activities.AnimalsActivity;
import com.tinysteps.app.activities.ColorsActivity;
import com.tinysteps.app.activities.FoodActivity;
import com.tinysteps.app.activities.GameActivity;
import com.tinysteps.app.activities.MemoryGameActivity;
import com.tinysteps.app.activities.NumbersActivity;
import com.tinysteps.app.activities.QuizActivity;
import com.tinysteps.app.activities.NumberQuizActivity;

public class MainActivity extends AppCompatActivity {

    private View cardAlphabets;
    private View cardNumbers;
    private View cardAnimals;
    private View cardColors;
    private View cardFood;
    private View cardGame;
    private View cardMemoryGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardAlphabets = findViewById(R.id.cardAlphabets);
        cardNumbers = findViewById(R.id.cardNumbers);
        cardAnimals = findViewById(R.id.cardAnimals);
        cardColors = findViewById(R.id.cardColors);
        cardFood = findViewById(R.id.cardFood);
        cardGame = findViewById(R.id.cardGame);
        cardMemoryGame = findViewById(R.id.cardMemoryGame);

        cardAlphabets.setOnClickListener(v ->
                startActivity(new Intent(this, AlphabetActivity.class)));

        cardNumbers.setOnClickListener(v ->
                startActivity(new Intent(this, NumbersActivity.class)));

        cardAnimals.setOnClickListener(v ->
                startActivity(new Intent(this, AnimalsActivity.class)));

        cardColors.setOnClickListener(v ->
                startActivity(new Intent(this, ColorsActivity.class)));

        cardFood.setOnClickListener(v ->
                startActivity(new Intent(this, FoodActivity.class)));

        cardGame.setOnClickListener(v -> showGamesMenu());

        cardMemoryGame.setOnClickListener(v ->
                startActivity(new Intent(this, MemoryGameActivity.class)));
    }

    private void showGamesMenu() {

        String[] games = {
                "Picture Matching Game 🧩",
                "Animal Quiz 🐾",
                "Number Quiz 🔢"
        };

        new AlertDialog.Builder(this)
                .setTitle("Choose a Game 🎮")
                .setItems(games, (dialog, which) -> {

                    if (which == 0) {
                        startActivity(
                                new Intent(this, GameActivity.class)
                        );
                    } else if (which == 1) {
                        startActivity(
                                new Intent(this, QuizActivity.class)
                        );
                    } else if (which == 2) {
                        startActivity(
                                new Intent(this, NumberQuizActivity.class)
                        );
                    }

                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}