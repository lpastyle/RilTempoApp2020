package com.example.riltempoapp;

import android.content.Context;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.riltempoapp.Tools.getNowDate;

public class HistoryActivity extends AppCompatActivity {
    private static String LOG_TAG = HistoryActivity.class.getSimpleName();
    private Context context = this;

    // views
    private ProgressBar progressBar;
    private RecyclerView tempDateRv;
    private final List<TempoDate> tempoDates = new ArrayList<>();
    private TempoDatesAdapter tempoDatesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // find views
        tempDateRv = findViewById(R.id.tempo_history_rv);
        progressBar = findViewById(R.id.network_fetch_pw);

        // set recycler view
        tempDateRv.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); // use a linear layout manager
        tempDateRv.setLayoutManager(layoutManager);

        tempoDatesAdapter = new TempoDatesAdapter(this, tempoDates); // attach a custom adapter
        tempDateRv.setAdapter(tempoDatesAdapter);

        // set progress bar

    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "onResume()");
        super.onResume();
        refreshTempoDates();
    }

    private void refreshTempoDates() {
        if (MainActivity.edfApi != null) {
            progressBar.setVisibility(View.VISIBLE);

            // get date range
            String yearNow = getNowDate("yyyy");
            String yearBefore = String.valueOf(Integer.parseInt(yearNow) - 1);

            // call API
            Call<TempoHistory> call = MainActivity.edfApi.getTempoHistory(yearBefore, yearNow);
            call.enqueue(new Callback<TempoHistory>() {
                @Override
                public void onResponse(@NotNull Call<TempoHistory> call, @NotNull Response<TempoHistory> response) {
                    // empty current list
                    tempoDates.clear();
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        TempoHistory tempoHistory = response.body();
                        if (tempoHistory != null) {
                            List<TempoDate> results = tempoHistory.getTempoDates();
                            Log.d(LOG_TAG, "nb date received = " + results.size());
                            tempoDates.addAll(results);
                        }
                    } else {
                        Log.e(LOG_TAG, "HTTP error " + response.code());
                        Toast toast = Toast.makeText(context, R.string.toast_http_error, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    tempoDatesAdapter.notifyDataSetChanged();
                    tempDateRv.scrollToPosition(0);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NotNull Call<TempoHistory> call, @NotNull Throwable t) {
                    Log.e(LOG_TAG, "Call to 'getTempoHistory' failed");
                    progressBar.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(context, R.string.toast_network_error, Toast.LENGTH_LONG);
                    toast.show();
                }
            });

        } else {
            Log.e(LOG_TAG, "Api not initialized");
        }
    }

}
