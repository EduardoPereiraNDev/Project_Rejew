package com.example.projeto_rejew;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FormSignUp_Autor extends AppCompatActivity {
    private Spinner menu;

    String[] opcoes = {"Sexo", "Masculino", "Feminino", "Outro"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_autorum);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        menu = findViewById(R.id.spinner);

        // Criando um ArrayAdapter personalizado para lidar com o hint
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, opcoes) {
            @Override
            public boolean isEnabled(int position) {
                // Desabilita o primeiro item (o hint)
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                if (position == 0) {
                    // Deixa o hint em cinza
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }

                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu.setAdapter(spinnerArrayAdapter);
    }

    public void passarTipoConta(View v){
        Intent intent = new Intent(FormSignUp_Autor.this, TipoContaSignUp.class);
        startActivity(intent);
    }

    public void voltarLogin(View v){
        Intent intent = new Intent(FormSignUp_Autor.this, FormLogin.class);
        startActivity(intent);
    }

    public void continuarCadastro(View v){
        Intent intent = new Intent(FormSignUp_Autor.this, FormAutor.class);
        startActivity(intent);
    }
}
