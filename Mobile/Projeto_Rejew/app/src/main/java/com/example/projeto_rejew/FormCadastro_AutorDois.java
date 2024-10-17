package com.example.projeto_rejew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FormCadastro_AutorDois extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formcadastro_autordois);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void voltarPaginaAnterior(View v) {
        Intent intent = new Intent(FormCadastro_AutorDois.this, FormCadastro_AutorUm.class);
        startActivity(intent);
    }

    public void cadastrarAutor(View v) {
        Intent intent = new Intent(FormCadastro_AutorDois.this, MainActivity.class);
        startActivity(intent);
    }
}
