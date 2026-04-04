package ru.mirea.britvinva.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TextView textViewDevBook = findViewById(R.id.textViewDevBook);
        EditText editTextUserBook = findViewById(R.id.editTextUserBook);
        EditText editTextUserQuote = findViewById(R.id.editTextUserQuote);
        Button btnSendResult = findViewById(R.id.btnSendResult);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String book_name = extras.getString(MainActivity.BOOK_NAME_KEY);
            String quotes_name = extras.getString(MainActivity.QUOTES_KEY);
            textViewDevBook.setText(String.format("Название Вашей любимой книги: %s\nЦитата: %s", book_name, quotes_name));
        }

        btnSendResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userBook = editTextUserBook.getText().toString();
                String userQuote = editTextUserQuote.getText().toString();

                String textToSend = String.format("Название Вашей любимой книги: %s. Цитата: %s", userBook, userQuote);

                Intent data = new Intent();
                data.putExtra(MainActivity.USER_MESSAGE, textToSend);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }
}