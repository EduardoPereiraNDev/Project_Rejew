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
import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.reflect.TypesJVMKt;
import okhttp3.ResponseBody;

public class PerfilUsuario extends AppCompatActivity implements ComentarioDeleteCallback {

    private String emailEntrada;
    private String emailEntradaPessoal;
    private RecyclerView recyclerViewFavoritados;
    private RecyclerView recyclerViewComentarios;
    private Usuario usuarioPe;
    private LivroAdapter livroAdapter;
    private AdapterComentario adapterComentario;

    private AppCompatButton buttonSeguir;
    private ImageView fotoFundo;
    private Boolean estaSeguindo;
    private CircleImageView fotoPerfil;

    private TextView nomeUsuario;
    private TextView nomePerfil;
    private TextView seguidoresQTD;
    private TextView seguindoQTD;
    private TextView comentariosQTD;
    private TextView bio;

    private ComentarioAPIController comentarioAPIController;
    private UsuarioAPIController usuarioAPIController;
    private LivroAPIController livroAPIController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_usuario);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fotoPerfil = findViewById(R.id.fotoPerfil);
        fotoFundo = findViewById(R.id.fotoFundo);
        nomeUsuario = findViewById(R.id.nomeUsuario);
        nomePerfil = findViewById(R.id.nomePerfil);
        seguidoresQTD = findViewById(R.id.seguidoresQTD);
        seguindoQTD = findViewById(R.id.seguindoQTD);
        comentariosQTD = findViewById(R.id.comentariosQTD);
        bio = findViewById(R.id.bio);

        buttonSeguir = findViewById(R.id.buttonSeguir);

        recyclerViewFavoritados = findViewById(R.id.livrosfavoritados);
        recyclerViewComentarios = findViewById(R.id.comentariosUsuario);

        recyclerViewFavoritados.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewComentarios.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        RetrofitClient retrofitClient = new RetrofitClient();
        comentarioAPIController = new ComentarioAPIController(retrofitClient);
        usuarioAPIController = new UsuarioAPIController(retrofitClient);

        emailEntradaPessoal = recuperarEmailUsuario();

        livroAPIController = new LivroAPIController(retrofitClient);

        emailEntrada =  getIntent().getStringExtra("emailEntrada");

        carregarUsuario(emailEntrada);
        carregarLivrosFavoritados(emailEntrada);
        qtdSeguindo(emailEntrada);
        qtdSeguidores(emailEntrada);
        carregarUsuarioPessoal();
        verificarEstaSeguindo();
    }

    private String recuperarEmailUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("usuarioDados", MODE_PRIVATE);
        return sharedPreferences.getString("emailEntrada", null);
    }

    @Override
    public void onComentarioDeleted() {
        Toast.makeText(this, "Comentário deletado com sucesso!", Toast.LENGTH_SHORT).show();
        buscarcomentarioUsuario(emailEntrada);
    }

    private void carregarUsuarioPessoal() {
        usuarioAPIController.buscarUsuario(emailEntradaPessoal, new UsuarioAPIController.UsuarioCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                if (usuario != null) {
                    usuarioPe = usuario;
                }
            }

            @Override
            public void onSuccessBoolean(Boolean booleano) {

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

            }
        });
    }

    private void carregarUsuario(String emailEntrada) {
        usuarioAPIController.buscarUsuario(emailEntrada, new UsuarioAPIController.UsuarioCallback() {

            @Override
            public void onSuccess(Usuario usuario) {
                if (usuario != null) {
                    nomeUsuario.setText(usuario.getNomeUsuario());
                    nomePerfil.setText(usuario.getNomePerfil());
                    comentariosQTD.setText(String.valueOf(usuario.getComentario().size()));
                    bio.setText(usuario.getRecadoPerfil());

                    buscarcomentarioUsuario(emailEntrada);

                    usuarioAPIController.carregarImagemPerfil(usuario.getCaminhoImagem(), new UsuarioAPIController.UsuarioCallback() {
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
                            int larguraPadrao = 180;
                            int alturaPadrao = 180;
                            Glide.with(PerfilUsuario.this)
                                    .load(bytes)
                                    .override(larguraPadrao, alturaPadrao)
                                    .centerCrop()
                                    .into(fotoPerfil);
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
                            Log.e("UsuarioAdapter", "Falha ao carregar a imagem: " + t.getMessage());
                            fotoPerfil.setImageResource(R.drawable.imagedefault);
                        }
                    });

                    DisplayMetrics displayMetrics = PerfilUsuario.this.getResources().getDisplayMetrics();
                    int larguraTela = displayMetrics.widthPixels;
                    usuarioAPIController.carregarImagemFundo(usuario.getCaminhoImagemFundo(), new UsuarioAPIController.UsuarioCallback() {
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
                            int alturaPadrao = 650;
                            Glide.with(PerfilUsuario.this)
                                    .load(bytes)
                                    .override(larguraTela, alturaPadrao)
                                    .centerCrop()
                                    .into(fotoFundo);
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
                            Log.e("UsuarioAdapter", "Falha ao carregar a imagem: " + t.getMessage());
                            fotoFundo.setImageResource(R.drawable.imagedefault);
                        }


                    });
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
                Log.e("UsuarioAPI", "Falha ao carregar o usuário: " + t.getMessage());
            }


        });
    }

    private void qtdSeguindo(String emailEntrada) {
        usuarioAPIController.qtdSeguindo(emailEntrada, new UsuarioAPIController.UsuarioCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
            }

            @Override
            public void onSuccessBoolean(Boolean favoritado) {
            }

            @Override
            public void onSuccessInt(Integer integer) {
                seguindoQTD.setText(String.valueOf(integer));
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
                Log.e("UsuarioAPI", "Falha ao carregar qtd seguidores: " + t.getMessage());
            }

        });
    }

    private void qtdSeguidores(String emailEntrada) {
        usuarioAPIController.qtdSeguidores(emailEntrada, new UsuarioAPIController.UsuarioCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
            }

            @Override
            public void onSuccessBoolean(Boolean favoritado) {
            }

            @Override
            public void onSuccessInt(Integer integer) {
                seguidoresQTD.setText(String.valueOf(integer));
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
                Log.e("UsuarioAPI", "Falha ao carregar qtd Seguindo : " + t.getMessage());
            }

        });
    }


    private void carregarLivrosFavoritados(String emailEntrada) {
        usuarioAPIController.verFavoritados(emailEntrada, new UsuarioAPIController.UsuarioCallback() {
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
                livroAdapter = new LivroAdapter(PerfilUsuario.this, livros, livroAPIController);
                recyclerViewFavoritados.setAdapter(livroAdapter);
            }

            @Override
            public void onSuccessResponse(ResponseBody body) {

            }

            @Override
            public void onSuccessString(String string) {

            }


            @Override
            public void onFailure(Throwable t) {
                Log.e("UsuarioAPI", "Falha ao carregar livros favoritados: " + t.getMessage());
            }

        });
    }

    private void buscarcomentarioUsuario(String emailEntrada){
        comentarioAPIController.buscarComentarioPorUsuario(emailEntrada, new ComentarioAPIController.ComentarioCallback() {
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
                adapterComentario = new AdapterComentario(PerfilUsuario.this, comentarioL,usuarioPe, usuarioAPIController, comentarioAPIController, PerfilUsuario.this);
                recyclerViewComentarios.setAdapter(adapterComentario);
                adapterComentario.notifyDataSetChanged();
            }

            @Override
            public void onSuccessUsuario(Usuario usuario) {

            }

            @Override
            public void onFailure(Throwable t) {
                if (t.getMessage() != null && t.getMessage().contains("404")) {

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(PerfilUsuario.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Falha ao carregar o livro");
                    alerta.setMessage("Error: " + t.getMessage());
                    alerta.setNegativeButton("Voltar", null);
                    alerta.create().show();
                }
            }
        });
    }




    public void passarCat(View view) {
        Intent intent = new Intent(PerfilUsuario.this, Pessoas_Comentario.class);
        startActivity(intent);
    }

    public void SeguirOuDeixar(View view) {
        if (estaSeguindo) {
            usuarioAPIController.deixarSeguirUsuario(emailEntradaPessoal, emailEntrada, new UsuarioAPIController.UsuarioCallback() {

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
                    qtdSeguidores(emailEntrada);
                    estaSeguindo = false;
                    atualizarBotaoFavoritar(estaSeguindo);
                }

                @Override
                public void onSuccessString(String string) {

                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("API Response", "Erro ao desfavoritar: " + t.getMessage());
                }
            });
        } else {
            usuarioAPIController.seguirUsuario(emailEntradaPessoal, emailEntrada, new UsuarioAPIController.UsuarioCallback() {

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
                    qtdSeguidores(emailEntrada);
                    estaSeguindo = true;
                    atualizarBotaoFavoritar(estaSeguindo);
                }

                @Override
                public void onSuccessString(String string) {

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
            buttonSeguir.setText("Deixar de Seguir");
            buttonSeguir.setBackgroundResource(R.drawable.buttondesf);
            buttonSeguir.setTextAppearance(this, R.style.ButtonDesf);
        } else {
            buttonSeguir.setText("Seguir");
            buttonSeguir.setBackgroundResource(R.drawable.buttonfunc);
            buttonSeguir.setTextAppearance(this, R.style.ButtonFavo);
        }
    }



    private void verificarEstaSeguindo() {
        usuarioAPIController.estaSeguindoUsuario(emailEntradaPessoal, emailEntrada, new UsuarioAPIController.UsuarioCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
            }

            @Override
            public void onSuccessBoolean(Boolean favoritado) {
                estaSeguindo = favoritado;
                atualizarBotaoFavoritar(estaSeguindo);
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
}
