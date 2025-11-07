package com.example.weatherapp.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {

    @SerializedName("by")
    private String by;

    @SerializedName("valid_key")
    private boolean validKey;

    @SerializedName("results")
    private Results results;

    @SerializedName("execution_time")
    private double executionTime;

    @SerializedName("from_cache")
    private boolean fromCache;

    public String getBy() {
        return by;
    }

    public boolean isValidKey() {
        return validKey;
    }

    public Results getResults() {
        return results;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public boolean isFromCache() {
        return fromCache;
    }

    public static class Results {
        @SerializedName("temp")
        private int temp;

        @SerializedName("date")
        private String date;

        @SerializedName("time")
        private String time;

        @SerializedName("condition_code")
        private String conditionCode;

        @SerializedName("description")
        private String description;

        @SerializedName("currently")
        private String currently;

        @SerializedName("cid")
        private String cid;

        @SerializedName("city")
        private String city;

        @SerializedName("img_id")
        private String imgId;

        @SerializedName("humidity")
        private int humidity;

        @SerializedName("cloudiness")
        private double cloudiness;

        @SerializedName("rain")
        private double rain;

        @SerializedName("wind_speedy")
        private String windSpeedy;

        @SerializedName("wind_direction")
        private int windDirection;

        @SerializedName("sunrise")
        private String sunrise;

        @SerializedName("sunset")
        private String sunset;

        @SerializedName("moon_phase")
        private String moonPhase;

        @SerializedName("city_name")
        private String cityName;

        @SerializedName("forecast")
        private List<Forecast> forecast;

        // Getters
        public int getTemp() {
            return temp;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getConditionCode() {
            return conditionCode;
        }

        public String getDescription() {
            return description;
        }

        public String getCurrently() {
            return currently;
        }

        public String getCid() {
            return cid;
        }

        public String getCity() {
            return city;
        }

        public String getImgId() {
            return imgId;
        }

        public int getHumidity() {
            return humidity;
        }

        public double getCloudiness() {
            return cloudiness;
        }

        public double getRain() {
            return rain;
        }

        public String getWindSpeedy() {
            return windSpeedy;
        }

        public int getWindDirection() {
            return windDirection;
        }

        public String getSunrise() {
            return sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public String getMoonPhase() {
            return moonPhase;
        }

        public String getCityName() {
            return cityName;
        }

        public List<Forecast> getForecast() {
            return forecast;
        }
    }
}