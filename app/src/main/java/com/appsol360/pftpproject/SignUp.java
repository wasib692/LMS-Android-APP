package com.appsol360.pftpproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    Button alreadLogin, signUp;
    private FirebaseAuth mAuth;
    TextInputLayout Useremail, Userpassword;
    String emailPattern = "^(([\\\\w-]+\\\\.)+[\\\\w-]+|([a-zA-Z]{1}|[\\\\w-]{2,}))@\"\n" +
            "              + \"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\\\.([0-1]?\"\n" +
            "              + \"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\\\.\"\n" +
            "              + \"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\\\.([0-1]?\"\n" +
            "              + \"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|\"\n" +
            "              + \"([a-zA-Z]+[\\\\w-]+\\\\.)+[a-zA-Z]{2,4})$";
            //"[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        alreadLogin = findViewById(R.id.alreadyAccount);
        Useremail = findViewById(R.id.email);
        Userpassword = findViewById(R.id.password);
        signUp = findViewById(R.id.button);
        progressDialog = new ProgressDialog(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });
        alreadLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, LoginActivity.class));
            }
        });
        //create account

    }

    private void performAuth() {
        String email = Useremail.getEditText().getText().toString();
        String password = Userpassword.getEditText().getText().toString();
        if (email.matches(emailPattern)) {
            Useremail.setError("Enter Correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            Userpassword.setError("Enter correct password");

        } else {
            progressDialog.setMessage("SignUp...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                progressDialog.dismiss();
                                sendUserToNextActivity();
                                Toast.makeText(SignUp.this, "Account created",
                                        Toast.LENGTH_SHORT).show();
                                //  FirebaseUser user = mAuth.getCurrentUser();

                            } else {

                                progressDialog.dismiss();
                                Toast.makeText(SignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                        }
                    };
            // [END create_user_with_email]

    });}



}

    private void sendUserToNextActivity() {
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}