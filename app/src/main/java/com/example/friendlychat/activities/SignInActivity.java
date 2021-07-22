package com.example.friendlychat.activities;

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

import com.example.friendlychat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText email , password1  ;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        password1 = findViewById(R.id.password1);
        firebaseAuth = FirebaseAuth.getInstance();
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
                    editor.putString("email",email.getText().toString());
                    editor.putString("password",password1.getText().toString());
                    editor.putString("First Time Second Time","Second Time");
                    editor.commit();
                    Toast.makeText(SignInActivity.this, "Successfully Sign In", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(SignInActivity.this, "Sign In Failed", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

        });
    }

    public void SignUpActivity(View view)
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void ForgotPasswordActivity(View view)
    {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }
}