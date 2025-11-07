package com.example.weatherapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;

public class MapFragment extends Fragment {

    private static final String TAG = "MapFragment";
    private static final String ARG_CITY = "city";

    private String cityName;
    private WebView webView;
    private double latitude = -23.5505;
    private double longitude = -46.6333;

    public static MapFragment newInstance(String city) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city != null ? city : "São Paulo");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getArguments() != null) {
                cityName = getArguments().getString(ARG_CITY, "São Paulo");
                updateCityLocation(cityName);
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro no onCreate", e);
            cityName = "São Paulo";
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        try {
            // Cria FrameLayout programaticamente se o layout falhar
            View view;
            try {
                view = inflater.inflate(R.layout.fragment_map, container, false);
                webView = view.findViewById(R.id.mapWebView);
            } catch (Exception e) {
                Log.e(TAG, "Erro ao inflar layout, criando programaticamente", e);
                FrameLayout frameLayout = new FrameLayout(requireContext());
                webView = new WebView(requireContext());
                webView.setId(R.id.mapWebView);
                frameLayout.addView(webView, new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                ));
                view = frameLayout;
            }

            if (webView != null) {
                setupWebView();
                loadMap();
            } else {
                Log.e(TAG, "WebView é null");
                showError("Erro ao criar mapa");
            }

            return view;

        } catch (Exception e) {
            Log.e(TAG, "Erro fatal no onCreateView", e);
            showError("Erro ao carregar mapa");
            return createErrorView();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        try {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setBuiltInZoomControls(false);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.d(TAG, "Mapa carregado");
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Log.e(TAG, "Erro ao carregar mapa: " + description);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Erro ao configurar WebView", e);
        }
    }

    private void loadMap() {
        try {
            String html = createLeafletHtml();
            webView.loadDataWithBaseURL("https://unpkg.com", html, "text/html", "UTF-8", null);
            Log.d(TAG, "HTML carregado - Cidade: " + cityName);
        } catch (Exception e) {
            Log.e(TAG, "Erro ao carregar HTML", e);
            showError("Erro ao carregar mapa");
        }
    }

    private String createLeafletHtml() {
        String safeCityName = cityName != null ? cityName.replace("'", "\\'") : "São Paulo";

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='utf-8'/>" +
                "<meta name='viewport' content='width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no'/>" +
                "<link rel='stylesheet' href='https://unpkg.com/leaflet@1.9.4/dist/leaflet.css'/>" +
                "<style>body{margin:0;padding:0}#map{height:100vh;width:100%}</style>" +
                "</head>" +
                "<body>" +
                "<div id='map'></div>" +
                "<script src='https://unpkg.com/leaflet@1.9.4/dist/leaflet.js'></script>" +
                "<script>" +
                "try{" +
                "var map=L.map('map').setView([" + latitude + "," + longitude + "],12);" +
                "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',{" +
                "attribution:'OpenStreetMap',maxZoom:18}).addTo(map);" +
                "L.marker([" + latitude + "," + longitude + "]).addTo(map)" +
                ".bindPopup('<b>" + safeCityName + "</b>').openPopup();" +
                "}catch(e){console.error(e);}" +
                "</script>" +
                "</body>" +
                "</html>";
    }

    private void updateCityLocation(String city) {
        if (city == null || city.trim().isEmpty()) {
            city = "São Paulo";
        }

        String cityLower = city.toLowerCase().trim();

        // Mapa de coordenadas
        if (cityLower.contains("são paulo") || cityLower.contains("sao paulo")) {
            latitude = -23.5505; longitude = -46.6333;
        } else if (cityLower.contains("rio")) {
            latitude = -22.9068; longitude = -43.1729;
        } else if (cityLower.contains("brasília") || cityLower.contains("brasilia")) {
            latitude = -15.7939; longitude = -47.8828;
        } else if (cityLower.contains("salvador")) {
            latitude = -12.9714; longitude = -38.5014;
        } else if (cityLower.contains("fortaleza")) {
            latitude = -3.7319; longitude = -38.5267;
        } else if (cityLower.contains("belo horizonte")) {
            latitude = -19.9167; longitude = -43.9345;
        } else if (cityLower.contains("manaus")) {
            latitude = -3.1190; longitude = -60.0217;
        } else if (cityLower.contains("curitiba")) {
            latitude = -25.4284; longitude = -49.2733;
        } else if (cityLower.contains("recife")) {
            latitude = -8.0476; longitude = -34.8770;
        } else if (cityLower.contains("porto alegre")) {
            latitude = -30.0346; longitude = -51.2177;
        } else {
            // Padrão: São Paulo
            latitude = -23.5505; longitude = -46.6333;
        }
    }

    public void updateCity(String city) {
        try {
            cityName = city;
            updateCityLocation(city);
            if (webView != null) {
                loadMap();
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao atualizar cidade", e);
        }
    }

    private void showError(String message) {
        if (getContext() != null && isAdded()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private View createErrorView() {
        FrameLayout errorView = new FrameLayout(requireContext());
        errorView.setBackgroundColor(0xFFEEEEEE);
        return errorView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (webView != null) {
                webView.destroy();
                webView = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao destruir WebView", e);
        }
    }
}