package com.example.projectfour;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static final String TAG = ("DAOEmployee");
    private ProgressDialog progressDialog;
    private Uri imageUri;
    StorageReference storage;
    ImageView addImage, imPicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initial progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Create new account");
        progressDialog.setCanceledOnTouchOutside(false);

        imPicture = findViewById(R.id.imgProfile);
        addImage = findViewById(R.id.addImg);


        TextInputEditText textName = findViewById(R.id.etName);
        TextInputEditText textEmail =findViewById(R.id.etEmail);
        TextInputEditText textPhone = findViewById(R.id.etPhone);
        TextInputEditText tePassword = findViewById(R.id.etPassword);

        Button signUp = findViewById(R.id.btnSignUp);
        TextView tLogin = findViewById(R.id.tvLogin);

        mAuth = FirebaseAuth.getInstance();

        DAOEmployee dao = new DAOEmployee();



        //////////////////////////////////////////////////////////////////
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = textName.getText().toString().trim();
                String email = textEmail.getText().toString().trim();
                String phone_number = textPhone.getText().toString().trim();
                String password = tePassword.getText().toString().trim();

                if (name.isEmpty()){
                    textName.setError("Name is empty");
                }
                if (email.isEmpty()){
                    textEmail.setError("Email is empty");
                }
                if (phone_number.isEmpty()){
                    textPhone.setError("Phone number is empty");
                }
                if (password.isEmpty()){
                    tePassword.setError("Password number is empty");
                }
                if (password.length()<6){
                    tePassword.setError("your password minimal 6 character");
                }
                else {

                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Employee employee = new Employee(textName.getText().toString(), textEmail.getText().toString(), textPhone.getText().toString());
                                    dao.add(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
                                            Intent in = new Intent(RegisterActivity.this,DashboardActivity.class);
                                            startActivity(in);
                                            finish();
                                        }
                                    });


                                } else {
                                    progressDialog.dismiss();
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "Create User Data:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                }

            }
        });
        // end sign up register
        //////////////////////////////////////////////////////////////////////////
        //btn login activity
        tLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goLoginIntent);
            }
        });
        //end btn login activity
        ///////////////////////////////////////////////////////////////////////////
    }

}