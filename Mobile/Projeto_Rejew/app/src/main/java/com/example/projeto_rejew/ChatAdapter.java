package com.example.projeto_rejew;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projeto_rejew.api.ChatAPIController;
import com.example.projeto_rejew.entity.Chat;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Chat> chats;
    private final Context context;
    private final ChatAPIController chatAPIController;
    private static final int LARGURA_PADRAO = 40;
    private static final int ALTURA_PADRAO = 40;

    public ChatAdapter(Context context, List<Chat> chats, ChatAPIController chatAPIController) {
        this.context = context;
        this.chats = chats;
        this.chatAPIController = chatAPIController;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imagemLogo;
        public final TextView nomeChat;

        public ChatViewHolder(View view) {
            super(view);
            imagemLogo = view.findViewById(R.id.imagemLogo);
            nomeChat = view.findViewById(R.id.nomeChat);
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_adapter, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chats.get(position);
        holder.nomeChat.setText(chat.getGeneroChat());
        carregarImagemLogo(chat.getCaminhoImagemLogo(), holder.imagemLogo);
    }

    private void carregarImagemLogo(String caminhoImagemLogo, ImageView imageView) {
        chatAPIController.retornarImagem(caminhoImagemLogo, new ChatAPIController.ChatCallback() {
            @Override
            public void onSuccessByte(byte[] bytes) {
                Glide.with(context)
                        .load(bytes)
                        .override(LARGURA_PADRAO, ALTURA_PADRAO)
                        .centerCrop()
                        .into(imageView);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ChatAdapter", "Falha ao carregar a imagem: " + t.getMessage());
                imageView.setImageResource(R.drawable.imagedefault);
            }

            @Override
            public void onSuccess(Chat chat) {}
            @Override
            public void onSuccessList(List<Chat> chatList) {}
        });
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void atualizarLista(List<Chat> novosChats) {
        this.chats = novosChats;
        notifyDataSetChanged();
    }
}
