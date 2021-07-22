package com.example.friendlychat.RecyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendlychat.R;
import com.example.friendlychat.classes.PersonInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddFriendsRecyclerAdapter extends RecyclerView.Adapter<AddFriendsRecyclerAdapter.FriendsViewHolder> {

    private LayoutInflater layoutInflater;
    private List<PersonInfo> personInfos;
    private selected selected_interface;

    public AddFriendsRecyclerAdapter ( Context context , List<PersonInfo> personInfos , selected selected_interface )
    {
        layoutInflater = LayoutInflater.from(context);
        this.personInfos = personInfos ;
        this.selected_interface = selected_interface;
    }

    @NonNull
    @NotNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.friends_recycler_view_layout,parent,false);
        return new FriendsViewHolder(view, selected_interface);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AddFriendsRecyclerAdapter.FriendsViewHolder holder, int position) {

        holder.textView.setText(personInfos.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return personInfos.size();
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;
        private selected selected_interface;

        public FriendsViewHolder(@NonNull @NotNull View itemView , selected selected_interface) {
            super(itemView);

            this.selected_interface = selected_interface;
            textView = itemView.findViewById(R.id.text);
            textView.setOnClickListener(this::onClick);
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
