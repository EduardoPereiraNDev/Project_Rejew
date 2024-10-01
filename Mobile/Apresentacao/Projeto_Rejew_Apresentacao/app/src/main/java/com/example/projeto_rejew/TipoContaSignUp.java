package com.example.projeto_rejew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TipoContaSignUp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tipo_conta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void voltarHome(View v){
        Intent intent = new Intent(TipoContaSignUp.this, Home.class);
        startActivity(intent);
    }
    public void abrirAutor(View v) {
        Intent intent = new Intent(TipoContaSignUp.this, FormSignUp.class);
        startActivity(intent);
    }
    public void abrirLeitor(View v){
        Intent intent = new Intent(TipoContaSignUp.this, FormSignUp.class);
        startActivity(intent);
    }
}
