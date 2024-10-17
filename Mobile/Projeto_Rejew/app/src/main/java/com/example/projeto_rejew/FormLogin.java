package com.example.projeto_rejew;

import android.content.Intent;
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
import com.example.projeto_rejew.entity.Usuario;

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

        // Configuração de layout para ajustar as barras de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa os campos de email, senha e o ícone de visibilidade da senha
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.senha);
        olhoVisibilidade = findViewById(R.id.senhaicon);  // Certifique-se de que o ícone está presente no layout XML

        // Listener para alternar visibilidade da senha
        olhoVisibilidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    olhoVisibilidade.setImageResource(R.drawable.olhoon);  // Ícone de senha oculta
                } else {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    olhoVisibilidade.setImageResource(R.drawable.olhooff);  // Ícone de senha visível
                }

                // Mover o cursor para o final do texto da senha
                txtPassword.setSelection(txtPassword.getText().length());

                // Alterna o estado de visibilidade da senha
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
                    Intent intent = new Intent(FormLogin.this, CatalogoRejew.class);
                    intent.putExtra("emailEntrada", usuario.getEmailEntrada());
                    startActivity(intent);
                }
            }
            @Override
            public void onSuccessByte(byte[] bytes) {

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