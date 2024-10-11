package com.example.projeto_rejew;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projeto_rejew.api.LivroAPIController;
import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.entity.Livro;

import java.util.List;

public class LivroInterface extends AppCompatActivity {

    LivroAPIController livroAPIController;

    private ImageView imagemLivro;
    private TextView autorLivro;
    private TextView generoLivro;
    private TextView qtdPaginas;
    private TextView tituloLivro;
    private TextView sinopse;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_livro_interface);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RetrofitClient retrofitClient = new RetrofitClient();
        livroAPIController = new LivroAPIController(retrofitClient);

        imagemLivro = findViewById(R.id.imagemLivro);
        autorLivro = findViewById(R.id.autorLivro);
        generoLivro = findViewById(R.id.generoLivro);
        qtdPaginas = findViewById(R.id.qtdPaginas);
        tituloLivro = findViewById(R.id.tituloLivro);
        sinopse = findViewById(R.id.sinopse);
        ratingBar = findViewById(R.id.ratingBar);

        long isbn = getIntent().getLongExtra("isbnLivro", -1);
        buscarLivroID(isbn);
    }

    private void buscarLivroID(long isbn){
        livroAPIController.buscarLivroPorId(isbn, new LivroAPIController.LivroCallback() {
            @Override
            public void onSuccess(Livro livro) {
                tituloLivro.setText(livro.getNomeLivro());
                autorLivro.setText(livro.getAutorLivro());
                generoLivro.setText(livro.getGeneroLivro());
                qtdPaginas.setText(String.valueOf(livro.getNumeroPag()));
                sinopse.setText(livro.getSinopseLivro());
                ratingBar.setRating(livro.getNotaLivro());

                View viewFundo = findViewById(R.id.viewFundo);
                viewFundo.setBackgroundColor(Color.parseColor(livro.getCorPrimaria()));

                carregarImagem(livro.getCaminhoImgCapa());
            }

            @Override
            public void onSuccessList(List<Livro> livroList) {
            }

            @Override
            public void onSuccessByte(byte[] bytes) {
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(LivroInterface.this);
                alerta.setCancelable(false);
                alerta.setTitle("Falha ao carregar o livro");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
            }
        });
    }

    private void carregarImagem(String caminhoImgCapa) {
        int larguraPadrao = 230;
        int alturaPadrao = 330;
        livroAPIController.retornarImagem(new LivroAPIController.LivroCallback() {
            @Override
            public void onSuccessByte(byte[] bytes) {
                Glide.with(LivroInterface.this)
                        .load(bytes)
                        .override(larguraPadrao, alturaPadrao)
                        .centerCrop()
                        .into(imagemLivro);
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(LivroInterface.this);
                alerta.setCancelable(false);
                alerta.setTitle("Falha ao carregar a imagem do livro");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
            }

            @Override
            public void onSuccess(Livro livro) {
            }

            @Override
            public void onSuccessList(List<Livro> livroList) {
            }
        }, caminhoImgCapa);
    }


    public void passarTelaCat(View view) {
        Intent intent = new Intent(LivroInterface.this, CatalogoRejew.class);
        startActivity(intent);
    }
}