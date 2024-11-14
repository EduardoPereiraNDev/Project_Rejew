package com.example.projeto_rejew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_rejew.api.ComentarioAPIController;
import com.example.projeto_rejew.api.MensagemAPIController;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Mensagem;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;

public class AdapterMensagem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static Context context;
    private static List<Mensagem> mensagens;
    private Usuario usuario;
    private UsuarioAPIController usuarioAPIController;
    private static MensagemAPIController mensagemAPIController;
    private static MensagemDeleteCallBack deleteCallback;

    public AdapterMensagem(Context context, List<Mensagem> mensagemList,
                           Usuario usuario, UsuarioAPIController usuarioAPIController,
                           MensagemAPIController mensagemAPIController, MensagemDeleteCallBack deleteCallback) {
        this.context = context;
        this.mensagens = mensagemList;
        this.usuario = usuario;
        this.usuarioAPIController = usuarioAPIController;
        this.mensagemAPIController = mensagemAPIController;
        this.deleteCallback = deleteCallback;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() { return mensagens.size(); }

    public void atualizarLista(List<Mensagem> novasMensagens) {
        this.mensagens = novasMensagens;
        notifyDataSetChanged();
    }

}
