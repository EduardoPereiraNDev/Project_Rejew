package com.example.projeto_rejew;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaginaInicial extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_inicial);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void abrirMenu(View v) {
        Intent intent = new Intent(PaginaInicial.this, PerfilUsuario.class);
        startActivity(intent);
    }

    /*
    public void abrirMenu(View v) {
        imageView = findViewById(R.id.fotoperfil);
        PopupMenu popupMenu = new PopupMenu(CatalogoRejew.this, imageView);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
               switch (item.getItemId()) {
                    case R.id.ver_perfil:
                        Toast.makeText(CatalogoRejew.this, "Opção 1 selecionada", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.configuração:
                        Toast.makeText(CatalogoRejew.this, "Opção 2 selecionada", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.sair_conta:
                        sairConta(v);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }
*/

    public void abrirPessoas(View v) {
        Intent intent = new Intent(PaginaInicial.this, Pessoas_Comentarios.class);
        startActivity(intent);
    }

    public void abrirChats(View v) {
        Intent intent = new Intent(PaginaInicial.this, GenerosChat.class);
        startActivity(intent);
    }

    public void abrirCategoriaTerror(View v) {
        Intent intent = new Intent(PaginaInicial.this, Categorias.class);
        startActivity(intent);
    }

    public void sairConta(View v) {
        Intent intent = new Intent(PaginaInicial.this, Home.class);
        startActivity(intent);
    }

    public void abrirMaisComentados(View v) {
        Intent intent = new Intent(PaginaInicial.this, MaisComentados.class);
        startActivity(intent);
    }

    public void sairConta(MenuItem item) {
    }
}
