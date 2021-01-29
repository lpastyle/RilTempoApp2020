package com.example.riltempoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.riltempoapp.IEdfApi.EDF_TEMPO_ALERT_TYPE;
import static com.example.riltempoapp.Tools.getNowDate;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // member data
    private final Context context = this;
    public static IEdfApi edfApi = null;

    // views
    private TextView redDaysTv;
    private TextView whiteDaysTv;
    private TextView blueDaysTv;
    private DayColorView todayDcv;
    private DayColorView tomorrowDcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- init EDF API client ---
        Retrofit retrofitClient = ApiClient.get();
        if (retrofitClient != null) {
            edfApi = ApiClient.get().create(IEdfApi.class);
        }

        // init views
        redDaysTv = findViewById(R.id.red_days_tv);
        whiteDaysTv = findViewById(R.id.white_days_tv);
        blueDaysTv = findViewById(R.id.blue_days_tv);
        todayDcv = findViewById(R.id.today_dcv);
        tomorrowDcv = findViewById(R.id.tomorrow_dcv);


    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "onResume()");
        super.onResume();
        updateNbTempoDaysLeft();
        updateNbTempoDaysColors();
    }

    private void updateNbTempoDaysColors() {
        if (edfApi != null) {
            Call<TempoDaysColor> call = edfApi.getTempoDaysColor(getNowDate("yyyy-MM-dd"), EDF_TEMPO_ALERT_TYPE);
            call.enqueue(new Callback<TempoDaysColor>() {
                @Override
                public void onResponse(@NotNull Call<TempoDaysColor> call, @NotNull Response<TempoDaysColor> response) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        TempoDaysColor nbTempoDaysColor = response.body();
                        if (nbTempoDaysColor != null) {
                            Log.d(LOG_TAG, "J day color " + nbTempoDaysColor.getJourJ().getTempo());
                            Log.d(LOG_TAG, "J+1 day color " + nbTempoDaysColor.getJourJ1().getTempo());
                            todayDcv.setDayColor(nbTempoDaysColor.getJourJ().getTempo());
                            tomorrowDcv.setDayColor(nbTempoDaysColor.getJourJ1().getTempo());
                        }
                    } else {
                        Log.e(LOG_TAG, "HTTP error " + response.code());
                        Toast toast = Toast.makeText(context, R.string.toast_http_error, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TempoDaysColor> call, @NotNull Throwable t) {
                    Log.e(LOG_TAG, "Call to 'getTempoDaysColor' failed");
                    Toast toast = Toast.makeText(context, R.string.toast_network_error, Toast.LENGTH_LONG);
                    toast.show();

                }
            });

        } else {
            Log.e(LOG_TAG, "Api not initialized");
        }
    }

    private void updateNbTempoDaysLeft() {
        if (edfApi != null) {
            Call<TempoDaysLeft> call = edfApi.getTempoDaysLeft(EDF_TEMPO_ALERT_TYPE);
            call.enqueue(new Callback<TempoDaysLeft>() {
                @Override
                public void onResponse(@NotNull Call<TempoDaysLeft> call, @NotNull Response<TempoDaysLeft> response) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        TempoDaysLeft nbTempoDaysLetf = response.body();
                        if (nbTempoDaysLetf != null) {
                            Log.d(LOG_TAG, "red days left: " + nbTempoDaysLetf.getPARAMNBJROUGE());
                            Log.d(LOG_TAG, "white days left: " + nbTempoDaysLetf.getPARAMNBJBLANC());
                            Log.d(LOG_TAG, "blue days left: " + nbTempoDaysLetf.getPARAMNBJBLEU());
                            redDaysTv.setText(String.valueOf(nbTempoDaysLetf.getPARAMNBJROUGE()));
                            whiteDaysTv.setText(String.valueOf(nbTempoDaysLetf.getPARAMNBJBLANC()));
                            blueDaysTv.setText(String.valueOf(nbTempoDaysLetf.getPARAMNBJBLEU()));
                        }
                    } else {
                        Log.e(LOG_TAG, "HTTP error " + response.code());
                        Toast toast = Toast.makeText(context, R.string.toast_http_error, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TempoDaysLeft> call, @NotNull Throwable t) {
                    Log.e(LOG_TAG, "Call to 'getTempoDaysLeft' failed");
                    Toast toast = Toast.makeText(context, R.string.toast_network_error, Toast.LENGTH_LONG);
                    toast.show();

                }
            });

        } else {
            Log.e(LOG_TAG, "Api not initialized");
        }
    }

    /**
     * History button call back
     */
    public void showHistory(View view) {
        Intent intent = new Intent();
        intent.setClass(this, HistoryActivity.class);
        startActivity(intent);
    }
}
