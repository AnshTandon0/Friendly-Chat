package com.example.friendlychat.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.friendlychat.R;
import com.example.friendlychat.RecyclerAdapters.ChatRecyclerAdapter;
import com.example.friendlychat.classes.FriendsInfo;
import com.example.friendlychat.classes.Message;
import com.example.friendlychat.classes.PersonInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private EditText editText;
    private String name;
    private ArrayList<Message> messages = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference , databaseReference2 , databaseReference3;
    private ChildEventListener childEventListener , childEventListener2 , childEventListener3;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RecyclerView recyclerView;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private String date;
    private String time;
    private String code_person,code_friend;
    private Boolean dateChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        name = getIntent().getStringExtra("name");
        editText = findViewById(R.id.editText);

        date = new SimpleDateFormat("dd/MM", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("messages");
        databaseReference2 = firebaseDatabase.getReference().child("persons");
        sharedPreferences = getSharedPreferences("Message",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if ( !sharedPreferences.getString("date","").equalsIgnoreCase(date) )
        {
            editor.putString("date",date);
            dateChanged = true;
        }

        recyclerView = findViewById(R.id.recyclerView);
        chatRecyclerAdapter = new ChatRecyclerAdapter(this , messages, name);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatRecyclerAdapter);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
               if (message.getSender().equalsIgnoreCase(sharedPreferences.getString("name","")) ||
               message.getReciever().equalsIgnoreCase(sharedPreferences.getString("name","")))
                {
                    if ( message.getSender().equalsIgnoreCase(name) || message.getReciever().equalsIgnoreCase(name) )
                    {
                        messages.add(message);
                        display();
                    }
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


        childEventListener2 = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                PersonInfo personInfo = snapshot.getValue(PersonInfo.class);
                if ( personInfo.getEmail().equalsIgnoreCase(sharedPreferences.getString("email",""))) {
                    code_person = snapshot.getKey();
                    databaseReference3 = FirebaseDatabase.getInstance().getReference().child("persons").child(code_person).child("friends");
                    databaseReference3.addChildEventListener(childEventListener3);
                }
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference2.addChildEventListener(childEventListener2);

        childEventListener3 = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                FriendsInfo friendsInfo = snapshot.getValue(FriendsInfo.class);
                if ( friendsInfo.getName().equalsIgnoreCase(name) )
                {
                    code_friend = snapshot.getKey();
                }
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

    }

    public void Send (View view)
    {
        date = new SimpleDateFormat("dd/MM", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        Message message = new Message();
        message.setMessage(editText.getText().toString());
        message.setSender(sharedPreferences.getString("name",""));
        message.setReciever(name);
        message.setDate(date);
        message.setTime(time);
        databaseReference.push().setValue(message);

        // clear the editText
        editText.setText("");
    }
    public void display()
    {
        chatRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        databaseReference3.child(code_friend).child("chatTime").setValue(messages.get(messages.size()-1).getTime());
        databaseReference3.child(code_friend).child("lastChat").setValue(messages.get(messages.size()-1).getMessage());
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}