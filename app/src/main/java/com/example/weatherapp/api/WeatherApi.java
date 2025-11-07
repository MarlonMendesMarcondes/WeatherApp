package com.example.weatherapp.api;

import com.example.weatherapp.models.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    /**
     * Endpoint para buscar informações do tempo
     * @param apiKey Sua chave de API da HG Brasil
     * @param cityName Nome da cidade (opcional, padrão é pela localização IP)
     * @return WeatherResponse com os dados do tempo
     */
    @GET("weather")
    Call<WeatherResponse> getWeather(
            @Query("key") String apiKey,
            @Query("city_name") String cityName
    );

    /**
     * Endpoint para buscar informações do tempo apenas com a chave
     * @param apiKey Sua chave de API da HG Brasil
     * @return WeatherResponse com os dados do tempo
     */
    @GET("weather")
    Call<WeatherResponse> getWeather(
            @Query("key") String apiKey
    );
}