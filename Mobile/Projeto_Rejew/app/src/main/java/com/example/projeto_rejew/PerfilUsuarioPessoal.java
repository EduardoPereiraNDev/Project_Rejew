package com.example.projeto_rejew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.projeto_rejew.entity.Usuario;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilUsuarioPessoal extends AppCompatActivity {

    private String emailEntrada;

    public ImageView fotoFundo;
    public CircleImageView fotoPerfil;
    public TextView nomeUsuario;
    public TextView nomePerfil;
    public TextView seguidoresQTD;
    public TextView seguindoQTD;
    public TextView comentariosQTD;
    public TextView bio;
    private UsuarioAPIController usuarioAPIController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_usuario_pessoal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fotoPerfil = findViewById(R.id.fotoPerfil);
        fotoFundo= findViewById(R.id.fotoFundo);
        nomeUsuario = findViewById(R.id.nomeUsuario);
        nomePerfil = findViewById(R.id.nomePerfil);
        seguidoresQTD = findViewById(R.id.seguidoresQTD);
        seguindoQTD = findViewById(R.id.seguindoQTD);
        comentariosQTD = findViewById(R.id.comentariosQTD);
        bio = findViewById(R.id.bio);

        RetrofitClient retrofitClient = new RetrofitClient();
        usuarioAPIController = new UsuarioAPIController(retrofitClient);

        emailEntrada = recuperarEmailUsuario();

        carregarUsuario(emailEntrada);
        Log.d("TAG", "Dados:"+ emailEntrada );
    }
    private String recuperarEmailUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("usuarioDados", MODE_PRIVATE);
        return sharedPreferences.getString("emailEntrada", null);
    }

    private void carregarUsuario(String emailEntrada) {
        usuarioAPIController.buscarUsuario(emailEntrada, new UsuarioAPIController.UsuarioCallback() {

            @Override
            public void onSuccess(Usuario usuario) {
                if (usuario != null) {

                    nomeUsuario.setText(usuario.getNomeUsuario());
                    nomePerfil.setText(usuario.getNomePerfil());
                    seguidoresQTD.setText(String.valueOf(usuario.getUsuariosSeguido().size()));
                    seguindoQTD.setText(String.valueOf(usuario.getUsuariosSeguindo().size()));
                    comentariosQTD.setText(String.valueOf(usuario.getComentario().size()));
                    bio.setText(usuario.getRecadoPerfil());

                    usuarioAPIController.carregarImagemPerfil(usuario.getCaminhoImagem(), new UsuarioAPIController.UsuarioCallback() {
                        @Override
                        public void onSuccess(Usuario usuario) {
                        }

                        @Override
                        public void onSuccessBoolean(Boolean favoritado) {
                        }

                        @Override
                        public void onSuccessByte(byte[] bytes) {
                            int larguraPadrao = 210;
                            int alturaPadrao = 210;
                            Glide.with(PerfilUsuarioPessoal.this)
                                    .load(bytes)
                                    .override(larguraPadrao, alturaPadrao)
                                    .centerCrop()
                                    .into(fotoPerfil);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("UsuarioAdapter", "Falha ao carregar a imagem: " + t.getMessage());
                            fotoPerfil.setImageResource(R.drawable.imagedefault);
                        }

                        @Override
                        public void onSuccessV(Void body) {

                        }
                    });
                    DisplayMetrics displayMetrics = PerfilUsuarioPessoal.this.getResources().getDisplayMetrics();
                    int larguraTela = displayMetrics.widthPixels;
                    usuarioAPIController.carregarImagemFundo(usuario.getCaminhoImagemFundo(), new UsuarioAPIController.UsuarioCallback() {
                            @Override
                            public void onSuccess(Usuario usuario) {
                            }

                            @Override
                            public void onSuccessBoolean(Boolean favoritado) {
                            }

                            @Override
                            public void onSuccessByte(byte[] bytes) {
                                int alturaPadrao = 650;
                                Glide.with(PerfilUsuarioPessoal.this)
                                        .load(bytes)
                                        .override(larguraTela, alturaPadrao)
                                        .centerCrop()
                                        .into(fotoFundo);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.e("UsuarioAdapter", "Falha ao carregar a imagem: " + t.getMessage());
                                fotoPerfil.setImageResource(R.drawable.imagedefault);
                            }

                            @Override
                            public void onSuccessV(Void body) {

                            }
                    });
                }
            }

            @Override
            public void onSuccessBoolean(Boolean favoritado) {
            }

            @Override
            public void onSuccessByte(byte[] bytes) {
            }

            @Override
            public void onFailure(Throwable t) {
            }

            @Override
            public void onSuccessV(Void body) {
            }
        });
    }

    public void editarPerfil(View view) {
        Intent intent = new Intent(PerfilUsuarioPessoal.this, PerfilUsuarioPessoalEditar.class);
        startActivity(intent);
    }

}