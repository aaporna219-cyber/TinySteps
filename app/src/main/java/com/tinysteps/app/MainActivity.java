package com.tinysteps.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.tinysteps.app.activities.AlphabetActivity;
import com.tinysteps.app.activities.NumbersActivity;
import com.tinysteps.app.activities.AnimalsActivity;
import com.tinysteps.app.activities.ColorsActivity;
import com.tinysteps.app.activities.FoodActivity;

public class MainActivity extends AppCompatActivity {

    private View cardAlphabets;
    private View cardNumbers;
    private View cardAnimals;
    private View cardColors;
    private View cardFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardAlphabets = findViewById(R.id.cardAlphabets);
        cardNumbers = findViewById(R.id.cardNumbers);
        cardAnimals = findViewById(R.id.cardAnimals);
        cardColors = findViewById(R.id.cardColors);
        cardFood = findViewById(R.id.cardFood);

        cardAlphabets.setOnClickListener(v ->
                startActivity(new Intent(
                        MainActivity.this,
                        AlphabetActivity.class
                ))
        );

        cardNumbers.setOnClickListener(v ->
                startActivity(new Intent(
                        MainActivity.this,
                        NumbersActivity.class
                ))
        );

        cardAnimals.setOnClickListener(v ->
                startActivity(new Intent(
                        MainActivity.this,
                        AnimalsActivity.class
                ))
        );

        cardColors.setOnClickListener(v ->
                startActivity(new Intent(
                        MainActivity.this,
                        ColorsActivity.class
                ))
        );

        cardFood.setOnClickListener(v ->
                startActivity(new Intent(
                        MainActivity.this,
                        FoodActivity.class
                ))
        );
    }
}
