package com.example.friendlychat.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.friendlychat.R;
import com.example.friendlychat.RecyclerAdapters.AddFriendsRecyclerAdapter;
import com.example.friendlychat.classes.FriendsInfo;
import com.example.friendlychat.classes.PersonInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddFriendsActivity extends AppCompatActivity implements AddFriendsRecyclerAdapter.selected {

    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private List<PersonInfo> personInfos = new ArrayList<>();
    private PersonInfo personInfod;
    private String code;
    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    private AddFriendsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new AddFriendsRecyclerAdapter(this,personInfos,this::selected_person);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        sharedPreferences = getSharedPreferences("Message",MODE_PRIVATE);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("persons");
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                PersonInfo personInfo = snapshot.getValue(PersonInfo.class);
                if ( personInfo.getEmail().equalsIgnoreCase(sharedPreferences.getString("email",""))) {
                    personInfod = personInfo;
                    code = snapshot.getKey();
                }
                else {
                    personInfos.add(personInfo);
                    notifychanges();
                }
            }

            @Override
            public void onChildChanged(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        };

        databaseReference.addChildEventListener(childEventListener);


    }

    private void notifychanges() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void selected_person(int position) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Adding to list ...");

        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle("Confirmation Message")
                .setMessage("Are you sure to add it to your friends list ?")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();

                        FriendsInfo friendsInfo = new FriendsInfo(personInfos.get(position).getName(),personInfos.get(position).getImageUrl(),"","",0);
                        List<FriendsInfo> list = new ArrayList<>();
                        list = personInfod.getFriends();
                        list.add(friendsInfo);

                        personInfod.setFriends(list);
                        databaseReference.child(code).setValue(personInfod);
                        progressDialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        alert.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}