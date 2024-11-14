package com.example.projeto_rejew;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projeto_rejew.api.ComentarioAPIController;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;

public class AdapterComentario extends RecyclerView.Adapter<AdapterComentario.ComentarioViewHolder> {

    private static Context context;
    private static List<Comentario> comentarios;
    private Usuario usuarioPe;
    private UsuarioAPIController usuarioAPIController;
    private static ComentarioAPIController comentarioAPIController;
    private static ComentarioDeleteCallback deleteCallback;


    public static class ComentarioViewHolder extends RecyclerView.ViewHolder {

        private ImageView menuTresPontos;
        private TextView nomeUsuario;
        private TextView comentarioConteudo;
        private TextView dataComentario;
        private CircleImageView imgFotoPerfil;



        public ComentarioViewHolder(View view) {
            super(view);
            nomeUsuario = view.findViewById(R.id.nomeUsuario);
            comentarioConteudo = view.findViewById(R.id.comentarioConteudo);
            dataComentario = view.findViewById(R.id.dataComentario);
            imgFotoPerfil = view.findViewById(R.id.imgFotoPerfil);
            menuTresPontos = view.findViewById(R.id.tresPontos);
            menuTresPontos.setOnClickListener(this::abrirMenu);
        }

        private void abrirMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(context, menuTresPontos);
            popupMenu.getMenuInflater().inflate(R.menu.menu_delete, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.deletarComentario) {
                        deletarComentario(comentarios.get(getAdapterPosition()).getIdComentario());
                        return true;
                    }
                    return false;
                }
            });

            popupMenu.show();
        }

        private void deletarComentario(long idComentario) {
            comentarioAPIController.deletarComentario(idComentario, new ComentarioAPIController.ComentarioCallback() {
                @Override
                public void onSuccess(Comentario comentario) {
                }

                @Override
                public void onSuccessString(String string) {

                }

                @Override
                public void onSuccessResponse(ResponseBody responseBody) {
                    Log.d("TAG", "DELETANDOOOO: ");
                    deleteCallback.onComentarioDeleted();
                }

                @Override
                public void onSuccessList(List<Comentario> ComentarioL) {
                }

                @Override
                public void onSuccessUsuario(Usuario usuario) {
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("Erro da API", "Erro ao deletar comentário: " + t.getMessage());
                }
            });
        }

    }


    public AdapterComentario(Context context, List<Comentario> comentarioList, Usuario usuario,
                             UsuarioAPIController usuarioAPIController,
                             ComentarioAPIController comentarioAPIController,
                             ComentarioDeleteCallback deleteCallback) {
        this.context = context;
        this.comentarios = comentarioList;
        this.usuarioPe = usuario;
        this.usuarioAPIController = usuarioAPIController;
        this.comentarioAPIController = comentarioAPIController;
        this.deleteCallback = deleteCallback;
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
            public void onSuccessUsuario(Usuario usuarioAutorComentario) {
                if (usuarioAutorComentario != null && usuarioAutorComentario.getEmailEntrada().equals(usuarioPe.getEmailEntrada())) {

                    holder.menuTresPontos.setVisibility(View.VISIBLE);
                } else {
                    holder.menuTresPontos.setVisibility(View.GONE);
                }
                holder.nomeUsuario.setText(usuarioAutorComentario.getNomeUsuario());

                usuarioAPIController.carregarImagemPerfil(usuarioAutorComentario.getCaminhoImagem(), new UsuarioAPIController.UsuarioCallback() {
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
                        holder.imgFotoPerfil.setImageResource(R.drawable.imagedefault);
                    }

                    @Override
                    public void onSuccessList(List<Usuario> usuarios) {}
                    @Override
                    public void onSuccessListL(List<Livro> livros) {}

                    @Override
                    public void onSuccessResponse(ResponseBody body) {}

                    @Override
                    public void onSuccessString(String string) {}

                    @Override
                    public void onSuccess(Usuario usuario) {}

                    @Override
                    public void onSuccessBoolean(Boolean favoritado) {}

                    @Override
                    public void onSuccessInt(Integer integer) {}
                });
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("API Error", "Erro ao buscar usuário do comentário: " + t.getMessage());
                holder.nomeUsuario.setText("Usuário desconhecido");
                holder.imgFotoPerfil.setImageResource(R.drawable.imagedefault);
                holder.menuTresPontos.setVisibility(View.GONE);
            }
            @Override public void onSuccess(Comentario comentario) {}

            @Override
            public void onSuccessString(String string) {}

            @Override
            public void onSuccessResponse(ResponseBody responseBody) {}

            @Override public void onSuccessList(List<Comentario> ComentarioL) {}
        });

    }



    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public void atualizarLista(List<Comentario> novaLista) {
        Log.d("AdapterComentario", "atualizarLista chamado com " + novaLista.size() + " comentários.");
        this.comentarios.clear();
        this.comentarios.addAll(novaLista);
        notifyDataSetChanged();
    }
}
