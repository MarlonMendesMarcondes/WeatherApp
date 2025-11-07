package com.example.weatherapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapFragment";
    private static final String ARG_CITY = "city";

    private String cityName;
    private GoogleMap googleMap;
    private LatLng cityLocation = new LatLng(-23.5505, -46.6333); // São Paulo

    public static MapFragment newInstance(String city) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate chamado");

        if (getArguments() != null) {
            cityName = getArguments().getString(ARG_CITY);
            Log.d(TAG, "Cidade: " + cityName);
            updateCityLocation(cityName);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView chamado");

        try {
            View view = inflater.inflate(R.layout.fragment_map, container, false);
            Log.d(TAG, "Layout inflado com sucesso");

            // Inicializa o mapa
            initializeMap();

            return view;
        } catch (Exception e) {
            Log.e(TAG, "Erro ao criar view", e);
            Toast.makeText(getContext(), "Erro ao carregar mapa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void initializeMap() {
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);

            if (mapFragment != null) {
                Log.d(TAG, "MapFragment encontrado, obtendo mapa...");
                mapFragment.getMapAsync(this);
            } else {
                Log.e(TAG, "MapFragment é null - verifique fragment_map.xml");
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Erro: Mapa não encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao inicializar mapa", e);
            if (getContext() != null) {
                Toast.makeText(getContext(), "Erro ao inicializar mapa", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        try {
            Log.d(TAG, "onMapReady - Mapa carregado com sucesso!");
            googleMap = map;

            // Adiciona marcador
            googleMap.addMarker(new MarkerOptions()
                    .position(cityLocation)
                    .title(cityName != null ? cityName : "São Paulo"));

            Log.d(TAG, "Marcador adicionado na posição: " + cityLocation.toString());

            // Move a câmera
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, 12));
            Log.d(TAG, "Câmera movida");

            // Configurações do mapa
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);

            Log.d(TAG, "Configurações do mapa aplicadas");

            if (getContext() != null) {
                Toast.makeText(getContext(), "Mapa carregado!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e(TAG, "Erro no onMapReady", e);
            if (getContext() != null) {
                Toast.makeText(getContext(), "Erro ao configurar mapa", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateCityLocation(String city) {
        if (city == null) {
            Log.w(TAG, "Cidade é null, usando São Paulo");
            return;
        }

        Log.d(TAG, "Atualizando localização para: " + city);

        switch (city.toLowerCase().trim()) {
            case "são paulo":
            case "sao paulo":
                cityLocation = new LatLng(-23.5505, -46.6333);
                break;
            case "rio de janeiro":
                cityLocation = new LatLng(-22.9068, -43.1729);
                break;
            case "brasília":
            case "brasilia":
                cityLocation = new LatLng(-15.7939, -47.8828);
                break;
            case "salvador":
                cityLocation = new LatLng(-12.9714, -38.5014);
                break;
            case "fortaleza":
                cityLocation = new LatLng(-3.7319, -38.5267);
                break;
            case "belo horizonte":
                cityLocation = new LatLng(-19.9167, -43.9345);
                break;
            case "manaus":
                cityLocation = new LatLng(-3.1190, -60.0217);
                break;
            case "curitiba":
                cityLocation = new LatLng(-25.4284, -49.2733);
                break;
            case "recife":
                cityLocation = new LatLng(-8.0476, -34.8770);
                break;
            case "porto alegre":
                cityLocation = new LatLng(-30.0346, -51.2177);
                break;
            default:
                Log.w(TAG, "Cidade não reconhecida: " + city + " - usando São Paulo");
                cityLocation = new LatLng(-23.5505, -46.6333);
                break;
        }

        Log.d(TAG, "Coordenadas: " + cityLocation.toString());
    }

    public void updateCity(String city) {
        Log.d(TAG, "updateCity chamado com: " + city);
        cityName = city;
        updateCityLocation(city);

        if (googleMap != null) {
            try {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions()
                        .position(cityLocation)
                        .title(cityName));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, 12));
                Log.d(TAG, "Mapa atualizado para nova cidade");
            } catch (Exception e) {
                Log.e(TAG, "Erro ao atualizar cidade", e);
            }
        } else {
            Log.w(TAG, "googleMap é null, não pode atualizar");
        }
    }
}