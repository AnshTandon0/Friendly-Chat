package com.example.friendlychat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder>
{
    LayoutInflater layoutInflater;
    private ArrayList<Message> sendMessages;

    public MessageAdapter(ArrayList<Message> sendMessages , Context context) {
        this.sendMessages = sendMessages;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = layoutInflater.inflate(R.layout.recycler_view_layout,parent,false);
        return new MessageHolder(mView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.textView.setText(sendMessages.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return sendMessages.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder
    {
        TextView textView ;
        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
