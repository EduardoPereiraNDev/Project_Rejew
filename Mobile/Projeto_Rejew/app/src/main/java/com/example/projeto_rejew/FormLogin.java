package com.example.projeto_rejew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;
import java.util.Set;

public class FormLogin extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtPassword;
    private ImageView olhoVisibilidade;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.senha);
        olhoVisibilidade = findViewById(R.id.senhaicon);

        olhoVisibilidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    olhoVisibilidade.setImageResource(R.drawable.olhoon);
                } else {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    olhoVisibilidade.setImageResource(R.drawable.olhooff);
                }

                txtPassword.setSelection(txtPassword.getText().length());
                isPasswordVisible = !isPasswordVisible;
            }
        });
    }

    public void getLogin(View view) {
        String email = txtEmail.getText().toString();
        String senha = txtPassword.getText().toString();

        RetrofitClient retrofitClient;
        retrofitClient = new RetrofitClient();

        UsuarioAPIController userAPIController = new UsuarioAPIController(retrofitClient);
        userAPIController.getLoginUser(email, senha, new UsuarioAPIController.UsuarioCallback(){

            @Override
            public void onSuccess(Usuario usuario) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(FormLogin.this);
                if (usuario.getEmailEntrada() == null){
                    alerta.setCancelable(false);
                    alerta.setTitle("Falha No Login");
                    alerta.setMessage("Não Existe nenhum usuário com estes dados");
                    alerta.setNegativeButton("Voltar",null);
                    alerta.create().show();
                }else{
                    alerta.setCancelable(false);
                    alerta.setTitle("Login");
                    alerta.setMessage("Login feito com sucesso");
                    alerta.setNegativeButton("Ok",null);
                    alerta.create().show();
                    SharedPreferences sharedPreferences = getSharedPreferences("usuarioDados", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("emailEntrada", email);
                    editor.apply();
                    Intent intent = new Intent(FormLogin.this, CatalogoRejew.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onSuccessBoolean(Boolean favoritado) {
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
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(FormLogin.this);
                alerta.setCancelable(false);
                alerta.setTitle("Login");
                alerta.setMessage("Falha ao realizar Login" + t.getMessage());
                alerta.setNegativeButton("Voltar",null);
                alerta.create().show();
            }

            @Override
            public void onSuccessV(Void body) {
            }
        });
    }

    public void passarHome(View v) {
        Intent intent = new Intent(FormLogin.this, MainActivity.class);
        startActivity(intent);
    }
    public void passarCadastro(View v) {
        Intent intent = new Intent(FormLogin.this, EscolhaTipoConta.class);
        startActivity(intent);
    }
    public void passarTelaCat(View v) {
        Intent intent = new Intent(FormLogin.this, CatalogoRejew.class);
        startActivity(intent);
    }


}