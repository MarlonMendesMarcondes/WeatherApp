package com.example.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherapp.R;
import com.example.weatherapp.models.Forecast;
import java.util.List;

/**
 * WeatherAdapter - Adapter para o RecyclerView de previsões do tempo
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<Forecast> forecastList;

    public WeatherAdapter(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather_card, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Forecast forecast = forecastList.get(position);
        holder.bind(forecast);
    }

    @Override
    public int getItemCount() {
        return forecastList != null ? forecastList.size() : 0;
    }

    /**
     * Atualiza os dados da lista
     */
    public void updateData(List<Forecast> newForecastList) {
        this.forecastList = newForecastList;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder para os itens da lista
     */
    static class WeatherViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtDate;
        private final TextView txtWeekday;
        private final TextView txtMaxMin;
        private final TextView txtCondition;
        private final TextView txtRainProbability;
        private final TextView txtWind;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtWeekday = itemView.findViewById(R.id.txtWeekday);
            txtMaxMin = itemView.findViewById(R.id.txtMaxMin);
            txtCondition = itemView.findViewById(R.id.txtCondition);
            txtRainProbability = itemView.findViewById(R.id.txtRainProbability);
            txtWind = itemView.findViewById(R.id.txtWind);
        }

        public void bind(Forecast forecast) {
            txtDate.setText(forecast.getDate());
            txtWeekday.setText(forecast.getWeekday());
            txtMaxMin.setText(forecast.getMax() + "°C / " + forecast.getMin() + "°C");
            txtCondition.setText(forecast.getDescription());
            txtRainProbability.setText("Chuva: " + forecast.getRainProbability() + "%");
            txtWind.setText("Vento: " + forecast.getWindSpeedy());
        }
    }
}