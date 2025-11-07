package com.example.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class Forecast {

    @SerializedName("date")
    private String date;

    @SerializedName("weekday")
    private String weekday;

    @SerializedName("max")
    private int max;

    @SerializedName("min")
    private int min;

    @SerializedName("cloudiness")
    private double cloudiness;

    @SerializedName("rain")
    private double rain;

    @SerializedName("rain_probability")
    private int rainProbability;

    @SerializedName("wind_speedy")
    private String windSpeedy;

    @SerializedName("description")
    private String description;

    @SerializedName("condition")
    private String condition;

    // Getters
    public String getDate() {
        return date;
    }

    public String getWeekday() {
        return weekday;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public double getCloudiness() {
        return cloudiness;
    }

    public double getRain() {
        return rain;
    }

    public int getRainProbability() {
        return rainProbability;
    }

    public String getWindSpeedy() {
        return windSpeedy;
    }

    public String getDescription() {
        return description;
    }

    public String getCondition() {
        return condition;
    }

    // Setters (se necessário)
    public void setDate(String date) {
        this.date = date;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setCloudiness(double cloudiness) {
        this.cloudiness = cloudiness;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public void setRainProbability(int rainProbability) {
        this.rainProbability = rainProbability;
    }

    public void setWindSpeedy(String windSpeedy) {
        this.windSpeedy = windSpeedy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}