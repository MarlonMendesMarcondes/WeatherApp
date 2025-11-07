package com.example.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.weatherapp.R;
import com.example.weatherapp.fragments.MapFragment;
import com.example.weatherapp.fragments.WeatherFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private String currentCity = "São Paulo";

    private final ActivityResultLauncher<Intent> citySearchLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String selectedCity = result.getData().getStringExtra("selected_city");
                    if (selectedCity != null) {
                        currentCity = selectedCity;
                        Toast.makeText(this, "Cidade: " + currentCity, Toast.LENGTH_SHORT).show();
                        updateFragments();
                    }
                }
            }
    );

    private final ActivityResultLauncher<ScanOptions> qrCodeLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                if (result.getContents() != null) {
                    currentCity = result.getContents();
                    Toast.makeText(this, "Cidade: " + currentCity, Toast.LENGTH_SHORT).show();
                    updateFragments();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("PREVISÃO");
                tab.setIcon(R.drawable.ic_weather);
            } else {
                tab.setText("MAPA");
                tab.setIcon(R.drawable.ic_map);
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search_city) {
            Intent intent = new Intent(this, CitySearchActivity.class);
            citySearchLauncher.launch(intent);
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (id == R.id.action_scan_qr) {
            ScanOptions options = new ScanOptions();
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            options.setPrompt("Escaneie o QR Code da cidade");
            qrCodeLauncher.launch(options);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateFragments() {
        Fragment weatherFragment = getSupportFragmentManager().findFragmentByTag("f0");
        if (weatherFragment instanceof WeatherFragment) {
            ((WeatherFragment) weatherFragment).loadWeatherData(currentCity);
        }

        Fragment mapFragment = getSupportFragmentManager().findFragmentByTag("f1");
        if (mapFragment instanceof MapFragment) {
            ((MapFragment) mapFragment).updateCity(currentCity);
        }
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull MainActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return position == 0 ? WeatherFragment.newInstance(currentCity) : MapFragment.newInstance(currentCity);
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}