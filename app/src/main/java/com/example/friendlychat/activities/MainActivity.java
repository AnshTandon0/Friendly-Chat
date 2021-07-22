package com.example.friendlychat.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.friendlychat.RecyclerAdapters.MainActivityRecyclerAdapter;
import com.example.friendlychat.R;
import com.example.friendlychat.classes.FriendsInfo;
import com.example.friendlychat.classes.PersonInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityRecyclerAdapter.selected {

    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private PersonInfo personInfod;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("persons");
        sharedPreferences = getSharedPreferences("Message",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        email = sharedPreferences.getString("email","");


        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                PersonInfo personInfo = snapshot.getValue(PersonInfo.class);
                if ( personInfo.getEmail().equalsIgnoreCase(sharedPreferences.getString("email",""))) {
                    personInfod = personInfo;
                    display(personInfo.getFriends());
                }
                if ( personInfo.getEmail().equalsIgnoreCase(email))
                {
                    editor.putString("name",personInfo.getName());
                    editor.commit();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity,menu);
        return true;
    }

    public void signOut (MenuItem item)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        editor.putString("First Time Second Time","First Time");
        editor.commit();
        Intent intent = new Intent(this , SignInActivity.class);
        startActivity(intent);
        finish();
    }

    public void display(List<FriendsInfo> friendsInfoList)
    {
        RecyclerView  recyclerView = findViewById(R.id.recyclerView);
        MainActivityRecyclerAdapter mainActivityRecyclerAdapter = new MainActivityRecyclerAdapter(friendsInfoList,this,this::selected_person);
        recyclerView.setAdapter(mainActivityRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void add ( View view )
    {
        Intent intent = new Intent(this , AddFriendsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void selected_person(int position) {

        Intent intent = new Intent(this,ChatActivity.class);
        intent.putExtra("name",personInfod.getFriends().get(position).getName());
        startActivity(intent);
        finish();
    }
}