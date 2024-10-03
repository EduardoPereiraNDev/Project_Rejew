package com.example.projeto_rejew;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FormSignUp_Autor extends AppCompatActivity {

    private TextView texto;
    private Button botao;
    private Spinner menu;

    String[] opcoes = {"opcao 1", "opcao 2", "opcao 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_autor);
        texto = findViewById(R.id.textView);
        botao = findViewById(R.id.button);
        menu = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_signup_autor, opcoes);
        menu.setAdapter(adapter);
        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                texto.setText(opcoes[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FormSignUp_Autor.this, "Escolheu " + opcoes[menu.getSelectedItemPosition()], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
