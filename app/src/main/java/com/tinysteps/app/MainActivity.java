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
import com.tinysteps.app.activities.ShapeQuizActivity;

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

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {

        cardAlphabets = findViewById(R.id.cardAlphabets);
        cardNumbers = findViewById(R.id.cardNumbers);
        cardAnimals = findViewById(R.id.cardAnimals);
        cardColors = findViewById(R.id.cardColors);
        cardFood = findViewById(R.id.cardFood);
        cardGame = findViewById(R.id.cardGame);
        cardMemoryGame = findViewById(R.id.cardMemoryGame);
    }

    private void setupClickListeners() {

        cardAlphabets.setOnClickListener(v ->
                startActivity(new Intent(this, AlphabetActivity.class))
        );

        cardNumbers.setOnClickListener(v ->
                startActivity(new Intent(this, NumbersActivity.class))
        );

        cardAnimals.setOnClickListener(v ->
                startActivity(new Intent(this, AnimalsActivity.class))
        );

        cardColors.setOnClickListener(v ->
                startActivity(new Intent(this, ColorsActivity.class))
        );

        cardFood.setOnClickListener(v ->
                startActivity(new Intent(this, FoodActivity.class))
        );

        cardMemoryGame.setOnClickListener(v ->
                startActivity(new Intent(this, MemoryGameActivity.class))
        );

        cardGame.setOnClickListener(v -> showGamesMenu());
    }

    private void showGamesMenu() {

        String[] games = {
                "Picture Matching Game 🧩",
                "Animal Quiz 🐘",
                "Number Quiz 🔢",
                "Shapes Quiz 🔺"
        };

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle("Choose a Game 🎮");

        builder.setItems(games, (dialog, which) -> {

            switch (which) {

                case 0:
                    startActivity(
                            new Intent(this, GameActivity.class)
                    );
                    break;

                case 1:
                    startActivity(
                            new Intent(this, QuizActivity.class)
                    );
                    break;

                case 2:
                    startActivity(
                            new Intent(this, NumberQuizActivity.class)
                    );
                    break;

                case 3:
                    startActivity(
                            new Intent(this, ShapeQuizActivity.class)
                    );
                    break;
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) ->
                dialog.dismiss()
        );

        builder.show();
    }
}
