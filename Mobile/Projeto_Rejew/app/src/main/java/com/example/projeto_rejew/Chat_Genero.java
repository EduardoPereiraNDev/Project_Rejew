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
    private ImageView imagemLogo;

    private Handler handler = new Handler();  // Handler para polling
    private static final int POLLING_INTERVAL = 5000; // 5 segundos

    public Chat_Genero() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_genero);

        SharedPreferences sharedPreferences = getSharedPreferences("usuarioDados", MODE_PRIVATE);
        email = sharedPreferences.getString("emailEntrada", null);
        carregarUsuario(email);

        RetrofitClient retrofitClient = new RetrofitClient();
        mensagemAPIController = new MensagemAPIController(retrofitClient);
        usuarioAPIController = new UsuarioAPIController(retrofitClient);
        chatAPIController = new ChatAPIController(retrofitClient);

        mensagem = findViewById(R.id.barraMensagem);
        recyclerView = findViewById(R.id.recyclerView_Chat);

        nomeGenero = findViewById(R.id.nomeGenero);
        imagemLogo = findViewById(R.id.imagemLogo);
        imagemFundo = findViewById(R.id.imagemFundo);

        idChat = getIntent().getLongExtra("idChat", -1);

        generoChat = getIntent().getStringExtra("generoChat");
        TextView tituloGenero = findViewById(R.id.nomeGenero);
        tituloGenero.setText(generoChat);

        usuario = new Usuario();
        chat = new Chat();

        configurarInsets();

        buscarChatPorID(idChat);
        buscarMensagemChat(generoChat);

        // Inicie o polling
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

    @Override
    public void onMensagemDeleted() {
        Toast.makeText(this, "Mensagem deletada com sucesso!", Toast.LENGTH_SHORT).show();
        buscarMensagemChat(generoChat);
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(Chat_Genero.this);
        alerta.setCancelable(false);
        alerta.setTitle(title);
        alerta.setMessage(message);
        alerta.setNegativeButton("Voltar", null);
        alerta.create().show();
    }

    public void chamarBarraMensagem(View view) {
        enviarMensagem();
    }

    private void enviarMensagem() {
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
                showAlert("Sucesso", "Sua mensagem foi enviada!");
                buscarMensagemChat(generoChat);
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

    private void carregarImagem(String caminhoImagem, ImageView imageView, int largura, int altura) {
        chatAPIController.retornarImagem(caminhoImagem, new ChatAPIController.ChatCallback() {
            @Override
            public void onSuccess(Chat chat) {

            }

            @Override
            public void onSuccessByte(byte[] bytes) {
                Glide.with(Chat_Genero.this)
                        .load(bytes)
                        .override(largura, altura)
                        .centerCrop()
                        .into(imageView);
            }

            @Override
            public void onSuccessList(List<Chat> chatList) {

            }

            @Override
            public void onFailure(Throwable t) {
                showAlert("Falha ao carregar a imagem", "Erro: " + t.getMessage());
            }
        });
    }

    private void carregarImagemFundo(String caminhoImagemFundo) {
        carregarImagem(caminhoImagemFundo, imagemFundo, 1080, 1920);
    }

    private void carregarImagemLogo(String caminhoImagemLogo) {
        carregarImagem(caminhoImagemLogo, imagemLogo, 200, 200);
    }

    private void buscarChatPorID(long idChat) {
        chatAPIController.buscarChatPorId(idChat, new ChatAPIController.ChatCallback() {
            @Override
            public void onSuccess(Chat chatEncontrado) {
                nomeGenero.setText(chatEncontrado.getGeneroChat());
                carregarImagemFundo(chatEncontrado.getCaminhoImagemFundoChat());
                carregarImagemLogo(chatEncontrado.getCaminhoImagemLogo());
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

    private void buscarMensagemChat(String generoChat) {
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

    // Método para iniciar o polling
    private void startPolling() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                buscarMensagemChat(generoChat); // Atualiza as mensagens
                handler.postDelayed(this, POLLING_INTERVAL); // Continua o polling
            }
        }, POLLING_INTERVAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // Para o polling quando a atividade for destruída
    }
}
