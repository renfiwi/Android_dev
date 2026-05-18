package ru.mirea.britvinva.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

public class MicrophoneFragment extends Fragment {

    private String recordFilePath = null;
    private Button btnRecord;
    private Button btnPlay;

    private boolean isRecording = false;
    private boolean isPlaying = false;

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    onRecord(!isRecording);
                } else {
                    Toast.makeText(requireContext(), "Нужно разрешение", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_microphone, container, false);

        btnRecord = root.findViewById(R.id.btnRecord);
        btnPlay = root.findViewById(R.id.btnPlay);

        recordFilePath = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "field_diary.3gp").getAbsolutePath();

        btnRecord.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                onRecord(!isRecording);
            } else {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
            }
        });

        btnPlay.setOnClickListener(v -> onPlay(!isPlaying));

        return root;
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
            btnRecord.setText("Остановить запись");
            btnPlay.setEnabled(false);
        } else {
            stopRecording();
            btnRecord.setText("Начать запись");
            btnPlay.setEnabled(true);
        }
        isRecording = start;
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
            btnPlay.setText("Остановить прослушивание");
            btnRecord.setEnabled(false);
        } else {
            stopPlaying();
            btnPlay.setText("Прослушать запись");
            btnRecord.setEnabled(true);
        }
        isPlaying = start;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e("MicrophoneFragment", "prepare() failed");
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
            player.setOnCompletionListener(mp -> onPlay(false));
        } catch (IOException e) {
            Log.e("MicrophoneFragment", "prepare() failed");
        }
    }

    private void stopPlaying() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recorder != null) { recorder.release(); recorder = null; }
        if (player != null) { player.release(); player = null; }
    }
}