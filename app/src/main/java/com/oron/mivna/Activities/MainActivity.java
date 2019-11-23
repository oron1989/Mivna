package com.oron.mivna.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oron.mivna.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private EditText emailField;
    private EditText passwordField;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailLoginEditText);
        passwordField = findViewById(R.id.passwordLoginEditText);
        loginButton = findViewById(R.id.loginButton);

        //if the user is already login and not logout
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();

                if (mUser != null) {
                    Log.w("TAG", "success to login");
                    //Toast.makeText(MainActivity.this, "Signed In", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(MainActivity.this, PostListActivity.class));
                   // finish();
                } else {
                    Log.w("TAG", "failure to login");
                    //Toast.makeText(MainActivity.this, "Not Signed In", Toast.LENGTH_LONG).show();
                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(emailField.getText().toString()) && !TextUtils.isEmpty(passwordField.getText().toString())) {
                    String email = emailField.getText().toString();
                    String pwd = passwordField.getText().toString();

                    login(email, pwd);

                } else {
                    Toast.makeText(MainActivity.this, "One of the fields is missing", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void login(String email, String pwd) {
        mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Yay!! we're in!
                    Log.w("TAG", "success to login");
                    //Toast.makeText(MainActivity.this, "Signed in", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(MainActivity.this, PostListActivity.class));
                    //finish();
                } else {
                    //Not in!!
                }
            }
        });
    }

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
}
