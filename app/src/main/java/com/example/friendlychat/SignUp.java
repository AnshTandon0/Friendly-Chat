package com.example.friendlychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private EditText email , password1 , password2 ;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;

    // TODO add direct google signup and signIn feature

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        progressDialog = new ProgressDialog(this);
        sharedPreferences = getSharedPreferences("Message",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("First Time Second Time" , "").equalsIgnoreCase("Second Time"))
        {
            firebaseAuth.signInWithEmailAndPassword(sharedPreferences.getString("email",""),sharedPreferences.getString("password",""));
            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void register(View view)
    {
        if (email.getText().toString().isEmpty())
        {
            email.setError("Enter your email");
            return;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
        {
            email.setError("Enter correct email");
            return;
        }

        else if (password1.getText().toString().isEmpty())
        {
            password1.setError("Enter your password");
            return;
        }

        else if (password2.getText().toString().isEmpty())
        {
            password2.setError("Confirm your password");
            return;
        }

        else if (password1.getText().toString().length()<6)
        {
            password1.setError("Minimum length 6");
            return;
        }

        else if ( ! password1.getText().toString().equals(password2.getText().toString()))
        {
            password2.setError("Password doesn't match");
            return;
        }

        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        String mail =email.getText().toString();
        String pr =password1.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(mail,pr).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    editor.putString("email",email.getText().toString());
                    editor.putString("password",password1.getText().toString());
                    editor.putString("First Time Second Time","Second Time");
                    editor.commit();
                    Toast.makeText(SignUp.this, "Successfully registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(SignUp.this, "Sign Up Failed", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

        });
    }

    public void SignInActivity(View view)
    {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
        finish();
    }
}