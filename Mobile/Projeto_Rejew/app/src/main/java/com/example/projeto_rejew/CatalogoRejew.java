package com.example.projeto_rejew;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_rejew.api.LivroAPIController;
import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.entity.Livro;

import java.util.List;

public class CatalogoRejew extends AppCompatActivity {

    private LivroAdapter livroAdapter;
    private LivroAPIController livroAPIController;
    private ImageView imageView;

    private RecyclerView recyclerViewAventura;
    private RecyclerView recyclerViewTerror;
    private RecyclerView recyclerViewRomance;
    private RecyclerView recyclerViewFiccao;
    private RecyclerView recyclerViewCulinaria;
    private RecyclerView recyclerViewInfantil;

    private LivroAdapter adapterAventura;
    private LivroAdapter adapterTerror;
    private LivroAdapter adapterRomance;
    private LivroAdapter adapterFiccao;
    private LivroAdapter adapterCulinaria;
    private LivroAdapter adapterInfantil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_inicial);

        recyclerViewAventura = findViewById(R.id.recyclerViewAventura);
        recyclerViewTerror = findViewById(R.id.recyclerViewTerror);
        recyclerViewRomance = findViewById(R.id.recyclerViewRomance);
        recyclerViewFiccao = findViewById(R.id.recyclerViewFiccao);
        recyclerViewCulinaria = findViewById(R.id.recyclerViewCulinaria);
        recyclerViewInfantil = findViewById(R.id.recyclerViewInfantil);

        recyclerViewAventura.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTerror.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRomance.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFiccao.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCulinaria.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewInfantil.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RetrofitClient retrofitClient = new RetrofitClient();
        livroAPIController = new LivroAPIController(retrofitClient);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        carregarLivros();
    }

    private void carregarLivros() {
        livroAPIController.carregarLivros(new LivroAPIController.LivroCallback() {
            @Override
            public void onSuccess(Livro livro) {
                // Aqui você pode manipular o sucesso se receber um único livro
            }

            @Override
            public void onSuccessList(List<Livro> livroList) {
                atualizarRecyclerView(livroList);
            }

            @Override
            public void onSuccessByte(byte[] bytes) {
                // Você pode lidar com a resposta de bytes aqui, se necessário
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(CatalogoRejew.this);
                alerta.setCancelable(false);
                alerta.setTitle("Falha ao recarregar o catálogo");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
            }
        });
    }

    private void atualizarRecyclerView(List<Livro> livros) {
        if (livroAdapter == null) {
            livroAdapter = new LivroAdapter(this, livros, livroAPIController);
            recyclerView.setAdapter(livroAdapter);
        } else {
            livroAdapter.atualizarLista(livros);
        }
    }

    public void abrirMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(CatalogoRejew.this, imageView);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(CatalogoRejew.this, item.getItemId() + ", " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popupMenu.show();
    }
    public void sairConta(MenuItem item) {
        Intent intent = new Intent(CatalogoRejew.this, MainActivity.class);
        startActivity(intent);
    }
}
