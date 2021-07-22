package com.example.friendlychat.RecyclerAdapters;

import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.friendlychat.R;
import com.example.friendlychat.classes.Message;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ChatViewHolder>  {


    private Boolean dateChanged = false;
    private LayoutInflater layoutInflater;
    private List<Message> messageList;
    private String name;
    private String date;

    public ChatRecyclerAdapter (Context context , List<Message> messageList , String name)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.messageList = messageList;
        this.name = name;
    }

    @NonNull
    @NotNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.chat_recycler_view_layout,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatRecyclerAdapter.ChatViewHolder holder, int position) {

        String date_to_check = new SimpleDateFormat("dd/MM", Locale.getDefault()).format(new Date());
        String day_to_check = getDay(date_to_check);
        if ( position == 0 ) {
            dateChanged = true;
            date = messageList.get(0).getDate();
        }
        if ( position > 0 && !messageList.get(position).getDate().equalsIgnoreCase(date) )
        {
            dateChanged = true;
            date = messageList.get(position).getDate();
        }

        if ( dateChanged )
        {
            dateChanged = false;
            String day = getDay(messageList.get(position).getDate());
            holder.date.setVisibility(View.VISIBLE);

            if ( messageList.get(position).getDate().equalsIgnoreCase(date_to_check) )
            {
                holder.date.setText("Today");
            }
            else if ( Integer.parseInt(day) - Integer.parseInt(day_to_check ) == 1 )
            {
                holder.date.setText("Yesterday");
            }
            else
            {
                holder.date.setText(messageList.get(position).getDate());
            }

        }

        if ( messageList.get(position).getSender().equalsIgnoreCase(name))
        {
            holder.message.setText(messageList.get(position).getMessage());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT;
            holder.cardView.setLayoutParams(params);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#EDBF35"));
        }
        else
        {
            holder.message.setText(messageList.get(position).getMessage());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            holder.cardView.setLayoutParams(params);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#B5D5F8"));
        }


        if ( messageList.get(position).getSender().equalsIgnoreCase(name))
        {
            holder.time.setText(messageList.get(position).getTime());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT;
            holder.time.setLayoutParams(params);
        }
        else
        {
            holder.time.setText(messageList.get(position).getTime());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            holder.time.setLayoutParams(params);
        }

        if ( !messageList.get(position).getImageUrl().equalsIgnoreCase("") )
        {
            holder.imageView.setVisibility(View.VISIBLE);
            if ( messageList.get(position).getSender().equalsIgnoreCase(name))
            {
                Glide.with(holder.imageView)
                        .asBitmap()
                        .load(messageList.get(position).getImageUrl())
                        .into(holder.imageView);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.LEFT;
                holder.imageView.setLayoutParams(params);
            }
            else
            {
                Glide.with(holder.imageView)
                        .asBitmap()
                        .load(messageList.get(position).getImageUrl())
                        .into(holder.imageView);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.RIGHT;
                holder.imageView.setLayoutParams(params);
            }

        }

    }

    private String getDay ( String string )
    {
        String str[] = string.split("/",2);
        return  str[0];
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    public class ChatViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView date , time;
        private TextView message;
        private CardView cardView;

        public ChatViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.imageView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            message = itemView.findViewById(R.id.message);
        }
    }
}
