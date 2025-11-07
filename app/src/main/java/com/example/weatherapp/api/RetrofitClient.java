package com.example.weatherapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.hgbrasil.com/";

    // IMPORTANTE: Substitua pela sua chave de API
    // Obtenha gratuitamente em: https://console.hgbrasil.com/
    public static final String API_KEY = "b69792fb";

    private static Retrofit retrofit = null;

    /**
     * Retorna a instância do Retrofit
     * Usa o padrão Singleton para economizar recursos
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Retorna a instância da API
     */
    public static WeatherApi getWeatherApi() {
        return getClient().create(WeatherApi.class);
    }
}