package com.example.friendlychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private EditText email , password1  ;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.email);
        password1 = findViewById(R.id.password1);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    public void SignIn(View view)
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

        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password1.getText().toString()).addOnCompleteListener(this ,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(SignIn.this, "Successfully Sign In", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(SignIn.this, "Sign In Failed", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

        });
    }

    public void SignUpActivity(View view)
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
        finish();
    }

    public void ForgotPasswordActivity(View view)
    {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
        finish();
    }
}