package com.example.projeto_rejew;

import android.content.Context;
import android.content.Intent;
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

public class AdapterLivrosPesquisa extends RecyclerView.Adapter<AdapterLivrosPesquisa.LivroPesquisaViewHolder> {

    private List<Livro> livros;
    private Context context;
    private LivroAPIController livroAPIController;

    public static class LivroPesquisaViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagemLivro;
        public View corFundo;
        public TextView tituloLivro;
        public TextView autorLivro;

        public LivroPesquisaViewHolder(View view) {
            super(view);
            imagemLivro = view.findViewById(R.id.imagemLivro);
            corFundo = view.findViewById(R.id.corFundo);
            tituloLivro = view.findViewById(R.id.tituloLivro);
            autorLivro = view.findViewById(R.id.autorLivro);
        }
    }

    public AdapterLivrosPesquisa(Context context, List<Livro> livros, LivroAPIController livroAPIController) {
        this.context = context;
        this.livros = livros;
        this.livroAPIController = livroAPIController;
    }

    @NonNull
    @Override
    public LivroPesquisaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_livros_pesquisa, parent, false);
        return new LivroPesquisaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LivroPesquisaViewHolder holder, int position) {
        Livro livro = livros.get(position);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LivroInterface.class);
            intent.putExtra("isbnLivro", livro.getIsbnLivro());
            context.startActivity(intent);
        });

        holder.tituloLivro.setText(livro.getNomeLivro());
        holder.autorLivro.setText(livro.getAutorLivro());

        try {
            int cor = Color.parseColor(livro.getCorPrimaria());
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(cor);
            drawable.setStroke(2, Color.BLACK);
            drawable.setCornerRadius(20);
            holder.corFundo.setBackground(drawable);
        } catch (NumberFormatException e) {
            holder.corFundo.setBackgroundColor(0xFFFFFFFF);
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
                        .override(70, 100)
                        .centerCrop()
                        .into(holder.imagemLivro);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("LivroPesquisaAdapter", "Erro ao carregar imagem: " + t.getMessage());
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
