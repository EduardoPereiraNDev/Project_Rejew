package com.example.projeto_rejew;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projeto_rejew.api.LivroAPIController;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterComentario extends RecyclerView.Adapter<AdapterComentario.ComentarioViewHolder> {

    private List<Comentario> comentarios;
    private UsuarioAPIController usuarioAPIController;
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

    public AdapterComentario(Context context, List<Comentario> comentarios, UsuarioAPIController usuarioAPIController) {
        this.context = context;
        this.comentarios = comentarios;
        this.usuarioAPIController = usuarioAPIController;
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
        holder.nomeUsuario.setText(comentario.getUsuarioComent().getNomeUsuario());
        holder.comentarioConteudo.setText(comentario.getConteudoComent());
        holder.dataComentario.setText(comentario.getDataComentario());


        int larguraPadrao = 90;
        int alturaPadrao = 90;
        usuarioAPIController.carregarImagemPerfil( comentario.getUsuarioComent().getCaminhoImagem() , new UsuarioAPIController.UsuarioCallback() {

            @Override
            public void onSuccess(Usuario usuario) {
            }

            @Override
            public void onSuccessByte(byte[] bytes) {
                Glide.with(context)
                        .load(bytes)
                        .override(larguraPadrao, alturaPadrao)
                        .centerCrop()
                        .into(holder.imgFotoPerfil);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Error", "onFailure: "+ t );
                holder.imgFotoPerfil.setImageResource(R.drawable.imagedefault);
            }

            @Override
            public void onSuccessV(Void body) {

            }
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
