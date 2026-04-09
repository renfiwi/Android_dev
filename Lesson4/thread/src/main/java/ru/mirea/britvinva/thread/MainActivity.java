package ru.mirea.britvinva.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import ru.mirea.britvinva.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread mainThread = Thread.currentThread();
        binding.textView.setText("Имя текущего потока: " + mainThread.getName());

        mainThread.setName("МОЙ НОМЕР ГРУППЫ: 54, НОМЕР ПО СПИСКУ: 3, ЛЮБИМЫЙ ФИЛЬМ: Интерстеллар");
        binding.textView.append("\nНовое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String bgThreadName = Thread.currentThread().getName();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.textView.append("\n\nЗапуск вычислений");
                                    binding.textView.append("\nВычисления идут в потоке: " + bgThreadName);
                                }
                            });

                            String pairsStr = binding.editTextPairs.getText().toString();
                            String daysStr = binding.editTextDays.getText().toString();

                            int pairs = Integer.parseInt(pairsStr);
                            int days = Integer.parseInt(daysStr);
                            float average = (float) pairs / days;
                            Log.d("ThreadProject", "Запущен поток №1 студентом группы №54 номер по списку 3 БСБО-54-24");
                            long endTime = System.currentTimeMillis() + 5 * 1000;
                            while (System.currentTimeMillis() < endTime) {
                                synchronized (this) {
                                    wait(endTime - System.currentTimeMillis());
                                }
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String uiThreadName = Thread.currentThread().getName();

                                    binding.textView.append("\nСреднее количество пар в день: " + average);
                                    binding.textView.append("\nОбновление интерфейса в потоке: " + uiThreadName);
                                }
                            });
                        } catch (Exception e) {
                            Log.e("ThreadApp", "Ошибка в фоновом потоке", e);
                        }
                    }
                }, "поток бсбо-54-24, бритвин в.а.").start();
            }
        });
    }
}