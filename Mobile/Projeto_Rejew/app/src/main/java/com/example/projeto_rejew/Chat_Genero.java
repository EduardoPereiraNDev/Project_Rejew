package com.example.projeto_rejew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projeto_rejew.api.ChatAPIController;
import com.example.projeto_rejew.api.MensagemAPIController;
import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Chat;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Mensagem;
import com.example.projeto_rejew.entity.Usuario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;

public class Chat_Genero extends AppCompatActivity implements MensagemDeleteCallBack {
    private Usuario usuario;
    private Chat chat;
    private RecyclerView recyclerView;
    private AdapterMensagem adapterMensagem;
    private String email;
    private String generoChat;
    private EditText mensagem;
    private long idChat;
    private MensagemAPIController mensagemAPIController;
    private UsuarioAPIController usuarioAPIController;
    private ChatAPIController chatAPIController;
    private TextView nomeGenero;
    private ImageView imagemFundo;
    private CircleImageView imagemLogo;

    private Handler handler = new Handler();
    private static final int POLLING_INTERVAL = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_genero);

        RetrofitClient retrofitClient = new RetrofitClient();
        mensagemAPIController = new MensagemAPIController(retrofitClient);
        usuarioAPIController = new UsuarioAPIController(retrofitClient);
        chatAPIController = new ChatAPIController(retrofitClient);

        mensagem = findViewById(R.id.barraMensagem);

        recyclerView = findViewById(R.id.recyclerView_Chat);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nomeGenero = findViewById(R.id.nomeGenero);
        imagemLogo = findViewById(R.id.imagemLogo);
        imagemFundo = findViewById(R.id.imagemFundo);

        idChat = getIntent().getLongExtra("IdChat", -1);

        usuario = new Usuario();
        chat = new Chat();

        SharedPreferences sharedPreferences = getSharedPreferences("usuarioDados", MODE_PRIVATE);
        email = sharedPreferences.getString("emailEntrada", null);

        carregarUsuario(email);
        buscarChatPorID();

        configurarInsets();
        startPolling();
    }

    private void configurarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void carregarUsuario(String email) {
        usuarioAPIController.buscarUsuario(email, new UsuarioAPIController.UsuarioCallback() {
            @Override
            public void onSuccess(Usuario usuarioEncontrado) {
                if (usuarioEncontrado != null) {
                    usuario = usuarioEncontrado;
                }
            }

            @Override
            public void onSuccessBoolean(Boolean booleano) {

            }

            @Override
            public void onSuccessInt(Integer integer) {

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
            public void onSuccessResponse(ResponseBody body) {

            }

            @Override
            public void onSuccessString(String string) {

            }

            @Override
            public void onFailure(Throwable t) {
                showAlert("Falha ao carregar usuario", "Erro: " + t.getMessage());
            }
        });
    }

    public void enviarMensagem(View view) {
        String conteudoMensagem = mensagem.getText().toString().trim();
        if (conteudoMensagem.isEmpty()) {
            Toast.makeText(this, "A mensagem não pode estar vazia", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formataData.format(new Date());

        Mensagem mensagem = new Mensagem(conteudoMensagem, dataFormatada, usuario, chat);
        mensagemAPIController.enviarMensagem(mensagem, new MensagemAPIController.MensagemCallback() {
            @Override
            public void onSuccess(Mensagem mensagem) {
                buscarMensagemChat();
            }

            @Override
            public void onSuccessString(String string) {

            }

            @Override
            public void onSuccessResponse(ResponseBody responseBody) {

            }

            @Override
            public void onSuccessList(List<Mensagem> mensagemList) {

            }

            @Override
            public void onSuccessUsuario(Usuario usuario) {

            }

            @Override
            public void onFailure(Throwable t) {
                showAlert("Falha ao enviar Mensagem", "Erro: " + t.getMessage());
            }
        });
    }

    private void carregarImagemFundo(String caminhoImagemFundo) {
        chatAPIController.retornarImagemFundo(caminhoImagemFundo, new ChatAPIController.ChatCallback() {
            @Override
            public void onSuccess(Chat chat) {

            }

            @Override
            public void onSuccessByte(byte[] bytes) {
                Glide.with(Chat_Genero.this)
                        .load(bytes)
                        .override(1080, 1920)
                        .centerCrop()
                        .into(imagemFundo);
            }

            @Override
            public void onSuccessList(List<Chat> chatList) {

            }

            @Override
            public void onFailure(Throwable t) {
                imagemFundo.setImageResource(R.drawable.imagedefault);
                showAlert("Falha ao carregar a imagem", "Erro: " + t.getMessage());
            }
        });
    }

    private void carregarImagemLogo(String caminhoImagemLogo) {
        chatAPIController.retornarImagem(caminhoImagemLogo, new ChatAPIController.ChatCallback() {
            @Override
            public void onSuccess(Chat chat) {

            }

            @Override
            public void onSuccessByte(byte[] bytes) {
                Glide.with(Chat_Genero.this)
                        .load(bytes)
                        .override(400, 400)
                        .centerCrop()
                        .into(imagemLogo);
            }

            @Override
            public void onSuccessList(List<Chat> chatList) {

            }

            @Override
            public void onFailure(Throwable t) {
                imagemLogo.setImageResource(R.drawable.imagedefault);
                showAlert("Falha ao carregar a imagem", "Erro: " + t.getMessage());
            }
        });
    }

    private void buscarChatPorID() {
        chatAPIController.buscarChatPorId(idChat, new ChatAPIController.ChatCallback() {
            @Override
            public void onSuccess(Chat chatEncontrado) {
                chat = chatEncontrado;
                generoChat = chatEncontrado.getGeneroChat();
                nomeGenero.setText(chatEncontrado.getGeneroChat());
                carregarImagemFundo(chatEncontrado.getCaminhoImagemFundoChat());
                carregarImagemLogo(chatEncontrado.getCaminhoImagemLogo());
                buscarMensagemChat();
            }

            @Override
            public void onSuccessByte(byte[] bytes) {

            }

            @Override
            public void onSuccessList(List<Chat> chatList) {

            }

            @Override
            public void onFailure(Throwable t) {
                showAlert("Falha ao carregar o chat", "Erro: " + t.getMessage());
            }
        });
    }

    private void buscarMensagemChat() {
        mensagemAPIController.buscarMensagensPorChat(generoChat, new MensagemAPIController.MensagemCallback() {
            @Override
            public void onSuccess(Mensagem mensagem) {

            }

            @Override
            public void onSuccessString(String string) {

            }

            @Override
            public void onSuccessResponse(ResponseBody responseBody) {

            }

            @Override
            public void onSuccessList(List<Mensagem> mensagemList) {
                if (adapterMensagem == null) {
                    adapterMensagem = new AdapterMensagem(Chat_Genero.this, mensagemList, usuario, usuarioAPIController, mensagemAPIController, Chat_Genero.this);
                    recyclerView.setAdapter(adapterMensagem);
                } else {
                    adapterMensagem.atualizarLista(mensagemList);
                }
            }

            @Override
            public void onSuccessUsuario(Usuario usuario) {

            }

            @Override
            public void onFailure(Throwable t) {
                if (t.getMessage() == null || !t.getMessage().contains("404")) {
                    showAlert("Falha ao carregar as mensagens", "Erro: " + t.getMessage());
                }
            }
        });
    }

    private void startPolling() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                buscarMensagemChat();
                handler.postDelayed(this, POLLING_INTERVAL);
            }
        }, POLLING_INTERVAL);
    }

    @Override
    public void onMensagemDeleted() {
        Toast.makeText(this, "Mensagem deletada com sucesso!", Toast.LENGTH_SHORT).show();
        buscarMensagemChat();
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(Chat_Genero.this);
        alerta.setCancelable(false);
        alerta.setTitle(title);
        alerta.setMessage(message);
        alerta.setNegativeButton("Voltar", null);
        alerta.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // Para o polling quando a atividade for destruída
    }
}
