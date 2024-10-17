package com.example.projeto_rejew;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Usuario;

public class FormCadastro extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private EditText nome;
    private EditText nomeUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        nome = findViewById(R.id.nome);
        nomeUsuario = findViewById(R.id.nomeUsuario);
    }

    public void realizarCadastro(View view){

        String nome2 = nome.getText().toString();
        String nomeUsuario2 = nomeUsuario.getText().toString();
        String email2 = email.getText().toString();
        String senha2 = senha.getText().toString();

        if (nome2 == null || nomeUsuario2 == null || email2 == null || senha2 == null) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(FormCadastro.this);
            alerta.setCancelable(false);
            alerta.setTitle("Falha no Cadastro");
            alerta.setMessage("Dados Vazios ou invalidos");
            alerta.setNegativeButton("Voltar", null);
            alerta.create().show();
        }else {

            Usuario usuario = new Usuario(nome2, nomeUsuario2, email2, senha2);

            RetrofitClient retrofitClient;
            retrofitClient = new RetrofitClient();

            UsuarioAPIController userAPIController = new UsuarioAPIController(retrofitClient);
            userAPIController.cadastrarUsuario(usuario, new UsuarioAPIController.UsuarioCallback() {

                @Override
                public void onSuccess(Usuario usuario) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(FormCadastro.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Login");
                    alerta.setMessage("Cadastro realizado com sucesso, faça login novamente");
                    alerta.setNegativeButton("Ok", null);
                    alerta.create().show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                                Intent intent = new Intent(FormCadastro.this, CatalogoRejew.class);
                                FormCadastro.this.startActivity(intent);
                        }
                    }, 3000);
                }

                @Override
                public void onSuccessByte(byte[] bytes){
                }

                @Override
                public void onFailure(Throwable t) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(FormCadastro.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Cadastro");
                    alerta.setMessage("Falha ao realizar Cadastro" + t.getMessage());
                    alerta.setNegativeButton("Voltar", null);
                    alerta.create().show();
                }
            });
        }
    }

    public void passarTelaL(View v) {
        Intent intent = new Intent(FormCadastro.this, FormLogin.class);
        startActivity(intent);
    }
    public void passarTelaCat(View v) {
        Intent intent = new Intent(FormCadastro.this, CatalogoRejew.class);
        startActivity(intent);
    }
}