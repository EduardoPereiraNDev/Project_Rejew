package com.example.projeto_rejew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;

public class Pessoas_Comentario extends AppCompatActivity {

    private UsuarioAPIController usuarioAPIController;
    private PesquisarPessoas pesquisarPessoas;
    private List<Usuario> usuariosPesquisados;
    private String emailEntrada;
    private Usuario usuarioPe;
    private RecyclerView pessoas;
    private EditText pesquisaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pessoas_comentario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usuariosPesquisados = new ArrayList<>();

        pesquisaText = findViewById(R.id.pesquisarPessoas);

        pessoas = findViewById(R.id.perfilUsuarios);
        pessoas.setLayoutManager(new LinearLayoutManager(this));

        RetrofitClient retrofitClient = new RetrofitClient();
        usuarioAPIController = new UsuarioAPIController(retrofitClient);

        emailEntrada = recuperarEmailUsuario();

        carregarUsuario(emailEntrada);

    }

    private String recuperarEmailUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("usuarioDados", MODE_PRIVATE);
        return sharedPreferences.getString("emailEntrada", null);
    }

    private void carregarUsuario(String emailEntrada) {
        usuarioAPIController.buscarUsuario(emailEntrada, new UsuarioAPIController.UsuarioCallback() {

            @Override
            public void onSuccess(Usuario usuario) {
                usuarioPe = usuario;
                carregarUsuarios();
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
                AlertDialog.Builder alerta = new AlertDialog.Builder(Pessoas_Comentario.this);
                alerta.setCancelable(false);
                alerta.setTitle("Falha ao carregar o livro");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
            }
        });
    }


    public void pesquisarPessoas(View view) {
        usuariosPesquisados.clear();
        String pesquisa = pesquisaText.getText().toString();
        Log.d("TAG", "pesquisarPessoas: "+ pesquisa);

        if (pesquisa == null || pesquisa.isEmpty()) {
            carregarUsuarios();
        }else {
            usuarioAPIController.buscarUsuariosNomeP(pesquisa, new UsuarioAPIController.UsuarioCallback() {
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
                    adicionarUsuarios(usuarios);
                    atualizarListaUsuarios();
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
                    if (t.getMessage() != null && t.getMessage().contains("404")) {

                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(Pessoas_Comentario.this);
                        alerta.setCancelable(false);
                        alerta.setTitle("Falha ao carregar o livro");
                        alerta.setMessage("Error: " + t.getMessage());
                        alerta.setNegativeButton("Voltar", null);
                        alerta.create().show();
                    }
                }
            });
            usuarioAPIController.buscarUsuariosNome(pesquisa, new UsuarioAPIController.UsuarioCallback() {
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
                    adicionarUsuarios(usuarios);
                    atualizarListaUsuarios();
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
                    if (t.getMessage() != null && t.getMessage().contains("404")) {

                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(Pessoas_Comentario.this);
                        alerta.setCancelable(false);
                        alerta.setTitle("Falha ao carregar o livro");
                        alerta.setMessage("Error: " + t.getMessage());
                        alerta.setNegativeButton("Voltar", null);
                        alerta.create().show();
                    }
                }
            });
        }
    }

    private void adicionarUsuarios(List<Usuario> novosUsuarios) {
        for (Usuario usuario : novosUsuarios) {
            if (!usuariosPesquisados.contains(usuario)) {
                usuariosPesquisados.add(usuario);
            }
        }
    }


    private void atualizarListaUsuarios() {
        List<Usuario> usuariosFiltrados = usuariosPesquisados.stream()
                .filter(usuario -> !usuario.equals(usuarioPe))
                .collect(Collectors.toList());
        if (pesquisarPessoas == null) {
            pesquisarPessoas = new PesquisarPessoas(Pessoas_Comentario.this, usuariosFiltrados, usuarioAPIController);
            pessoas.setAdapter(pesquisarPessoas);
        } else {
            pesquisarPessoas = null;
            pesquisarPessoas = new PesquisarPessoas(Pessoas_Comentario.this, usuariosFiltrados, usuarioAPIController);
            pessoas.setAdapter(pesquisarPessoas);
        }
    }

    private void carregarUsuarios(){
        usuarioAPIController.listarUsuarios(new UsuarioAPIController.UsuarioCallback() {
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
                List<Usuario> usuariosFiltrados = usuarios.stream()
                        .filter(usuario -> !usuario.equals(usuarioPe))
                        .collect(Collectors.toList());
                pesquisarPessoas = new PesquisarPessoas(Pessoas_Comentario.this, usuariosFiltrados, usuarioAPIController);
                pessoas.setAdapter(pesquisarPessoas);
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
                if (t.getMessage() != null && t.getMessage().contains("404")) {

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Pessoas_Comentario.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Falha ao carregar o livro");
                    alerta.setMessage("Error: " + t.getMessage());
                    alerta.setNegativeButton("Voltar", null);
                    alerta.create().show();
                }
            }


        });
    }

    public void paginicialChats(View v) {
        Intent intent = new Intent(Pessoas_Comentario.this, GeneroChat.class);
        startActivity(intent);
    }
    public void paginicialPessoas(View v) {
        Intent intent = new Intent(Pessoas_Comentario.this, Pessoas_Comentario.class);
        startActivity(intent);
    }
    public void passarCat(View v) {
        Intent intent = new Intent(Pessoas_Comentario.this, CatalogoRejew.class);
        startActivity(intent);
    }

    public void VoltarFinish(View view) {
        finish();
    }

}
