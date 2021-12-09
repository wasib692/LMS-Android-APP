package com.appsol360.pftpproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button signup, login;
    ImageView image;
    TextView Logo_text, logo_desc;
    TextInputLayout email, password;
    String emailPattern ="^(([\\\\w-]+\\\\.)+[\\\\w-]+|([a-zA-Z]{1}|[\\\\w-]{2,}))@\"\n" +
            "              + \"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\\\.([0-1]?\"\n" +
            "              + \"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\\\.\"\n" +
            "              + \"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\\\.([0-1]?\"\n" +
            "              + \"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|\"\n" +
            "              + \"([a-zA-Z]+[\\\\w-]+\\\\.)+[a-zA-Z]{2,4})$";
            //"[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.signup_btn);
        image = findViewById(R.id.login_logo);
        Logo_text = findViewById(R.id.logoName);
        logo_desc = findViewById(R.id.login_slogan);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_pass);
        login = findViewById(R.id.login_btn);

        progressDialog = new ProgressDialog(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
//for animations
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(Logo_text, "logo_text");
                pairs[2] = new Pair<View, String>(logo_desc, "logo_slogan");
                pairs[3] = new Pair<View, String>(email, "email_trans");
                pairs[4] = new Pair<View, String>(password, "pass_trans");
                pairs[5] = new Pair<View, String>(login, "button_trans");
                pairs[6] = new Pair<View, String>(signup, "login_signup_trans");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformLogin();
            }

        });

    }

    private void PerformLogin() {
        String Useremail = email.getEditText().getText().toString();
        String Userpassword = password.getEditText().getText().toString();
        if (Useremail.matches(emailPattern)) {
            email.setError("Enter Correct Email");
        } else if (Userpassword.isEmpty() || Userpassword.length() < 6) {
            password.setError("Enter correct password");

        } else {
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(Useremail, Userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(LoginActivity.this, "Login successfull",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Email/Password is incorrect",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}