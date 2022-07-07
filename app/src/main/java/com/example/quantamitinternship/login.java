package com.example.quantamitinternship;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {

    public static int x = 0;
    Button submit;
    TextView textView;
    EditText user, pass;
    ProgressBar progessBar;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    ImageView google, fb;
    FirebaseAuth.AuthStateListener mAuthListener;
    TextView forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit = findViewById(R.id.login);
        textView = findViewById(R.id.gotoregister);
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        progessBar = findViewById(R.id.progressBar1);
        mAuth = FirebaseAuth.getInstance();
        google = findViewById(R.id.google);
        fb = findViewById(R.id.facebook);
        fstore = FirebaseFirestore.getInstance();
        forgot = findViewById(R.id.forgotpassword);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FacebookLogin.class));
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {

                }
            }
        };

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progessBar.setVisibility(View.VISIBLE);
                String username = user.getText().toString();
                String password = pass.getText().toString();
                if (username.isEmpty()) {
                    user.setError("User Name is Required");
                    return;
                }
                if (password.isEmpty()) {
                    pass.setError("Password is Required");
                    return;
                }
                mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            startActivity(new Intent(getApplicationContext(), mainscreen.class));
                            Toast.makeText(login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            login.x = 1;
                            progessBar.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(login.this, "Error Occurred while Logging in! Please check credentials again.", Toast.LENGTH_LONG).show();
                            progessBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), register.class));
            }
        });
    }
    // }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder altdial = new AlertDialog.Builder(login.this);
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
    // Google Login Activity

}