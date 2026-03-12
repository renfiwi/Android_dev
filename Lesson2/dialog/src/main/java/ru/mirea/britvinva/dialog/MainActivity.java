package ru.mirea.britvinva.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

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
    }

    public void onClickShowDialog(View view) {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(),
                "mirea");
    }
    public void onOkClicked() {
        Toast.makeText(getApplicationContext(),
                "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(),
                "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(),
                "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG).show();
    }

    public void onClickTimeDialogFragment(View view) {
        MyTimeDialogFragment timeDialog = new MyTimeDialogFragment();
        timeDialog.show(getSupportFragmentManager(), "timePicker");
    }

    public void onClickMyDateDialogFragment(View view) {
        MyDateDialogFragment dateDialog = new MyDateDialogFragment();
        dateDialog.show(getSupportFragmentManager(), "datePicker");
    }

    public void onClickProgressDialogFragment(View view) {
        MyProgressDialogFragment progressDialog = new MyProgressDialogFragment();
        progressDialog.show(getSupportFragmentManager(), "progressDialog");
    }

    public void onTimeSet(int hourOfDay, int minute) {
        String text = String.format("Выбрано время: %02d:%02d", hourOfDay, minute);
        Snackbar.make(findViewById(R.id.main), text, Snackbar.LENGTH_LONG).show();
    }

    public void onDateSet(int year, int month, int day) {
        String text = String.format("Выбрана дата: %02d.%02d.%d", day, month, year);
        Snackbar.make(findViewById(R.id.main), text, Snackbar.LENGTH_LONG).show();
    }

    public void onProgressOkClicked() {
        Snackbar.make(findViewById(R.id.main), "Загрузка подтверждена", Snackbar.LENGTH_SHORT).show();
    }
}