package com.example.weatherapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.CityAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CitySearchActivity - Tela de busca de cidades
 * Permite ao usuário buscar e selecionar uma cidade para ver a previsão
 */
public class CitySearchActivity extends AppCompatActivity {

    private CityAdapter adapter;
    private List<String> allCities;
    private List<String> filteredCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);

        // Configura Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Buscar Cidade");
        }

        initializeCities();
        setupRecyclerView();
        setupSearchView();
    }

    private void initializeCities() {
        // Lista de cidades brasileiras
        allCities = Arrays.asList(
                "São Paulo", "Rio de Janeiro", "Brasília", "Salvador",
                "Fortaleza", "Belo Horizonte", "Manaus", "Curitiba",
                "Recife", "Porto Alegre", "Belém", "Goiânia",
                "Guarulhos", "Campinas", "São Luís", "São Gonçalo",
                "Maceió", "Duque de Caxias", "Natal", "Teresina",
                "Campo Grande", "Nova Iguaçu", "São Bernardo do Campo",
                "João Pessoa", "Santo André", "Osasco", "Jaboatão dos Guararapes",
                "São José dos Campos", "Ribeirão Preto", "Uberlândia",
                "Sorocaba", "Contagem", "Aracaju", "Feira de Santana",
                "Cuiabá", "Joinville", "Juiz de Fora", "Londrina",
                "Aparecida de Goiânia", "Ananindeua", "Porto Velho",
                "Serra", "Niterói", "Belford Roxo", "Caxias do Sul",
                "Macapá", "Campos dos Goytacazes", "Florianópolis",
                "Vila Velha", "Mauá", "São João de Meriti", "Vitória",
                "Blumenau", "Carapicuíba", "Pelotas", "Canoas",
                "Santos", "Piracicaba", "Bauru", "Jundiaí",
                "Franca", "São Vicente", "Itaquaquecetuba", "Guarujá",
                "Caruaru", "Petrolina", "Montes Claros", "Palmas",
                "Boa Vista", "Rio Branco"
        );

        filteredCities = new ArrayList<>(allCities);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cria adapter com listener de clique
        adapter = new CityAdapter(filteredCities, city -> {
            // Quando uma cidade é clicada, retorna para MainActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selected_city", city);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        recyclerView.setAdapter(adapter);
    }

    private void setupSearchView() {
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Digite o nome da cidade");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCities(newText);
                return true;
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filterCities(String query) {
        filteredCities.clear();

        if (query.isEmpty()) {
            // Se busca vazia, mostra todas as cidades
            filteredCities.addAll(allCities);
        } else {
            // Filtra cidades que contém o texto digitado
            String normalizedQuery = normalizeString(query);
            for (String city : allCities) {
                String normalizedCity = normalizeString(city);
                if (normalizedCity.contains(normalizedQuery)) {
                    filteredCities.add(city);
                }
            }
        }

        adapter.notifyDataSetChanged();

        if (filteredCities.isEmpty()) {
            Toast.makeText(this, "Nenhuma cidade encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    private String normalizeString(String text) {
        // Converte para minúsculas
        String lowerCaseText = text.toLowerCase();
        // Remove acentos e caracteres diacríticos
        String normalizedText = Normalizer.normalize(lowerCaseText, Normalizer.Form.NFD);
        return normalizedText.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Botão voltar da toolbar
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}