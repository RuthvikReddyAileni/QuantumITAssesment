package com.example.quantamitinternship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class register extends AppCompatActivity {

    EditText name,mail,pass,phn;
    TextView textView;
    Button submit;
    FirebaseAuth fAuth;
    static int score ;
    FirebaseFirestore fstore;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.email);
        pass = findViewById(R.id.passs);
        phn =findViewById(R.id.phone);
        submit =findViewById(R.id.register);
        textView =findViewById(R.id.gotologin);
        progressBar = findViewById(R.id.progressBar2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });
        fstore =  FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String user = name.getText().toString();
                String newmail = mail.getText().toString();
                String newphn = phn.getText().toString();
                String newpass = pass.getText().toString();

                if(user.isEmpty()||newmail.isEmpty()||newpass.isEmpty()||newphn.isEmpty()){
                    Toast.makeText(register.this,"Fill all details!",LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                fAuth.createUserWithEmailAndPassword(newmail,newpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            startActivity(new Intent(getApplicationContext(),mainscreen.class));
                            Toast.makeText( register.this, "Successfully Registered!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText( register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder altdial = new AlertDialog.Builder(register.this);
        altdial.setMessage("Are you sure to quit the app?").setCancelable(false)
                .setPositiveButton("Yes ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = altdial.create();
        alert.setTitle("Alert ! ");
        alert.show();
    }
}