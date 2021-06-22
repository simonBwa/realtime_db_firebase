package com.example.projectfour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initial progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Progress Login");
        progressDialog.setCanceledOnTouchOutside(false);


        CheckBox cBox = findViewById(R.id.rememberMe);
        TextView forgetMe = findViewById(R.id.forgotPass);
        TextView tvRegister = findViewById(R.id.tRegister);
        TextInputEditText tiEmail = findViewById(R.id.ttEmail);
        TextInputEditText tiPassword = findViewById(R.id.ttPassword);

        Button btn_login = findViewById(R.id.btnLogin);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        /////////////////////////////////////////////////////////////////////
        //button login activity
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = tiEmail.getText().toString();
                String password = tiPassword.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    tiEmail.setError("email required");
                }
                if (TextUtils.isEmpty(password)) {
                    tiPassword.setError("password required");
                }
                if (password.length()<6){
                    tiPassword.setError("password must be 6 character");
                }
                else {

                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(LoginActivity.this, "Success Login", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),DashboardActivity.class));

                                } else {
                                    // If sign in fails, display a message to the user.
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "email and password not registered", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                }

            }
        });
        // end button login activity
        /////////////////////////////////////////////////////////////////////////
        //button register activity
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRegisterIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(goRegisterIntent);
            }
        });
        //end button register activity
        ///////////////////////////////////////////////////////////////////////

    }
}