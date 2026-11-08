package com.tinysteps.app.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tinysteps.app.R;

import java.util.ArrayList;
import java.util.Collections;

public class MemoryGameActivity extends AppCompatActivity {

    private GridLayout memoryGrid;
    private TextView tvMoves, tvPairs, tvFeedback, btnBack;
    private Button btnPlayAgain;

    private final String[] animals = {
            "🐘", "🦁", "🐰", "🐵"
    };

    private final ArrayList<String> cards = new ArrayList<>();
    private final ArrayList<TextView> cardViews = new ArrayList<>();

    private int firstCard = -1;
    private int secondCard = -1;
    private int moves = 0;
    private int matchedPairs = 0;

    private boolean waiting = false;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);

        memoryGrid = findViewById(R.id.memoryGrid);
        tvMoves = findViewById(R.id.tvMoves);
        tvPairs = findViewById(R.id.tvPairs);
        tvFeedback = findViewById(R.id.tvMemoryFeedback);
        btnBack = findViewById(R.id.btnMemoryBack);
        btnPlayAgain = findViewById(R.id.btnMemoryAgain);

        btnBack.setOnClickListener(v -> finish());
        btnPlayAgain.setOnClickListener(v -> startNewGame());

        startNewGame();
    }

    private void startNewGame() {

        handler.removeCallbacksAndMessages(null);

        cards.clear();
        cardViews.clear();
        memoryGrid.removeAllViews();

        moves = 0;
        matchedPairs = 0;
        firstCard = -1;
        secondCard = -1;
        waiting = false;

        for (String animal : animals) {
            cards.add(animal);
            cards.add(animal);
        }

        Collections.shuffle(cards);

        tvFeedback.setText("Find the matching animal pairs! 🐾");
        btnPlayAgain.setVisibility(Button.GONE);

        updateCounters();
        createCards();
    }

    private void createCards() {

        for (int i = 0; i < cards.size(); i++) {

            final int position = i;

            TextView card = new TextView(this);

            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams(
                            GridLayout.spec(i / 4),
                            GridLayout.spec(i % 4, 1f)
                    );

            params.width = 0;
            params.height = dpToPx(105);
            params.setMargins(
                    dpToPx(4),
                    dpToPx(5),
                    dpToPx(4),
                    dpToPx(5)
            );

            card.setLayoutParams(params);
            card.setText("❓");
            card.setTextSize(29);
            card.setTextColor(Color.rgb(48, 48, 74));
            card.setGravity(Gravity.CENTER);
            card.setTypeface(null, Typeface.BOLD);
            card.setBackground(createBackground("#E5F0FF"));
            card.setClickable(true);
            card.setFocusable(true);

            card.setOnClickListener(v -> revealCard(position));

            memoryGrid.addView(card);
            cardViews.add(card);
        }
    }

    private void revealCard(int position) {

        if (waiting || position == firstCard ||
                cardViews.get(position).isEnabled() == false) {
            return;
        }

        TextView selectedCard = cardViews.get(position);

        selectedCard.setText(cards.get(position));
        selectedCard.setBackground(createBackground("#FFF0D8"));

        if (firstCard == -1) {
            firstCard = position;
            tvFeedback.setText("Find its matching card! 😊");
            return;
        }

        secondCard = position;
        moves++;
        updateCounters();

        if (cards.get(firstCard).equals(cards.get(secondCard))) {

            cardViews.get(firstCard).setEnabled(false);
            cardViews.get(secondCard).setEnabled(false);

            cardViews.get(firstCard).setBackground(
                    createBackground("#B9EBCB")
            );

            cardViews.get(secondCard).setBackground(
                    createBackground("#B9EBCB")
            );

            matchedPairs++;
            tvFeedback.setText("Great! You found a pair! ⭐");

            firstCard = -1;
            secondCard = -1;

            updateCounters();

            if (matchedPairs == 4) {
                finishGame();
            }

        } else {

            waiting = true;
            tvFeedback.setText("Not a match! Try again. 😊");

            handler.postDelayed(() -> {

                cardViews.get(firstCard).setText("❓");
                cardViews.get(secondCard).setText("❓");

                cardViews.get(firstCard).setBackground(
                        createBackground("#E5F0FF")
                );

                cardViews.get(secondCard).setBackground(
                        createBackground("#E5F0FF")
                );

                firstCard = -1;
                secondCard = -1;
                waiting = false;

            }, 900);
        }
    }

    private void finishGame() {

        tvFeedback.setText(
                "Amazing! You found all 4 pairs! 🏆"
        );

        btnPlayAgain.setVisibility(Button.VISIBLE);
    }

    private void updateCounters() {

        tvMoves.setText("🎯 Moves: " + moves);
        tvPairs.setText("⭐ Pairs: " + matchedPairs + "/4");
    }

    private GradientDrawable createBackground(String color) {

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor(color));
        drawable.setCornerRadius(dpToPx(16));

        return drawable;
    }

    private int dpToPx(int dp) {

        return (int) (dp * getResources()
                .getDisplayMetrics().density + 0.5f);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
