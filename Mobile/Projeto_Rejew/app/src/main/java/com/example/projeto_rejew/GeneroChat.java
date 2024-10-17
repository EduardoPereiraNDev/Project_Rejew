package com.example.projeto_rejew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GeneroChat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_genero_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void chatsPaginicial(View v) {
        Intent intent = new Intent(GeneroChat.this, CatalogoRejew.class);
        startActivity(intent);
    }
    public void chatAventura(View v) {
        Intent intent = new Intent(GeneroChat.this, Chat_Genero.class);
        startActivity(intent);
    }
    public void generochatPessoas(View v) {
        Intent intent = new Intent(GeneroChat.this, Pessoas_Comentario.class);
        startActivity(intent);
    }
}