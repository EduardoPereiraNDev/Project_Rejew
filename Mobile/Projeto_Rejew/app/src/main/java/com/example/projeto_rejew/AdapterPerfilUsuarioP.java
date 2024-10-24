package com.example.projeto_rejew;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Usuario;
import com.google.android.material.animation.ImageMatrixProperty;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPerfilUsuarioP extends RecyclerView.Adapter<AdapterPerfilUsuarioP.UsuarioViewHolder> {

    private Usuario usuarioP;
    private Context context;
    private UsuarioAPIController usuarioAPIController;

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        public ImageView fotoFundo;
        public CircleImageView fotoPerfil;
        public TextView nomeUsuario;
        public TextView seguidoresQTD;
        public TextView seguindoQTD;
        public TextView comentariosQTD;
        public TextView bio;

        public UsuarioViewHolder(View view) {
            super(view);
            fotoPerfil = view.findViewById(R.id.fotoPerfil);
            fotoFundo= view.findViewById(R.id.fotoFundo);
            nomeUsuario = view.findViewById(R.id.nomeUsuario);
            seguidoresQTD = view.findViewById(R.id.seguidoresQTD);
            seguindoQTD = view.findViewById(R.id.seguindoQTD);
            comentariosQTD = view.findViewById(R.id.comentariosQTD);
            bio = view.findViewById(R.id.bio);
        }
    }

    public AdapterPerfilUsuarioP(Context context, Usuario usuario, UsuarioAPIController usuarioAPIController) {
        this.context = context;
        this.usuarioP = usuario;
        this.usuarioAPIController = usuarioAPIController;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_perfil_usuario_p, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarioP;

        holder.nomeUsuario.setText(usuario.getNomeUsuario());


        holder.seguidoresQTD.setText(String.valueOf(usuario.getUsuariosSeguido().size()));
        holder.seguindoQTD.setText(String.valueOf(usuario.getUsuariosSeguindo().size()));
        holder.comentariosQTD.setText(String.valueOf(usuario.getComentario().size()));
        holder.bio.setText(usuario.getRecadoPerfil());

        usuarioAPIController.carregarImagemPerfil(usuario.getCaminhoImagem() , new UsuarioAPIController.UsuarioCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
            }

            @Override
            public void onSuccessByte(byte[] bytes) {
                int larguraPadrao = 210;
                int alturaPadrao = 210;
                Glide.with(context)
                        .load(bytes)
                        .override(larguraPadrao, alturaPadrao)
                        .centerCrop()
                        .into(holder.fotoPerfil);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("UsuarioAdapter", "Falha ao carregar a imagem: " + t.getMessage());
                holder.fotoPerfil.setImageResource(R.drawable.imagedefault);
            }

            @Override
            public void onSuccessV(Void body) {

            }
        });

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int larguraTela = displayMetrics.widthPixels;
        usuarioAPIController.carregarImagemFundo(usuario.getCaminhoImagemFundo() , new UsuarioAPIController.UsuarioCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
            }

            @Override
            public void onSuccessByte(byte[] bytes) {
                int alturaPadrao = 650;
                Glide.with(context)
                        .load(bytes)
                        .override(larguraTela, alturaPadrao)
                        .centerCrop()
                        .into(holder.fotoFundo);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("UsuarioAdapter", "Falha ao carregar a imagem: " + t.getMessage());
                holder.fotoPerfil.setImageResource(R.drawable.imagedefault);
            }

            @Override
            public void onSuccessV(Void body) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void atualizarLista(Usuario novoUsuario) {
        this.usuarioP = novoUsuario;
        notifyDataSetChanged();
    }
}
