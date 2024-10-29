package com.example.projeto_rejew;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projeto_rejew.api.ComentarioAPIController;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterComentario extends RecyclerView.Adapter<AdapterComentario.ComentarioViewHolder> {

    private List<Comentario> comentarios;
    private UsuarioAPIController usuarioAPIController;
    private ComentarioAPIController comentarioAPIController;
    private Context context;

    public static class ComentarioViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeUsuario;
        public TextView comentarioConteudo;
        public TextView dataComentario;
        public CircleImageView imgFotoPerfil;

        public ComentarioViewHolder(View view) {
            super(view);
            nomeUsuario = view.findViewById(R.id.nomeUsuario);
            comentarioConteudo = view.findViewById(R.id.comentarioConteudo);
            dataComentario = view.findViewById(R.id.dataComentario);
            imgFotoPerfil = view.findViewById(R.id.imgFotoPerfil);
        }
    }

    public AdapterComentario(Context context, List<Comentario> comentarios, UsuarioAPIController usuarioAPIController, ComentarioAPIController comentarioAPIController) {
        this.context = context;
        this.comentarios = comentarios;
        this.usuarioAPIController = usuarioAPIController;
        this.comentarioAPIController = comentarioAPIController;
    }

    @NonNull
    @Override
    public ComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_comentario, parent, false);
        return new ComentarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioViewHolder holder, int position) {
        Comentario comentario = comentarios.get(position);
        holder.comentarioConteudo.setText(comentario.getConteudoComent());
        holder.dataComentario.setText(comentario.getDataComentario());

        Long idComentario = comentario.getIdComentario();

        comentarioAPIController.buscarUsuarioPorComentario(idComentario, new ComentarioAPIController.ComentarioCallback() {
            @Override
            public void onSuccessUsuario(Usuario usuario) {
                // Exibir nome e carregar imagem
                holder.nomeUsuario.setText(usuario.getNomeUsuario());

                usuarioAPIController.carregarImagemPerfil(usuario.getCaminhoImagem(), new UsuarioAPIController.UsuarioCallback() {
                    @Override
                    public void onSuccessByte(byte[] bytes) {
                        Glide.with(context)
                                .load(bytes)
                                .override(90, 90)
                                .centerCrop()
                                .into(holder.imgFotoPerfil);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("Error", "Falha ao carregar imagem: " + t.getMessage());
                        holder.imgFotoPerfil.setImageResource(R.drawable.imagedefault);
                    }

                    @Override public void onSuccess(Usuario usuario) {}
                    @Override public void onSuccessBoolean(Boolean favoritado) {}
                    @Override public void onSuccessV(Void body) {}
                });
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("API Error", "Erro ao buscar usuário do comentário: " + t.getMessage());
                holder.nomeUsuario.setText("Usuário desconhecido");
                holder.imgFotoPerfil.setImageResource(R.drawable.imagedefault);
            }

            // Métodos não utilizados do callback
            @Override public void onSuccess(Comentario comentario) {}
            @Override public void onSuccessList(List<Comentario> ComentarioL) {}
        });
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public void atualizarLista(List<Comentario> novosComentarios) {
        this.comentarios = novosComentarios;
        notifyDataSetChanged();
    }
}
