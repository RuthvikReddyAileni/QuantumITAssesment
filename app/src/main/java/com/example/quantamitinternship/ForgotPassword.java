package com.example.quantamitinternship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    Button submit;
    EditText email;
    String mail;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();
        submit = findViewById(R.id.login1);
        email = findViewById(R.id.forgotemail);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail = email.getText().toString();
                if(mail.isEmpty()){
                    email.setError("Email Required");
                    return;
                }
                ForgotPassword();
            }
        });
    }
    public void ForgotPassword(){
        firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Password Reset Mail Sent.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),login.class));
                    finish();
                }
                else{
                    Toast.makeText(ForgotPassword.this,"Error! Please Try Again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}