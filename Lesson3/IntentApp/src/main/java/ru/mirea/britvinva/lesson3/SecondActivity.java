package ru.mirea.britvinva.lesson3;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String currentTime = getIntent().getStringExtra("time_key");

        int myNumber = 3;
        int square = myNumber * myNumber;

        TextView textView = findViewById(R.id.textView);
        if (textView != null) {
            String resultText = "КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ "
                    + square + ", а текущее время " + currentTime;
            textView.setText(resultText);
        }
    }
}