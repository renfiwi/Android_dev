package ru.mirea.britvinva.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class UploadWorker extends Worker {
    static final String TAG = "UploadWorker";

    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: start. Интернет доступен, начинаем скачивание...");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }

        Log.d(TAG, "doWork: end. Файлы успешно загружены в фоне!");

        return Result.success();
    }
}