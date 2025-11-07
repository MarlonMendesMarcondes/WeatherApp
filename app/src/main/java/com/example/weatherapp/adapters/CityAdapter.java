package com.example.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

import java.util.List;

/**
 * CityAdapter - Adapter para lista de cidades
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private final List<String> cities;
    private final OnCityClickListener listener;

    /**
     * Interface para cliques em cidades
     */
    public interface OnCityClickListener {
        void onCityClick(String city);
    }

    public CityAdapter(List<String> cities, OnCityClickListener listener) {
        this.cities = cities;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        String city = cities.get(position);
        holder.txtCityName.setText(city);

        // Configura clique no item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCityClick(city);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities != null ? cities.size() : 0;
    }

    /**
     * ViewHolder para itens de cidade
     */
    static class CityViewHolder extends RecyclerView.ViewHolder {
        TextView txtCityName;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCityName = itemView.findViewById(R.id.txtCityName);
        }
    }
}