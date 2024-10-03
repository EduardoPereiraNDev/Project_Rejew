package com.example.projeto_rejew;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_rejew.api.LivroAPIController;
import com.example.projeto_rejew.entity.Livro;

import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroViewHolder> {

    private List<Livro> livros;
    private Context context;
    private LivroAPIController livroAPIController; // Adicione o controller aqui

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
        holder.nomeLivro.setText(livro.getNomeLivro());
        holder.nomeAutor.setText(livro.getAutorLivro());

        try {
            int cor = Color.parseColor(livro.getCorPrimaria());
            holder.viewCor.setBackgroundColor(cor);
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
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imagemLivro.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(Throwable t) {
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
