package com.simplechesstimer;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class TimerActivity extends AppCompatActivity {

    Button player1, player2;

    CountDownTimer countDownTimer_p1, countDownTimer_p2;

    private long p1_time_left_in_mil, p2_time_left_in_mil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_timer);

        player1 = findViewById(R.id.player1_btn);
        player2 = findViewById(R.id.player2_btn);

        p1_time_left_in_mil = MainActivity.selected_time * 60000;
        p2_time_left_in_mil = p1_time_left_in_mil;

        setTimer();
        player1Clicked();
        player2Clicked();
    }

    void setTimer() {
        int min = (int) (p1_time_left_in_mil / 60000);
        int sec = (int) (p1_time_left_in_mil % 60000 / 1000);

        String text = min + " : " + sec;

        player1.setText(text);
        player2.setText(text);
    }

    void player1Clicked() {
        player1.setOnClickListener(v -> {
            player1.setBackgroundColor(Color.BLACK);
            player1.setTextColor(Color.WHITE);
            player2.setBackgroundColor(Color.WHITE);
            player2.setTextColor(Color.BLACK);

            if (countDownTimer_p1 != null) countDownTimer_p1.cancel();

            player1.setEnabled(false);
            player2.setEnabled(true);

            countDownTimer_p2 = new CountDownTimer(p2_time_left_in_mil, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    p2_time_left_in_mil = millisUntilFinished;
                    check_for_timeout(millisUntilFinished, false);
                    updateP2Timer();
                }

                @Override
                public void onFinish() {

                }
            }.start();
        });
    }

    void player2Clicked() {
        player2.setOnClickListener(v -> {
            player2.setBackgroundColor(Color.BLACK);
            player2.setTextColor(Color.WHITE);
            player1.setBackgroundColor(Color.WHITE);
            player1.setTextColor(Color.BLACK);

            if (countDownTimer_p2 != null) countDownTimer_p2.cancel();

            player1.setEnabled(true);
            player2.setEnabled(false);

            countDownTimer_p1 = new CountDownTimer(p1_time_left_in_mil, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    p1_time_left_in_mil = millisUntilFinished;
                    check_for_timeout(millisUntilFinished, true);
                    updateP1Timer();
                }

                @Override
                public void onFinish() {

                }
            }.start();
        });
    }

    void updateP1Timer() {
        int min = (int) (p1_time_left_in_mil / 60000);
        int sec = (int) (p1_time_left_in_mil % 60000 / 1000);

        String text;

        if (sec < 10) {
            text = min + " : 0" + sec;
        } else {
            text = min + " : " + sec;
        }

        player1.setText(text);
    }

    void updateP2Timer() {
        int min = (int) (p2_time_left_in_mil / 60000);
        int sec = (int) (p2_time_left_in_mil % 60000 / 1000);

        String text;

        if (sec < 10) {
            text = min + " : 0" + sec;
        } else {
            text = min + " : " + sec;
        }

        player2.setText(text);
    }

    void check_for_timeout(long time, boolean p1) {
        if (time < 900) {
            player1.setEnabled(false);
            player2.setEnabled(false);

            countDownTimer_p1.cancel();
            countDownTimer_p2.cancel();

            String text = "YOU LOSE";
            if (p1) {
                player1.setTextColor(Color.RED);
                player1.setText(text);
            } else {
                player2.setTextColor(Color.RED);
                player2.setText(text);
            }
        }
    }
}