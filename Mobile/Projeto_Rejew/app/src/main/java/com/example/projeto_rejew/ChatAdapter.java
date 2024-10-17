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
    private Context context;
    private ChatAPIController chatAPIController;
    int larguraPadrao = 40;
    int alturaPadrao = 40;

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagemLogo;
        public TextView nomeChat;
        public TextView ultimaMensagem;

        public ChatViewHolder(View view) {
            super(view);
            imagemLogo = view.findViewById(R.id.imagemLogo);
            nomeChat = view.findViewById(R.id.nomeChat);
        }
    }

    public ChatAdapter(Context context, List<Chat> chats, ChatAPIController chatAPIController) {
        this.context = context;
        this.chats = chats;
        this.chatAPIController = chatAPIController;
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

        chatAPIController.retornarImagem(new ChatAPIController.ChatCallback() {
            @Override
            public void onSuccess(Chat chat) {
            }

            @Override
            public void onSuccessList(List<Chat> chatList) {
            }

            @Override
            public void onSuccessByte(byte[] bytes) {
                Glide.with(context)
                        .load(bytes)
                        .override(larguraPadrao,alturaPadrao)
                        .centerCrop()
                        .into(holder.imagemLogo);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("ChatAdapter", "Falha ao carregar a imagem: " + t.getMessage());
                holder.imagemLogo.setImageResource(R.drawable.imagedefault);
            }
        }, chat.getCaminhoImagemLogo());
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
