package com.example.weatherapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.WeatherAdapter;
import com.example.weatherapp.api.RetrofitClient;
import com.example.weatherapp.api.WeatherApi;
import com.example.weatherapp.models.Forecast;
import com.example.weatherapp.models.WeatherResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {

    private static final String ARG_CITY = "city";

    private String cityName;
    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private ProgressBar progressBar;
    private TextView txtCurrentTemp;
    private TextView txtCurrentCity;
    private TextView txtCurrentCondition;
    private TextView txtCurrentDetails;
    private FloatingActionButton fabScanQR;

    public static WeatherFragment newInstance(String city) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cityName = getArguments().getString(ARG_CITY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewWeather);
        progressBar = view.findViewById(R.id.progressBar);
        txtCurrentTemp = view.findViewById(R.id.txtCurrentTemp);
        txtCurrentCity = view.findViewById(R.id.txtCurrentCity);
        txtCurrentCondition = view.findViewById(R.id.txtCurrentCondition);
        txtCurrentDetails = view.findViewById(R.id.txtCurrentDetails);
        fabScanQR = view.findViewById(R.id.fabScanQR);

        recyclerView = view.findViewById(R.id.recyclerViewWeather);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WeatherAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        fabScanQR.setOnClickListener(v -> {
            if (getActivity() != null) {
                Toast.makeText(getContext(), "Use o menu para escanear QR Code", Toast.LENGTH_SHORT).show();
            }
        });

        loadWeatherData(cityName);

        return view;
    }

    public void loadWeatherData(String city) {
        if (city != null) {
            cityName = city;
        }

        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        WeatherApi api = RetrofitClient.getWeatherApi();
        Call<WeatherResponse> call;

        if (cityName != null && !cityName.isEmpty()) {
            call = api.getWeather(RetrofitClient.API_KEY, cityName);
        } else {
            call = api.getWeather(RetrofitClient.API_KEY);
        }

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call,
                                   @NonNull Response<WeatherResponse> response) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }

                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();

                    if (weatherResponse.isValidKey() && weatherResponse.getResults() != null) {
                        updateUI(weatherResponse.getResults());
                    } else {
                        showError("Chave de API inválida");
                    }
                } else {
                    showError("Erro ao carregar dados");
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                showError("Erro: " + t.getMessage());
            }
        });
    }

    private void updateUI(WeatherResponse.Results results) {
        if (txtCurrentTemp != null) {
            txtCurrentTemp.setText(results.getTemp() + "°C");
        }
        if (txtCurrentCity != null) {
            txtCurrentCity.setText(results.getCityName());
        }
        if (txtCurrentCondition != null) {
            txtCurrentCondition.setText(results.getDescription());
        }
        if (txtCurrentDetails != null) {
            String details = "Umidade: " + results.getHumidity() + "% | " +
                    "Vento: " + results.getWindSpeedy() + " | " +
                    "Nascer do Sol: " + results.getSunrise() + " | " +
                    "Pôr do Sol: " + results.getSunset();
            txtCurrentDetails.setText(details);
        }

        List<Forecast> forecasts = results.getForecast();
        if (forecasts != null && !forecasts.isEmpty() && adapter != null) {
            adapter.updateData(forecasts);
        }
    }

    private void showError(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}