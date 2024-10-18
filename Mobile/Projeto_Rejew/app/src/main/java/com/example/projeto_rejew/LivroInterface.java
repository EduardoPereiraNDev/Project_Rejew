package com.example.projeto_rejew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.example.projeto_rejew.api.ComentarioAPIController;
import com.example.projeto_rejew.api.LivroAPIController;
import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LivroInterface extends AppCompatActivity {

    private LivroAPIController livroAPIController;
    private ComentarioAPIController comentarioAPIController;
    private UsuarioAPIController usuarioAPIController;
    private Usuario usuario;
    private Livro livroC;
    private String emailEntrada;
    private ImageView imagemLivro;
    private TextView autorLivro;
    private TextView generoLivro;
    private TextView qtdPaginas;
    private TextView tituloLivro;
    private TextView sinopse;
    private RatingBar ratingBar;
    private EditText comentEdit;

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
        usuarioAPIController = new UsuarioAPIController(retrofitClient);
        comentarioAPIController = new ComentarioAPIController(retrofitClient);


        imagemLivro = findViewById(R.id.imagemLivro);
        autorLivro = findViewById(R.id.autorLivro);
        generoLivro = findViewById(R.id.generoLivro);
        qtdPaginas = findViewById(R.id.qtdPaginas);
        tituloLivro = findViewById(R.id.tituloLivro);
        sinopse = findViewById(R.id.sinopse);
        ratingBar = findViewById(R.id.ratingBar);

        comentEdit = findViewById(R.id.addComentario);

        usuario = new Usuario();

        long isbn = getIntent().getLongExtra("isbnLivro", -1);
        buscarLivroID(isbn);
    }

    private String recuperarEmailUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("usuarioDados", MODE_PRIVATE);
        return sharedPreferences.getString("emailEntrada", null);
    }

    public void chamarComentarioAdd(View view) {
        adicionarComentario();
    }


    private void adicionarComentario(){
        String conteudoComent = comentEdit.getText().toString();

        SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
        Date data = new Date();
        String dataFormatada = formataData.format(data);
        emailEntrada = recuperarEmailUsuario();
        carregarUsuario(emailEntrada);

        Comentario comentario = new Comentario(dataFormatada, conteudoComent , usuario, livroC );
        comentarioAPIController.adicionarComentario(comentario, new ComentarioAPIController.ComentarioCallback() {
            @Override
            public void onSuccess(Comentario comentario) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(LivroInterface.this);
                alerta.setCancelable(false);
                alerta.setTitle("Login");
                alerta.setMessage("Seu comentario foi realizado com sucesso" + comentario.getUsuarioComent().getNomeUsuario());
                alerta.setNegativeButton("Ok", null);
                alerta.create().show();
            }

            @Override
            public void onSuccessList(List<Comentario> ComentarioL) {
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(LivroInterface.this);
                alerta.setCancelable(false);
                alerta.setTitle("Falha ao fazer Comentario");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
            }
        });
    }

    private void carregarUsuario(String emailEntrada) {
        usuarioAPIController.buscarUsuario(emailEntrada, new UsuarioAPIController.UsuarioCallback() {

            @Override
            public void onSuccess(Usuario usuarioC) {
                if (usuarioC != null) {
                    usuario = usuarioC;
                }
            }

            @Override
            public void onSuccessByte(byte[] bytes) {

            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(LivroInterface.this);
                alerta.setCancelable(false);
                alerta.setTitle("Falha ao carregar usuario");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
            }
        });
    }

    private void buscarLivroID(long isbn){
        livroAPIController.buscarLivroPorId(isbn, new LivroAPIController.LivroCallback() {
            @Override
            public void onSuccess(Livro livro) {
                livroC = new Livro();
                livroC = livro;
                tituloLivro.setText(livro.getNomeLivro());
                autorLivro.setText(livro.getAutorLivro());
                generoLivro.setText(livro.getGeneroLivro());
                qtdPaginas.setText("Pag ."+ + livro.getNumeroPag());
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