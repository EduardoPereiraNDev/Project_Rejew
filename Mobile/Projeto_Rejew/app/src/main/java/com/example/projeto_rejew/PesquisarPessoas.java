package com.example.projeto_rejew;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projeto_rejew.api.LivroAPIController;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;

public class PesquisarPessoas extends RecyclerView.Adapter<PesquisarPessoas.UsuarioViewHolder> {

    private List<Usuario> usuarios;
    private Context context;
    private UsuarioAPIController usuarioAPIController;

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imagemPerfil;
        public TextView nomeUsuario;


        public UsuarioViewHolder(View view) {
            super(view);
            imagemPerfil = view.findViewById(R.id.fotoPerfilPesq);
            nomeUsuario = view.findViewById(R.id.nomeUsuarioPesq);
        }
    }

    public PesquisarPessoas(Context context, List<Usuario> usuarios, UsuarioAPIController usuarioAPIController) {
        this.context = context;
        this.usuarios = usuarios;
        this.usuarioAPIController = usuarioAPIController;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pesquisar_pessoas, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PerfilUsuario.class);
            intent.putExtra("emailEntrada", usuario.getEmailEntrada());
            context.startActivity(intent);
        });
        holder.nomeUsuario.setText(usuario.getNomeUsuario());

        int larguraPadrao = 70;
        int alturaPadrao = 70;

        usuarioAPIController.carregarImagemPerfil( usuario.getCaminhoImagem(), new UsuarioAPIController.UsuarioCallback() {

            @Override
            public void onSuccess(Usuario usuario) {
            }

            @Override
            public void onSuccessBoolean(Boolean favoritado) {
            }

            @Override
            public void onSuccessInt(Integer integer) {

            }

            @Override
            public void onSuccessByte(byte[] bytes) {
                Glide.with(context)
                        .load(bytes)
                        .override(larguraPadrao, alturaPadrao)
                        .centerCrop()
                        .into(holder.imagemPerfil);
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
                Log.e("PesquisarPessoas", "Falha ao carregar a imagem: " + t.getMessage());
                Log.e("PesquisarPessoas", "StackTrace", t);
                holder.imagemPerfil.setImageResource(R.drawable.imagedefault);
            }


        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void atualizarLista(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        notifyDataSetChanged();
    }
}
