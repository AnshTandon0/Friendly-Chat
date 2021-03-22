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
        holder.message.setText(sendMessages.get(position).getMessage());
        holder.emailSender.setText(sendMessages.get(position).getSender());
    }

    @Override
    public int getItemCount() {
        return sendMessages.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder
    {
        TextView message;
        TextView emailSender;
        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            emailSender = itemView.findViewById(R.id.emailSender);
        }
    }
}
