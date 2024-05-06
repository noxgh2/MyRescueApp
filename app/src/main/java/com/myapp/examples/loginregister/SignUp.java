package com.myapp.examples.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    EditText EditTextFname, EditTextLname,EditTextNationalID, EditTextUsername, EditTextPhone, EditTextPassword,EditTextRePassword;
    Button buttonSignUp;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditTextFname = findViewById(R.id.Fname);
        EditTextLname = findViewById(R.id.Lname);
        EditTextNationalID = findViewById(R.id.NationalID);
        EditTextUsername = findViewById(R.id.username);
        EditTextPhone = findViewById(R.id.phone);
        EditTextPassword = findViewById(R.id.password);
        EditTextRePassword = findViewById(R.id.repassword);
        buttonSignUp = findViewById(R.id.btnsignup);
        progressBar = findViewById(R.id.progress);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fname, lname, id, username, phone, password, repassword;
                fname = String.valueOf(EditTextFname.getText());
                lname = String.valueOf(EditTextLname.getText());
                id = String.valueOf(EditTextNationalID.getText());
                username = String.valueOf(EditTextUsername.getText());
                phone = String.valueOf(EditTextPhone.getText());
                password = String.valueOf(EditTextPassword.getText());
                repassword = String.valueOf(EditTextRePassword.getText());

                if(!fname.equals("") && !lname.equals("") && !id.equals("") && !username.equals("") && !phone.equals("") && !password.equals("") && !repassword.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[6];
                            field[0] = "first_name";
                            field[1] = "last_name";
                            field[2] = "National_ID";
                            field[3] = "username";
                            field[4] = "phone_number";
                            field[5] = "password";
                            String[] data = new String[6];
                            data[0] = fname;
                            data[1] = lname;
                            data[2] = id;
                            data[3] = username;
                            data[4] = phone;
                            data[5] = password;
                            if (!password.equals(repassword)) {
                                // Show error message if passwords do not match
                                Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                                return; // Stop further execution
                            }
                            PutData putData = new PutData("http://192.168.8.138/loginregister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}