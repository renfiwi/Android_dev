package ru.mirea.britvinva.toastapp;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txt = findViewById(R.id.editText);
    }

    public void onClickFunc(View view) {
        if (txt != null) {
            String textToSend = txt.getText().toString();
            int amount = textToSend.length();
            Toast toast = Toast.makeText(getApplicationContext(),
                    "СТУДЕНТ №" + amount + " ГРУППА " + amount + " Количество символов - " + amount,
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}