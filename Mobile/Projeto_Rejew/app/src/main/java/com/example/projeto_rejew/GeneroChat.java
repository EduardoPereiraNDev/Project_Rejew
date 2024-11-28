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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_rejew.api.ChatAPIController;
import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.entity.Chat;

import java.util.List;

public class GeneroChat extends AppCompatActivity {

    private AdapterChats chatAdapter;
    private RecyclerView recyclerView;
    private ChatAPIController chatAPIController;

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

        RetrofitClient retrofitClient = new RetrofitClient();
        chatAPIController = new ChatAPIController(retrofitClient);

        recyclerView = findViewById(R.id.recyclerChats);
        int spanCount = 3;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount, GridLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        carregarChat();
    }

    private void carregarChat() {
        chatAPIController.carregarChats(new ChatAPIController.ChatCallback() {

            @Override
            public void onSuccess(Chat chat) {

            }

            @Override
            public void onSuccessByte(byte[] bytes) {

            }

            @Override
            public void onSuccessList(List<Chat> chatL) {
                chatAdapter = new AdapterChats(GeneroChat.this, chatL, chatAPIController);
                recyclerView.setAdapter(chatAdapter);
            }

            @Override
            public void onFailure(Throwable t) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(GeneroChat.this);
                alerta.setCancelable(false);
                alerta.setTitle("Falha ao recarregar o catálogo");
                alerta.setMessage("Error: " + t.getMessage());
                alerta.setNegativeButton("Voltar", null);
                alerta.create().show();
            }
        });
    }


    public void chatsPaginicial(View v) {
        finish();
    }

    public void chatCatalogo(View v) {
        Intent intent = new Intent(GeneroChat.this, CatalogoRejew.class);
        startActivity(intent);
    }

    public void irChat(View v) {
        Intent intent = new Intent(GeneroChat.this, GeneroChat.class);
        startActivity(intent);
    }

    public void generochatPessoas(View v) {
        Intent intent = new Intent(GeneroChat.this, Pessoas_Comentario.class);
        startActivity(intent);
    }
}