package com.example.projeto_rejew;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_rejew.api.ChatAPIController;
import com.example.projeto_rejew.api.LivroAPIController;
import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Usuario;

public class PerfilUsuarioPessoal extends AppCompatActivity {

    private String emailEntrada;

    private TextView nomePerfil;
    private UsuarioAPIController usuarioAPIController;
    private RecyclerView recyclerViewLivro;
    private AdapterPerfilUsuarioP adapterLivro;

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

        nomePerfil = findViewById(R.id.nomePerfil);

        recyclerViewLivro = findViewById(R.id.recyclerViewLivro);
        recyclerViewLivro.setLayoutManager(new LinearLayoutManager(this));

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
                    nomePerfil.setText(usuario.getNomePerfil());
                    adapterLivro = new AdapterPerfilUsuarioP(PerfilUsuarioPessoal.this, usuario, usuarioAPIController);
                    recyclerViewLivro.setAdapter(adapterLivro);
                }
            }

            @Override
            public void onSuccessByte(byte[] bytes) {

            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(PerfilUsuarioPessoal.this);
                alerta.setCancelable(false);
                alerta.setTitle("Falha ao recarregar o catálogo");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
            }
        });
    }

}