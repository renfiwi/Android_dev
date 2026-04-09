package ru.mirea.britvinva.data_thread;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.TimeUnit;

import ru.mirea.britvinva.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    String answer = "Различия методов:\n\n" +
            "1. runOnUiThread: Выполняет код в UI-потоке немедленно или ставит в очередь.\n" +
            "2. View.post: Ставит код в очередь UI-потока, привязываясь к конкретному View.\n" +
            "3. View.postDelayed: Ставит код в очередь с заданной задержкой.\n\n" +
            "Последовательность вывода на экран: runn1 -> runn2 -> runn3 (так как у runn3 установлена задержка 2000мс).";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Runnable runn1 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn1");
            }
        };
        final Runnable runn2 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn2");
            }
        };
        final Runnable runn3 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn3");

                binding.tvInfo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.tvInfo.setText(answer);
                    }
                }, 2000);
            }
        };

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);
                    TimeUnit.SECONDS.sleep(1);
                    binding.tvInfo.postDelayed(runn3, 2000);
                    binding.tvInfo.post(runn2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}