package com.example.friendlychat.RecyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendlychat.R;
import com.example.friendlychat.classes.FriendsInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivityRecyclerAdapter extends RecyclerView.Adapter<MainActivityRecyclerAdapter.MessageHolder>
{
    private LayoutInflater layoutInflater;
    private List<FriendsInfo> friendsInfos;
    private selected selected_interface;

    public MainActivityRecyclerAdapter(List<FriendsInfo> friendsInfos , Context context , selected selected_interface) {
        this.friendsInfos = friendsInfos;
        layoutInflater = LayoutInflater.from(context);
        this.selected_interface = selected_interface;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = layoutInflater.inflate(R.layout.main_recycler_view_layout,parent,false);
        return new MessageHolder(mView,selected_interface) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {

        String time_to_check =  new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        int min_to_check = getMin(time_to_check);

        if (!friendsInfos.get(position).getPhotoUrl().equalsIgnoreCase(""))
        {

        }

        if ( !friendsInfos.get(position).getChatTime().equalsIgnoreCase("") ) {
            int min = getMin(friendsInfos.get(position).getChatTime());
            holder.time.setText(String.valueOf(min_to_check - min)+ " min");
        }
        else
        {
            holder.time.setText(friendsInfos.get(position).getChatTime());
        }
        holder.name.setText(friendsInfos.get(position).getName());
        holder.message.setText(friendsInfos.get(position).getLastChat());
        holder.number.setText(String.valueOf(friendsInfos.get(position).getNoUnread()));
    }

    private int getMin ( String string )
    {
        String str[] = string.split(":",2);
        return Integer.parseInt(str[1]);
    }

    @Override
    public int getItemCount() {
        return friendsInfos.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener   {
        private ImageView imageView ;
        private TextView name , message , time , number ;
        private selected selected_interface;

        public MessageHolder(@NonNull View itemView , selected selected_interface) {
            super(itemView);
            this.selected_interface = selected_interface;
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            number = itemView.findViewById(R.id.unread_number);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            selected_interface.selected_person(getAdapterPosition());
        }
    }

    public interface selected
    {
        void selected_person ( int position );
    }
}
