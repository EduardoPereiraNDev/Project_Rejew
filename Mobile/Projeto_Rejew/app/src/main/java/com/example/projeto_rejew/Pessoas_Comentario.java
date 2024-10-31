package com.example.projeto_rejew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_rejew.api.ComentarioAPIController;
import com.example.projeto_rejew.api.LivroAPIController;
import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;

public class Pessoas_Comentario extends AppCompatActivity {

    private UsuarioAPIController usuarioAPIController;
    private PesquisarPessoas pesquisarPessoas;
    private RecyclerView pessoas;

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

        pessoas = findViewById(R.id.perfilUsuarios);
        pessoas.setLayoutManager(new LinearLayoutManager(this));

        RetrofitClient retrofitClient = new RetrofitClient();
        usuarioAPIController = new UsuarioAPIController(retrofitClient);

        carregarUsuarios();
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
            public void onSuccessByte(byte[] bytes) {
            }

            @Override
            public void onSuccessList(List<Usuario> usuarios) {
                pesquisarPessoas = new PesquisarPessoas(Pessoas_Comentario.this, usuarios, usuarioAPIController);
                pessoas.setAdapter(pesquisarPessoas);
            }

            @Override
            public void onSuccessListL(List<Livro> livros) {
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

            @Override
            public void onSuccessV(Void body) {

            }
        });
    }

    public void pessoasPaginicial(View v) {
        Intent intent = new Intent(Pessoas_Comentario.this, CatalogoRejew.class);
        startActivity(intent);
    }

    public void pessoasGenerochat(View v) {
        Intent intent = new Intent(Pessoas_Comentario.this, GeneroChat.class);
        startActivity(intent);
    }
}
