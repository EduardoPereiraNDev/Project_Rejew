package com.example.projeto_rejew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projeto_rejew.api.ComentarioAPIController;
import com.example.projeto_rejew.api.LivroAPIController;
import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.api.UsuarioLivroAPIController;
import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;
import com.example.projeto_rejew.entity.UsuarioLivroADTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;

public class LivroInterface extends AppCompatActivity implements ComentarioDeleteCallback {

    private LivroAPIController livroAPIController;
    private RecyclerView recyclerComentarios;
    private AdapterComentario adapterComentario;
    private ComentarioAPIController comentarioAPIController;
    private UsuarioAPIController usuarioAPIController;
    private UsuarioLivroAPIController usuarioLivroAPIController;
    private Usuario usuario;
    private Livro livroC;
    private long isbn;
    private Boolean favoritadoBool;
    private AppCompatButton buttonfavoritar;
    private AppCompatButton buttonavaliar;
    private String emailEntrada;
    private ImageView imagemLivro;
    private TextView autorLivro;
    private TextView generoLivro;
    private TextView qtdPaginas;
    private TextView tituloLivro;
    private TextView sinopse;
    private Boolean booleanAvaliado;
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

        buttonfavoritar = findViewById(R.id.buttonFavoritar);
        buttonavaliar = findViewById(R.id.buttonAvaliar);

        recyclerComentarios = findViewById(R.id.comentarios);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerComentarios.setLayoutManager(layoutManager);



        RetrofitClient retrofitClient = new RetrofitClient();
        livroAPIController = new LivroAPIController(retrofitClient);
        usuarioAPIController = new UsuarioAPIController(retrofitClient);
        comentarioAPIController = new ComentarioAPIController(retrofitClient);
        usuarioLivroAPIController = new UsuarioLivroAPIController(retrofitClient);


        imagemLivro = findViewById(R.id.imagemLivro);
        autorLivro = findViewById(R.id.autorLivro);
        generoLivro = findViewById(R.id.generoLivro);
        qtdPaginas = findViewById(R.id.qtdPaginas);
        tituloLivro = findViewById(R.id.tituloLivro);
        sinopse = findViewById(R.id.sinopse);
        ratingBar = findViewById(R.id.ratingBar);

        comentEdit = findViewById(R.id.addComentario);

        livroC = new Livro();
        usuario = new Usuario();

        booleanAvaliado = false;

         isbn = getIntent().getLongExtra("isbnLivro", -1);

        emailEntrada = recuperarEmailUsuario();
        carregarUsuario(emailEntrada);



        buscarLivroID(isbn);
        verificarFavoritacao(emailEntrada, isbn);
        carregarMedia();
        verificarAvaliado();
    }

    private String recuperarEmailUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("usuarioDados", MODE_PRIVATE);
        return sharedPreferences.getString("emailEntrada", null);
    }

    @Override
    public void onComentarioDeleted() {
        Toast.makeText(this, "Comentário deletado com sucesso!", Toast.LENGTH_SHORT).show();
        Log.d("TAG", "comentario deletado: ");
        buscarcomntarioLivro(isbn);
    }

    public void carregarMedia(){
        usuarioLivroAPIController.calcularMedia(isbn, new UsuarioLivroAPIController.UsuarioLivroCallback() {
            @Override
            public void onSuccess(UsuarioLivroADTO usuarioLivroADTO) {
            }

            @Override
            public void onSuccessList(List<UsuarioLivroADTO> usuariosLivroADTO) {

            }

            @Override
            public void onSuccessBoolean(Boolean booleano) {

            }

            @Override
            public void onSuccessDouble(Double doble) {
                if (doble == null || doble == 0){
                    ratingBar.setRating(0);
                }
                ratingBar.setRating(doble.floatValue());
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(LivroInterface.this);
                alerta.setCancelable(false);
                alerta.setTitle("Erro ao carregar Media");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
                Log.e("ERROR", "onFailure: "+ t );
            }
        });
    }

    public void mostrarDialogoDeAvaliacao(View view) {
        if (booleanAvaliado == true){
            Toast.makeText(LivroInterface.this, "Livro Já Avaliado!", Toast.LENGTH_SHORT).show();
        } else if (booleanAvaliado == false) {
            View dialogView = getLayoutInflater().inflate(R.layout.popup_ratingbar, null);

            RatingBar dialogRatingBar = dialogView.findViewById(R.id.dialogRatingBar);
            Button btnSubmitRating = dialogView.findViewById(R.id.btnSubmitRating);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);

            AlertDialog dialog = builder.create();
            dialog.show();
            btnSubmitRating.setOnClickListener(v -> {

                float rating = dialogRatingBar.getRating();
                avaliarLivro(rating);

                dialog.dismiss();
            });
        }
    }

    public void avaliarLivro(Float ratin){
        Double nota = ratin.doubleValue();
        usuarioLivroAPIController.adicionarAvaliacao(emailEntrada,isbn,nota , new UsuarioLivroAPIController.UsuarioLivroCallback() {
            @Override
            public void onSuccess(UsuarioLivroADTO usuarioLivroADTO) {
                Toast.makeText(LivroInterface.this, "Avaliação Enviada com sucesso!", Toast.LENGTH_SHORT).show();
                carregarMedia();
                verificarAvaliado();
            }

            @Override
            public void onSuccessList(List<UsuarioLivroADTO> usuariosLivroADTO) {

            }

            @Override
            public void onSuccessBoolean(Boolean booleano) {

            }

            @Override
            public void onSuccessDouble(Double doble) {
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(LivroInterface.this);
                alerta.setCancelable(false);
                alerta.setTitle("Falha ao Avaliar");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
            }
        });
    }

    public void verificarAvaliado(){
        usuarioLivroAPIController.verificarJaAvaliado(emailEntrada,isbn, new UsuarioLivroAPIController.UsuarioLivroCallback() {
            @Override
            public void onSuccess(UsuarioLivroADTO usuarioLivroADTO) {
            }

            @Override
            public void onSuccessList(List<UsuarioLivroADTO> usuariosLivroADTO) {

            }

            @Override
            public void onSuccessBoolean(Boolean booleano) {
                booleanAvaliado = booleano;
                atualizarBotaoAvaliar(booleano);
            }

            @Override
            public void onSuccessDouble(Double doble) {
                if (doble == null || doble == 0){
                    ratingBar.setRating(0);
                }
                ratingBar.setRating(doble.floatValue());
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(LivroInterface.this);
                alerta.setCancelable(false);
                alerta.setTitle("Erro ao carregar Media");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
                Log.e("ERROR", "onFailure: "+ t );
            }
        });
    }



    public void chamarComentarioAdd(View view) {
        adicionarComentario();
    }


    private void adicionarComentario(){
        String conteudoComent = comentEdit.getText().toString();

        SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
        Date data = new Date();
        String dataFormatada = formataData.format(data);


        Comentario comentario = new Comentario(dataFormatada, conteudoComent , usuario, livroC );
        comentarioAPIController.adicionarComentario(comentario, new ComentarioAPIController.ComentarioCallback() {
            @Override
            public void onSuccess(Comentario comentario) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(LivroInterface.this);
                alerta.setCancelable(false);
                alerta.setTitle("Login");
                alerta.setMessage("Seu comentario foi realizado com sucesso ");
                alerta.setNegativeButton("Ok", null);
                alerta.create().show();
                buscarcomntarioLivro(isbn);
                comentEdit.setText("");
            }

            @Override
            public void onSuccessString(String string) {

            }

            @Override
            public void onSuccessResponse(ResponseBody responseBody) {

            }

            @Override
            public void onSuccessList(List<Comentario> ComentarioL) {
            }

            @Override
            public void onSuccessUsuario(Usuario usuario) {

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
            public void onSuccessBoolean(Boolean favoritado) {
            }

            @Override
            public void onSuccessInt(Integer integer) {

            }

            @Override
            public void onSuccessByte(byte[] bytes) {

            }

            @Override
            public void onSuccessList(List<Usuario> usuarios) {

            }

            @Override
            public void onSuccessListL(List<Livro> livros) {

            }

            @Override
            public void onSuccessResponse(ResponseBody body) {

            }

            @Override
            public void onSuccessString(String string) {

            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(LivroInterface.this);
                alerta.setCancelable(false);
                alerta.setTitle("Falha ao carregar usuarioo");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
            }

        });
    }



    private void buscarLivroID(long isbnl){
        livroAPIController.buscarLivroPorId(isbnl, new LivroAPIController.LivroCallback() {
            @Override
            public void onSuccess(Livro livro) {
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

                buscarcomntarioLivro(isbn);
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

    private void buscarcomntarioLivro(long isbnl){
        comentarioAPIController.BuscarComentarioLivro(isbnl, new ComentarioAPIController.ComentarioCallback() {
            @Override
            public void onSuccess(Comentario comentario) {
            }

            @Override
            public void onSuccessString(String string) {

            }

            @Override
            public void onSuccessResponse(ResponseBody responseBody) {

            }

            @Override
            public void onSuccessList(List<Comentario> comentarioL) {
                if (adapterComentario == null) {
                    adapterComentario = new AdapterComentario(LivroInterface.this, comentarioL, usuario, usuarioAPIController, comentarioAPIController, LivroInterface.this);
                    recyclerComentarios.setAdapter(adapterComentario);
                } else {
                    adapterComentario.atualizarLista(comentarioL);
                }
            }

            @Override
            public void onSuccessUsuario(Usuario usuario) {

            }

            @Override
            public void onFailure(Throwable t) {
                if (t.getMessage() != null && t.getMessage().contains("404")) {

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(LivroInterface.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Falha ao carregar o livro");
                    alerta.setMessage("Error: " + t.getMessage());
                    alerta.setNegativeButton("Voltar", null);
                    alerta.create().show();
                }
            }
        });
    }

    public void favoritarLivroDesfv(View view) {
        if (favoritadoBool) {
            usuarioAPIController.desfavoritarLivro(emailEntrada, isbn, new UsuarioAPIController.UsuarioCallback() {
                @Override
                public void onSuccessString(String string) {

                }

                @Override
                public void onSuccess(Usuario usuario) {
                }

                @Override
                public void onSuccessBoolean(Boolean favoritado) {
                }

                @Override
                public void onSuccessInt(Integer integer) {

                }

                @Override
                public void onSuccessByte(byte[] bytes) {
                }

                @Override
                public void onSuccessList(List<Usuario> usuarios) {

                }

                @Override
                public void onSuccessListL(List<Livro> livros) {

                }

                @Override
                public void onSuccessResponse(ResponseBody body) {
                    favoritadoBool = false;
                    atualizarBotaoFavoritar(favoritadoBool);
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("API Response", "Erro ao desfavoritar: " + t.getMessage());
                }
            });
        } else {
            usuarioAPIController.favoritarLivro(emailEntrada, isbn, new UsuarioAPIController.UsuarioCallback() {
                @Override
                public void onSuccessString(String string) {

                }

                @Override
                public void onSuccess(Usuario usuario) {
                }

                @Override
                public void onSuccessBoolean(Boolean favoritado) {
                }

                @Override
                public void onSuccessInt(Integer integer) {

                }

                @Override
                public void onSuccessByte(byte[] bytes) {
                }

                @Override
                public void onSuccessList(List<Usuario> usuarios) {
                }

                @Override
                public void onSuccessListL(List<Livro> livros) {

                }

                @Override
                public void onSuccessResponse(ResponseBody body) {
                    favoritadoBool = true;
                    atualizarBotaoFavoritar(favoritadoBool);
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("API Response", "Erro ao favoritar: " + t.getMessage());
                }
            });
        }
    }

    private void atualizarBotaoFavoritar(Boolean favoritado) {
        if (favoritado) {
            buttonfavoritar.setText("Desfavoritar");
            buttonfavoritar.setBackgroundResource(R.drawable.buttondesf);
            buttonfavoritar.setTextAppearance(this, R.style.ButtonDesf);
        } else {
            buttonfavoritar.setText("Favoritar");
            buttonfavoritar.setBackgroundResource(R.drawable.buttonfunc);
            buttonfavoritar.setTextAppearance(this, R.style.ButtonFavo);
        }
    }

    private void atualizarBotaoAvaliar(Boolean avaliado) {
        if (avaliado) {
            buttonavaliar.setText("Avaliado");
            buttonavaliar.setBackgroundResource(R.drawable.buttondesf);
            buttonavaliar.setTextAppearance(this, R.style.ButtonDesf);
        } else {
            buttonavaliar.setText("Avaliar");
            buttonavaliar.setBackgroundResource(R.drawable.buttonfunc);
            buttonavaliar.setTextAppearance(this, R.style.ButtonFavo);
        }
    }



    private void verificarFavoritacao(String emailUsuario, Long isbnLivro) {

        usuarioAPIController.verFavoritadoLivro(emailUsuario, isbnLivro, new UsuarioAPIController.UsuarioCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
            }

            @Override
            public void onSuccessBoolean(Boolean favoritado) {
                favoritadoBool = favoritado;
                atualizarBotaoFavoritar(favoritadoBool);
            }

            @Override
            public void onSuccessInt(Integer integer) {

            }

            @Override
            public void onSuccessByte(byte[] bytes) {
            }

            @Override
            public void onSuccessList(List<Usuario> usuarios) {
            }

            @Override
            public void onSuccessListL(List<Livro> livros) {

            }

            @Override
            public void onSuccessResponse(ResponseBody body) {

            }

            @Override
            public void onSuccessString(String string) {

            }

            @Override
            public void onFailure(Throwable t){
            }
        });
    }





    public void passarTelaCat(View view) {
        Intent intent = new Intent(LivroInterface.this, CatalogoRejew.class);
        startActivity(intent);
    }


}