package com.example.projeto_rejew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_rejew.api.MensagemAPIController;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Mensagem;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;

public class AdapterMensagem extends RecyclerView.Adapter<AdapterMensagem.MensagemViewHolder> {
    private Context context;
    private List<Mensagem> mensagens;
    private Usuario usuario;
    private UsuarioAPIController usuarioAPIController;
    private MensagemAPIController mensagemAPIController;
    private MensagemDeleteCallBack deleteCallback;

    // Construtor
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

    // ViewHolder
    public static class MensagemViewHolder extends RecyclerView.ViewHolder {
        TextView textoMensagem;
        TextView textoMensagemOutro;
        TextView nomePerfil;
        TextView nomePerfilOutro;

        public MensagemViewHolder(View itemView) {
            super(itemView);
            textoMensagem = itemView.findViewById(R.id.conteudouser1);
            textoMensagemOutro = itemView.findViewById(R.id.conteudouser2);

            nomePerfil = itemView.findViewById(R.id.nomeuser1);
            nomePerfilOutro = itemView.findViewById(R.id.nomeuser2);
        }
    }

    @NonNull
    @Override
    public MensagemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_mensagem_chat, parent, false);
        return new MensagemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensagemViewHolder holder, int position) {
        Mensagem mensagem = mensagens.get(position);
        
        holder.itemView.setOnLongClickListener(v -> {
            deleteCallback.onDelete(mensagem);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mensagens != null ? mensagens.size() : 0;
    }

    // Atualiza a lista de mensagens
    public void atualizarLista(List<Mensagem> novasMensagens) {
        this.mensagens = novasMensagens;
        notifyDataSetChanged();
    }

    // Interface para callback de deletação
    public interface MensagemDeleteCallBack {
        void onDelete(Mensagem mensagem);
    }
}
