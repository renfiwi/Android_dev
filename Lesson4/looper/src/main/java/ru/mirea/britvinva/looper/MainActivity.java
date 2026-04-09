package ru.mirea.britvinva.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.britvinva.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(MainActivity.class.getSimpleName(), "Результат: " + msg.getData().getString("result"));
            }
        };

        myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myLooper.mHandler != null) {
                    String ageStr = binding.editTextAge.getText().toString();
                    String profession = binding.editTextProfession.getText().toString();

                    int age = 0;
                    if (!ageStr.isEmpty()) {
                        age = Integer.parseInt(ageStr);
                    }

                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putInt("AGE", age);
                    bundle.putString("PROFESSION", profession);
                    msg.setData(bundle);

                    myLooper.mHandler.sendMessage(msg);
                } else {
                    Toast.makeText(MainActivity.this, "Looper еще не готов", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}