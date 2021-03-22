package com.example.friendlychat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private ArrayList<Message> sendMessages = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("messages");
        sharedPreferences = getSharedPreferences("Message",MODE_PRIVATE);
        editor = sharedPreferences.edit();


         childEventListener = new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
             Message m = snapshot.getValue(Message.class);
             sendMessages.add(m);
             display();
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
        Intent intent = new Intent(this , SignIn.class);
        startActivity(intent);
        finish();
    }

    public void Send (View view)
    {
        Message message = new Message("anonymous","anonymous","");
        message.setMessage(editText.getText().toString());
        message.setSender(sharedPreferences.getString("email",""));
        databaseReference.push().setValue(message);

        // clear the editText
        editText.setText("");
    }
    public void display()
    {
        RecyclerView  recyclerView = findViewById(R.id.recyclerView);
        MessageAdapter messageAdapter = new MessageAdapter(sendMessages,this);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}