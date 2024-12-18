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
import com.example.projeto_rejew.entity.Livro;

import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroViewHolder> {

    private List<Livro> livros;
    private Context context;
    private LivroAPIController livroAPIController;

    public static class LivroViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagemLivro;
        public View viewCor;
        public TextView nomeLivro;
        public TextView nomeAutor;


        public LivroViewHolder(View view) {
            super(view);
            imagemLivro = view.findViewById(R.id.imagemLivro);
            viewCor = view.findViewById(R.id.corFundo);
            nomeLivro = view.findViewById(R.id.nomeLivro);
            nomeAutor = view.findViewById(R.id.nomeAutor);
        }
    }

    public LivroAdapter(Context context, List<Livro> livros, LivroAPIController livroAPIController) {
        this.context = context;
        this.livros = livros;
        this.livroAPIController = livroAPIController;
    }

    @NonNull
    @Override
    public LivroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_container_livro, parent, false);
        return new LivroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LivroViewHolder holder, int position) {
        Livro livro = livros.get(position);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LivroInterface.class);
            intent.putExtra("isbnLivro", livro.getIsbnLivro());
            context.startActivity(intent);
        });

        holder.nomeLivro.setText(livro.getNomeLivro());
        holder.nomeAutor.setText(livro.getAutorLivro());
        int larguraPadrao = 160;
        int alturaPadrao = 220;

        try {
            int cor = Color.parseColor(livro.getCorPrimaria());
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(cor);
            drawable.setStroke(2, Color.BLACK);
            drawable.setCornerRadius(20);
            holder.viewCor.setBackground(drawable);
        } catch (NumberFormatException e) {
            holder.viewCor.setBackgroundColor(0xFFFFFFFF);
        }

        livroAPIController.retornarImagem(new LivroAPIController.LivroCallback() {
            @Override
            public void onSuccess(Livro livro) {
            }
            @Override
            public void onSuccessList(List<Livro> livroList) {
            }
            @Override
            public void onSuccessByte(byte[] bytes) {
                Glide.with(context)
                        .load(bytes)
                        .override(larguraPadrao, alturaPadrao)
                        .centerCrop()
                        .into(holder.imagemLivro);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("LivroAdapter", "Falha ao carregar a imagem: " + t.getMessage());
                Log.e("LivroAdapter", "StackTrace", t);
                holder.imagemLivro.setImageResource(R.drawable.imagedefault);
            }
        }, livro.getCaminhoImgCapa());
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }

    public void atualizarLista(List<Livro> novosLivros) {
        this.livros = novosLivros;
        notifyDataSetChanged();
    }
}
