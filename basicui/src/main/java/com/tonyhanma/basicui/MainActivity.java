package com.tonyhanma.basicui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int PROGRESS_STEPS = 10;
    private static final int PROGRESS_STEP_SIZE = 10;
    private static final long PROGRESS_STEP_DELAY_MS = 500;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private ProgressBar progressBar;
    private int progressStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        RadioGroup maritalStatus = findViewById(R.id.rgMaritalStatus);
        CheckBox harryPotter = findViewById(R.id.checkboxHarry);
        CheckBox matrix = findViewById(R.id.checkboxMatrix);

        advanceProgress();

        showMaritalStatus(maritalStatus.getCheckedRadioButtonId());
        maritalStatus.setOnCheckedChangeListener((group, checkedId) -> showMaritalStatus(checkedId));

        bindMovie(harryPotter, R.string.watched_harry, R.string.not_watched_harry);
        bindMovie(matrix, R.string.watched_matrix, R.string.not_watched_matrix);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    /** Llena la barra en pasos de medio segundo desde el hilo principal, sin crear hilos a mano. */
    private void advanceProgress() {
        if (progressStep++ >= PROGRESS_STEPS) {
            return;
        }
        progressBar.incrementProgressBy(PROGRESS_STEP_SIZE);
        handler.postDelayed(this::advanceProgress, PROGRESS_STEP_DELAY_MS);
    }

    private void showMaritalStatus(@IdRes int checkedId) {
        if (checkedId == R.id.rbMarried) {
            toast(R.string.status_married);
        } else if (checkedId == R.id.rbSingle) {
            toast(R.string.status_single);
        } else if (checkedId == R.id.rbInRel) {
            toast(R.string.status_in_relationship);
        }
    }

    private void bindMovie(CheckBox movie, @StringRes int watched, @StringRes int notWatched) {
        toast(movie.isChecked() ? watched : notWatched);
        movie.setOnCheckedChangeListener((button, isChecked) -> toast(isChecked ? watched : notWatched));
    }

    private void toast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
