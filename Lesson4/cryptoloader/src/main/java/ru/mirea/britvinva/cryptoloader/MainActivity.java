package ru.mirea.britvinva.cryptoloader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ru.mirea.britvinva.cryptoloader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    public final String TAG = this.getClass().getSimpleName();
    private final int LoaderID = 1234;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phrase = binding.editTextText.getText().toString();
                if (phrase.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Введите текст!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SecretKey secretKey = generateKey();
                byte[] encryptedText = encryptMsg(phrase, secretKey);

                Bundle bundle = new Bundle();
                bundle.putByteArray(MyLoader.ARG_WORD, encryptedText);
                bundle.putByteArray("key", secretKey.getEncoded());

                LoaderManager.getInstance(MainActivity.this).restartLoader(LoaderID, bundle, MainActivity.this);
            }
        });
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle bundle) {
        if (id == LoaderID) {
            Toast.makeText(this, "Начало работы Loader...", Toast.LENGTH_SHORT).show();
            return new MyLoader(this, bundle);
        }
        throw new IllegalArgumentException("Неверный ID загрузчика");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String result) {
        if (loader.getId() == LoaderID) {
            Log.d(TAG, "onLoadFinished: " + result);
            Toast.makeText(this, "Расшифровано: " + result, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG, "onLoaderReset");
    }


    public static SecretKey generateKey() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encryptMsg(String message, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }
}