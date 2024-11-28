package com.example.projeto_rejew;

import android.content.Context;
import android.util.Log;
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
import java.util.Objects;

import okhttp3.ResponseBody;

public class AdapterMensagem extends RecyclerView.Adapter<AdapterMensagem.MensagemViewHolder> {
    private Context context;
    private List<Mensagem> mensagens;
    private Usuario usuario;
    private UsuarioAPIController usuarioAPIController;
    private MensagemAPIController mensagemAPIController;

    // Construtor
    public AdapterMensagem(Context context, List<Mensagem> mensagemList,
                           Usuario usuario, UsuarioAPIController usuarioAPIController,
                           MensagemAPIController mensagemAPIController) {
        this.context = context;
        this.mensagens = mensagemList;
        this.usuario = usuario;
        this.usuarioAPIController = usuarioAPIController;
        this.mensagemAPIController = mensagemAPIController;
    }

    // ViewHolder
    public static class MensagemViewHolder extends RecyclerView.ViewHolder {

        TextView nomePerfil;
        TextView nomePerfilOutro;
        TextView textoMensagem;
        TextView textoMensagemOutro;

        public MensagemViewHolder(View itemView) {
            super(itemView);

            nomePerfil = itemView.findViewById(R.id.nomeuser1);
            nomePerfilOutro = itemView.findViewById(R.id.nomeuser2);

            textoMensagem = itemView.findViewById(R.id.msguser);
            textoMensagemOutro = itemView.findViewById(R.id.msgoutros);
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

        mensagemAPIController.buscarUsuarioPorMensagem(mensagem.getIdMensagem(), new MensagemAPIController.MensagemCallback(){

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
            }

            @Override
            public void onSuccessUsuario(Usuario usuarioce) {
                if (Objects.equals(usuario.getEmailEntrada(), usuarioce.getEmailEntrada())){
                    holder.textoMensagem.setText(mensagem.getTextoMensagem());
                    holder.nomePerfil.setText(usuarioce.getNomeUsuario());

                    holder.textoMensagem.setVisibility(View.VISIBLE);
                    holder.nomePerfil.setVisibility(View.VISIBLE);

                    holder.textoMensagemOutro.setVisibility(View.GONE);
                    holder.nomePerfilOutro.setVisibility(View.GONE);
                }else{
                    holder.textoMensagemOutro.setText(mensagem.getTextoMensagem());
                    holder.nomePerfilOutro.setText(usuarioce.getNomeUsuario());

                    holder.textoMensagemOutro.setVisibility(View.VISIBLE);
                    holder.nomePerfilOutro.setVisibility(View.VISIBLE);

                    holder.textoMensagem.setVisibility(View.GONE);
                    holder.nomePerfil.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("API Error", "Erro ao buscar Usuario da mensagem: " + t.getMessage());
            }
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
