package com.example.quantamitinternship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class mainscreen extends AppCompatActivity  implements
        GoogleApiClient.OnConnectionFailedListener{


    public static GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder altdial = new AlertDialog.Builder(mainscreen.this);
        altdial.setMessage("Are you sure to quit the app?").setCancelable(false)
                .setPositiveButton("Quit ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                })
                .setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth firebaseAuth;
                            firebaseAuth = FirebaseAuth.getInstance();
                            firebaseAuth.signOut();
                            startActivity(new Intent(getApplicationContext(), login.class));
                            Toast.makeText(mainscreen.this, "Logged out successfully!", Toast.LENGTH_LONG).show();
                        }

                });

        AlertDialog alert = altdial.create();
        alert.setTitle("Alert ! ");
        alert.show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
