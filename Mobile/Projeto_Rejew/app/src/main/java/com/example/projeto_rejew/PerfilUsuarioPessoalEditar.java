package com.example.projeto_rejew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Usuario;

public class PerfilUsuarioPessoalEditar extends AppCompatActivity {

    private UsuarioAPIController usuarioAPIController;
    private String emailEntrada;
    private TextView emailUsuario;
    private EditText nomePerfil;
    private EditText nomeUsuario;
    private EditText senha;
    private EditText dataNascimento;
    private EditText recadoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_usuario_pessoal_editar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailUsuario = findViewById(R.id.emailUsuario);
        nomePerfil = findViewById(R.id.nomePerfil);
        nomeUsuario = findViewById(R.id.nomeUsuario);
        senha = findViewById(R.id.senha);
        dataNascimento = findViewById(R.id.dataNascimento);
        recadoPerfil = findViewById(R.id.recadoPerfil);

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
                    emailUsuario.setText(usuario.getEmailEntrada());
                    nomePerfil.setText(usuario.getNomePerfil());
                    nomeUsuario.setText(usuario.getNomeUsuario());
                    senha.setText(usuario.getSenhaEntrada());
                    dataNascimento.setText(usuario.getDataNascimento());
                    recadoPerfil.setText(usuario.getRecadoPerfil());
                }
            }

            @Override
            public void onSuccessBoolean(Boolean favoritado) {
            }
            @Override
            public void onSuccessByte(byte[] bytes) {
            }
            @Override
            public void onSuccessV(Void body) {
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void passarTelaCat(View view) {
        Intent intent = new Intent(PerfilUsuarioPessoalEditar.this, PerfilUsuarioPessoal.class);
        startActivity(intent);
    }
}