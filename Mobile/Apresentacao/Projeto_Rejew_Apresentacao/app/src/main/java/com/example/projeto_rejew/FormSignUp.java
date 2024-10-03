package com.example.projeto_rejew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FormSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_leitor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void passarLogin(View v) {
        Intent intent = new Intent(FormSignUp.this, FormLogin.class);
        startActivity(intent);
    }
    public void cadastrar(View v) {
        Intent intent = new Intent(FormSignUp.this, Home.class);
        startActivity(intent);
    }
    public void voltarTipoConta(View v){
        Intent intent = new Intent(FormSignUp.this, TipoContaSignUp.class);
        startActivity(intent);
    }
}