package com.example.projeto_rejew;

import android.content.Intent;
import android.os.Bundle;
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

public class FormLogin extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;

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
    }

    public void getLogin(View view){


        String email = txtEmail.getText().toString();
        String senha = txtPassword.getText().toString();

        RetrofitClient retrofitClient;
        retrofitClient = new RetrofitClient();

        UsuarioAPIController userAPIController = new UsuarioAPIController(retrofitClient);

        //apiConnection.loginUser(user,new APIConnection.LoginUserCallback(){
        userAPIController.getLoginUser(email, senha, new UsuarioAPIController.UsuarioCallback(){

            @Override
            public void onSuccess(Usuario usuario) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(FormLogin.this);
                alerta.setCancelable(false);
                alerta.setTitle("Login");
                alerta.setMessage(usuario.toString());
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(FormLogin.this);
                alerta.setCancelable(false);
                alerta.setTitle("Login");
                alerta.setMessage(t.toString());
                alerta.setNegativeButton("Falouu",null);
                alerta.create().show();
            }
        });

    }

    public void passarTelaEsc(View v) {
        Intent intent = new Intent(FormLogin.this, EscolhaTipoConta.class);
        startActivity(intent);
    }

    public void passarTelaCat(View v) {
        Intent intent = new Intent(FormLogin.this, CatalogoRejew.class);
        startActivity(intent);
    }
}