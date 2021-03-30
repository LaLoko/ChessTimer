package com.simplechesstimer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    static int selected_time;
    EditText editText;
    String pom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.main_custom_time);
        setTime();
        submitClicked();
    }

    void setTime() {
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.main_5_rb) {
                selected_time = 5;
                editText.setEnabled(false);
            } else if (checkedId == R.id.main_10_rb) {
                selected_time = 10;
                editText.setEnabled(false);
            } else {
                editText.setEnabled(true);
            }
        });
    }

    void setCustomTime() {
        if (editText.isEnabled()) {
            pom = String.valueOf(editText.getText());
        }
    }

    boolean isCustomTimeCorrect() {
        return pom.matches("^[1-9][0-9]*$");
    }

    void submitClicked() {
        Button button = findViewById(R.id.main_submit_btn);
        button.setOnClickListener(v -> {
            if (editText.isEnabled()) {
                setCustomTime();
                if (isCustomTimeCorrect()) {
                    selected_time = Integer.parseInt(String.valueOf(editText.getText()));
                    startActivity(new Intent(MainActivity.this, TimerActivity.class));
                } else {
                    Toast.makeText(this, "custom time is incorrect", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (selected_time == 5 | selected_time == 10) {
                    startActivity(new Intent(MainActivity.this, TimerActivity.class));
                } else {
                    Toast.makeText(this, "select time before submit", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}