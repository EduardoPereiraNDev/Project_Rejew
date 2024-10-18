package com.example.projeto_rejew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.example.projeto_rejew.api.ChatAPIController;
import com.example.projeto_rejew.api.LivroAPIController;
import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Chat;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CatalogoRejew extends AppCompatActivity {

    private LivroAdapter livroAdapter;
    private LivroAPIController livroAPIController;
    private ChatAPIController chatAPIController;
    private UsuarioAPIController usuarioAPIController;
    private ImageView imageView;
    private String caminhoImagem;

    private String emailEntrada;
    private Usuario usuario;
    private RecyclerView recyclerViewChat;
    private RecyclerView recyclerViewAventura;
    private RecyclerView recyclerViewTerror;
    private RecyclerView recyclerViewRomance;
    private RecyclerView recyclerViewFiccao;
    private RecyclerView recyclerViewCulinaria;
    private RecyclerView recyclerViewInfantil;

    private ChatAdapter adapterImagens;

    private LivroAdapter adapterAventura;
    private LivroAdapter adapterTerror;
    private LivroAdapter adapterRomance;
    private LivroAdapter adapterFiccao;
    private LivroAdapter adapterCulinaria;
    private LivroAdapter adapterInfantil;
    private CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_inicial);

        circleImageView = findViewById(R.id.fotoperfil);

        recyclerViewChat = findViewById(R.id.imagensChats);

        recyclerViewAventura = findViewById(R.id.recyclerViewAventura);
        recyclerViewTerror = findViewById(R.id.recyclerViewTerror);
        recyclerViewRomance = findViewById(R.id.recyclerViewRomance);
        recyclerViewFiccao = findViewById(R.id.recyclerViewFiccao);
        recyclerViewCulinaria = findViewById(R.id.recyclerViewCulinaria);
        recyclerViewInfantil = findViewById(R.id.recyclerViewInfantil);

        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewAventura.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTerror.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRomance.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFiccao.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCulinaria.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewInfantil.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RetrofitClient retrofitClient = new RetrofitClient();
        livroAPIController = new LivroAPIController(retrofitClient);

        chatAPIController = new ChatAPIController(retrofitClient);

        usuarioAPIController = new UsuarioAPIController(retrofitClient);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        carregarLivros();
        carregarChat();
        emailEntrada = recuperarEmailUsuario();
        carregarUsuario(emailEntrada);
        this.usuario = new Usuario();



    }

    private String recuperarEmailUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("usuarioDados", MODE_PRIVATE);
        return sharedPreferences.getString("emailEntrada", null);
    }

    private void carregarUsuario(String emailEntrada) {
        usuarioAPIController.buscarUsuario(emailEntrada, new UsuarioAPIController.UsuarioCallback() {

            @Override
            public void onSuccess(Usuario usuarioC) {
                if (usuarioC != null) {
                    usuario = usuarioC;
                    Log.d("DADOS", usuarioC.getCaminhoImagem() + usuarioC.getEmailEntrada());
                    caminhoImagem = usuarioC.getCaminhoImagem();
                    carregarImagemPerfil(caminhoImagem);
                }
            }

            @Override
            public void onSuccessByte(byte[] bytes) {

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

    private void carregarImagemPerfil(String caminhoImagem) {
        int larguraPadrao = 65;
        int alturaPadrao = 65;
        usuarioAPIController.carregarImagemPerfil(caminhoImagem, new UsuarioAPIController.UsuarioCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
            }
            @Override
            public void onSuccessByte(byte[] bytes) {
                Glide.with(CatalogoRejew.this)
                        .load(bytes)
                        .override(larguraPadrao,alturaPadrao)
                        .centerCrop()
                        .into(circleImageView);
            }
            @Override
            public void onFailure(Throwable t) {
                circleImageView.setImageResource(R.drawable.imagedefault);
            }
        });
    }

    private void carregarChat() {
        chatAPIController.carregarChats(new ChatAPIController.ChatCallback() {

            @Override
            public void onSuccess(Chat chat) {

            }

            @Override
            public void onSuccessByte(byte[] bytes) {

            }

            @Override
            public void onSuccessList(List<Chat> chatL) {
                adapterImagens = new ChatAdapter(CatalogoRejew.this, chatL, chatAPIController);
                recyclerViewChat.setAdapter(adapterImagens);
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


    private void carregarLivros() {
        livroAPIController.carregarLivros(new LivroAPIController.LivroCallback() {
            @Override
            public void onSuccess(Livro livro) { }

            @Override
            public void onSuccessList(List<Livro> livroList) {
                List<Livro> aventura = new ArrayList<>();
                List<Livro> terror = new ArrayList<>();
                List<Livro> romance = new ArrayList<>();
                List<Livro> ficcao = new ArrayList<>();
                List<Livro> culinaria = new ArrayList<>();
                List<Livro> infantil = new ArrayList<>();

                for (Livro livro : livroList) {
                    switch (livro.getGeneroLivro().toLowerCase()) {
                        case "aventura":
                            aventura.add(livro);
                            break;
                        case "terror":
                            terror.add(livro);
                            break;
                        case "romance":
                            romance.add(livro);
                            break;
                        case "ficçãocientifica":
                            ficcao.add(livro);
                            break;
                        case "culinária":
                            culinaria.add(livro);
                            break;
                        case "infantil":
                            infantil.add(livro);
                            break;
                    }
                }

                atualizarRecyclerViewAventura(aventura);
                atualizarRecyclerViewTerror(terror);
                atualizarRecyclerViewRomance(romance);
                atualizarRecyclerViewFiccao(ficcao);
                atualizarRecyclerViewCulinaria(culinaria);
                atualizarRecyclerViewInfantil(infantil);
            }

            @Override
            public void onSuccessByte(byte[] bytes) { }

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

    private void atualizarRecyclerViewAventura(List<Livro> livros) {
        adapterAventura = new LivroAdapter(this, livros, livroAPIController);
        recyclerViewAventura.setAdapter(adapterAventura);
    }

    private void atualizarRecyclerViewTerror(List<Livro> livros) {
        adapterTerror = new LivroAdapter(this, livros, livroAPIController);
        recyclerViewTerror.setAdapter(adapterTerror);
    }

    private void atualizarRecyclerViewRomance(List<Livro> livros) {
        adapterRomance = new LivroAdapter(this, livros, livroAPIController);
        recyclerViewRomance.setAdapter(adapterRomance);
    }

    private void atualizarRecyclerViewFiccao(List<Livro> livros) {
        adapterFiccao = new LivroAdapter(this, livros, livroAPIController);
        recyclerViewFiccao.setAdapter(adapterFiccao);
    }

    private void atualizarRecyclerViewCulinaria(List<Livro> livros) {
        adapterCulinaria = new LivroAdapter(this, livros, livroAPIController);
        recyclerViewCulinaria.setAdapter(adapterCulinaria);
    }

    private void atualizarRecyclerViewInfantil(List<Livro> livros) {
        adapterInfantil = new LivroAdapter(this, livros, livroAPIController);
        recyclerViewInfantil.setAdapter(adapterInfantil);
    }

    public void abrirMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(CatalogoRejew.this, circleImageView);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(CatalogoRejew.this, item.getItemId() + ", " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popupMenu.show();
    }

    public void irPerfil(MenuItem item) {
        Intent intent = new Intent(CatalogoRejew.this, PerfilUsuarioPessoal.class);
        startActivity(intent);
    }
    public void sairConta(MenuItem item) {
        Intent intent = new Intent(CatalogoRejew.this, MainActivity.class);
        startActivity(intent);
    }
    public void paginicialChats(View v) {
        Intent intent = new Intent(CatalogoRejew.this, GeneroChat.class);
        startActivity(intent);
    }
    public void paginicialPessoas(View v) {
        Intent intent = new Intent(CatalogoRejew.this, Pessoas_Comentario.class);
        startActivity(intent);
    }
}
