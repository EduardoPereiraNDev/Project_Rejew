package com.example.projeto_rejew;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FormCadastro_AutorUm extends AppCompatActivity {
    private Spinner menu;

    String[] opcoes = {"Sexo", "Masculino", "Feminino"};


    private EditText senha;
    private ImageView olhoVisibilidade;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formcadastro_autorum);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        menu = findViewById(R.id.spinner);
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, opcoes) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu.setAdapter(spinnerArrayAdapter);


        senha = findViewById(R.id.senha);
        olhoVisibilidade = findViewById(R.id.olhoIcon);

        olhoVisibilidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    senha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    olhoVisibilidade.setImageResource(R.drawable.olhoon);
                } else {
                    senha.setInputType(InputType.TYPE_CLASS_TEXT);
                    olhoVisibilidade.setImageResource(R.drawable.olhooff);
                }


                senha.setSelection(senha.getText().length());
                isPasswordVisible = !isPasswordVisible;
            }
        });
    }

    public void passarTipoConta(View v) {
        Intent intent = new Intent(FormCadastro_AutorUm.this, EscolhaTipoConta.class);
        startActivity(intent);
    }

    public void voltarLogin(View v) {
        Intent intent = new Intent(FormCadastro_AutorUm.this, FormLogin.class);
        startActivity(intent);
    }

    public void continuarCadastro(View v) {
        Intent intent = new Intent(FormCadastro_AutorUm.this, FormCadastro_AutorDois.class);
        startActivity(intent);
    }
}
