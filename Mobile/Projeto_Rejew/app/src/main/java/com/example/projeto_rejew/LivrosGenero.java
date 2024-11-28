package com.example.projeto_rejew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_rejew.api.LivroAPIController;
import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.entity.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivrosGenero extends AppCompatActivity {

    private TextView generoChatStr;
    private String generoChat;
    private RecyclerView recyclerView;
    private LivroAPIController livroAPIController;
    private LivroAdapter livroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_livros_genero);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        generoChatStr = findViewById(R.id.generoChat);

        generoChat = getIntent().getStringExtra("generoChat");

        generoChatStr.setText(generoChat);

        recyclerView = findViewById(R.id.recyclerLivrosGenero);
        int spanCount = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);

        RetrofitClient retrofitClient = new RetrofitClient();
        livroAPIController = new LivroAPIController(retrofitClient);

        carregarLivros();

    }

    private void carregarLivros() {
        livroAPIController.carregarLivros(new LivroAPIController.LivroCallback() {
            @Override
            public void onSuccess(Livro livro) { }

            @Override
            public void onSuccessList(List<Livro> livroList) {
                List<Livro> livros = new ArrayList<>();

                for (Livro livro : livroList) {
                    if (livro.getGeneroLivro().equalsIgnoreCase(generoChat)){
                        livros.add(livro);
                    }
                }
                livroAdapter = new LivroAdapter(LivrosGenero.this, livros, livroAPIController);
                recyclerView.setAdapter(livroAdapter);

            }

            @Override
            public void onSuccessByte(byte[] bytes) { }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(LivrosGenero.this);
                alerta.setCancelable(false);
                alerta.setTitle("Falha ao recarregar o catálogo");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
            }
        });
    }

    public void chatsPaginicial(View view) {
        finish();
    }

    public void paginicialChats(View v) {
        Intent intent = new Intent(LivrosGenero.this, GeneroChat.class);
        startActivity(intent);
    }
    public void paginicialPessoas(View v) {
        Intent intent = new Intent(LivrosGenero.this, Pessoas_Comentario.class);
        startActivity(intent);
    }
    public void passarCat(View v) {
        Intent intent = new Intent(LivrosGenero.this, CatalogoRejew.class);
        startActivity(intent);
    }
}