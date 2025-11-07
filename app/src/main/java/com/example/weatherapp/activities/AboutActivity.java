package com.example.weatherapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.weatherapp.R;

/**
 * AboutActivity - Tela com informações pessoais do desenvolvedor
 */
public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "AboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_about);
            Log.d(TAG, "Layout carregado");

            // Configura a Toolbar
            setupToolbar();

            // Inicializa os componentes
            setupViews();

        } catch (Exception e) {
            Log.e(TAG, "Erro no onCreate", e);
            Toast.makeText(this, "Erro ao carregar tela Sobre: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setupToolbar() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setTitle("Sobre");
                }
                Log.d(TAG, "Toolbar configurada");
            } else {
                Log.e(TAG, "Toolbar não encontrada no layout");
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao configurar toolbar", e);
        }
    }

    private void setupViews() {
        try {
            // Busca os componentes
            ImageView imgProfile = findViewById(R.id.imgProfile);
            TextView txtName = findViewById(R.id.txtName);
            TextView txtRA = findViewById(R.id.txtRA);
            TextView txtCourse = findViewById(R.id.txtCourse);
            TextView txtDescription = findViewById(R.id.txtDescription);

            // Verifica se os componentes foram encontrados
            if (txtName == null) {
                Log.e(TAG, "txtName não encontrado");
            } else {
                txtName.setText("Marlon Mendes Marcondes");
                Log.d(TAG, "Nome configurado");
            }

            if (txtRA == null) {
                Log.e(TAG, "txtRA não encontrado");
            } else {
                txtRA.setText("RA: 09049837");
                Log.d(TAG, "RA configurado");
            }

            if (txtCourse == null) {
                Log.e(TAG, "txtCourse não encontrado");
            } else {
                txtCourse.setText("Curso: Programação de Dispositivos Móveis");
                Log.d(TAG, "Curso configurado");
            }

            if (txtDescription == null) {
                Log.e(TAG, "txtDescription não encontrado");
            } else {
                txtDescription.setText("Aplicativo desenvolvido como atividade acadêmica para " +
                        "demonstrar conhecimentos em desenvolvimento Android com Java, " +
                        "consumo de APIs REST, Material Design e integração com bibliotecas " +
                        "de terceiros como Google Maps e ZXing.");
                Log.d(TAG, "Descrição configurada");
            }

            // Tenta carregar a imagem
            if (imgProfile == null) {
                Log.e(TAG, "imgProfile não encontrado");
            } else {
                try {
                    imgProfile.setImageResource(R.drawable.dev);
                    Log.d(TAG, "Imagem configurada");
                } catch (Exception e) {
                    Log.e(TAG, "Erro ao carregar imagem dev - usando ícone padrão", e);
                    // Tenta usar ícone padrão se dev não existir
                    try {
                        imgProfile.setImageResource(R.drawable.ic_person);
                    } catch (Exception ex) {
                        Log.e(TAG, "Erro ao carregar ic_person também", ex);
                    }
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Erro ao configurar views", e);
            Toast.makeText(this, "Erro ao configurar informações", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try {
            if (item.getItemId() == android.R.id.home) {
                finish();
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro no menu", e);
        }
        return super.onOptionsItemSelected(item);
    }
}