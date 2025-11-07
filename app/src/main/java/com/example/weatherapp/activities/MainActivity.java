package com.example.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
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

    private static final String TAG = "MainActivity";
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private String currentCity = "São Paulo";

    private final ActivityResultLauncher<ScanOptions> qrCodeLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                try {
                    if (result.getContents() != null) {
                        currentCity = result.getContents();
                        Toast.makeText(this, "Cidade alterada para: " + currentCity, Toast.LENGTH_SHORT).show();
                        updateWeatherFragment();
                    } else {
                        Toast.makeText(this, "Scan cancelado", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Erro no scanner QR", e);
                    Toast.makeText(this, "Erro ao processar QR Code", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_main);
            Log.d(TAG, "Layout carregado com sucesso");

            // Configura a Toolbar
            setupToolbar();

            // Inicializa ViewPager e TabLayout
            setupViewPager();

        } catch (Exception e) {
            Log.e(TAG, "Erro no onCreate", e);
            Toast.makeText(this, "Erro ao inicializar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setupToolbar() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                Log.d(TAG, "Toolbar configurada");
            } else {
                Log.e(TAG, "Toolbar é null");
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao configurar toolbar", e);
        }
    }

    private void setupViewPager() {
        try {
            viewPager = findViewById(R.id.viewPager);
            tabLayout = findViewById(R.id.tabLayout);

            if (viewPager == null) {
                Log.e(TAG, "ViewPager é null - verifique activity_main.xml");
                Toast.makeText(this, "Erro: ViewPager não encontrado", Toast.LENGTH_LONG).show();
                return;
            }

            if (tabLayout == null) {
                Log.e(TAG, "TabLayout é null - verifique activity_main.xml");
                Toast.makeText(this, "Erro: TabLayout não encontrado", Toast.LENGTH_LONG).show();
                return;
            }

            ViewPagerAdapter adapter = new ViewPagerAdapter(this);
            viewPager.setAdapter(adapter);
            Log.d(TAG, "ViewPager configurado");

            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> {
                        try {
                            if (position == 0) {
                                tab.setText("Previsão");
                                tab.setIcon(R.drawable.ic_weather);
                            } else {
                                tab.setText("Mapa");
                                tab.setIcon(R.drawable.ic_map);
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Erro ao configurar tab " + position, e);
                        }
                    }
            ).attach();
            Log.d(TAG, "TabLayout configurado");

        } catch (Exception e) {
            Log.e(TAG, "Erro ao configurar ViewPager", e);
            Toast.makeText(this, "Erro ao configurar abas: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Erro ao criar menu", e);
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == R.id.action_about) {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.action_scan_qr) {
                scanQRCode();
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro no menu", e);
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void scanQRCode() {
        try {
            ScanOptions options = new ScanOptions();
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            options.setPrompt("Escaneie o QR Code da cidade");
            options.setCameraId(0);
            options.setBeepEnabled(true);
            options.setBarcodeImageEnabled(true);
            options.setOrientationLocked(false);

            qrCodeLauncher.launch(options);
        } catch (Exception e) {
            Log.e(TAG, "Erro ao iniciar scanner", e);
            Toast.makeText(this, "Erro ao abrir scanner: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateWeatherFragment() {
        try {
            if (viewPager != null) {
                viewPager.setCurrentItem(0, true);
                viewPager.post(() -> {
                    try {
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("f0");
                        if (fragment instanceof WeatherFragment) {
                            ((WeatherFragment) fragment).loadWeatherData(currentCity);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Erro ao atualizar fragment", e);
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao atualizar WeatherFragment", e);
        }
    }

    public String getCurrentCity() {
        return currentCity;
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull MainActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            try {
                if (position == 0) {
                    Log.d(TAG, "Criando WeatherFragment");
                    return WeatherFragment.newInstance(currentCity);
                } else {
                    Log.d(TAG, "Criando MapFragment");
                    return MapFragment.newInstance(currentCity);
                }
            } catch (Exception e) {
                Log.e(TAG, "Erro ao criar fragment na posição " + position, e);
                // Retorna um fragment padrão em caso de erro
                return WeatherFragment.newInstance(currentCity);
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}