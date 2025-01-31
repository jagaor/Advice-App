package com.example.api;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adviceapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tvAdvice;
    private Button btnGetAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAdvice = findViewById(R.id.tvAdvice);
        btnGetAdvice = findViewById(R.id.btnGetAdvice);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.adviceslip.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        AdviceApi adviceApi = retrofit.create(AdviceApi.class);

        btnGetAdvice.setOnClickListener(v -> {
            Call<AdviceResponse> call = adviceApi.getAdvice();
            call.enqueue(new Callback<AdviceResponse>() {
                @Override
                public void onResponse(Call<AdviceResponse> call, Response<AdviceResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String advice = response.body().getSlip().getAdvice();
                        tvAdvice.setText(advice);
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to get advice", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AdviceResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
