package com.myapp.examples.loginregister;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
public class Login extends AppCompatActivity {
    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(getApplicationContext());
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username, password;
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                if(!username.equals("?") && !password.equals("?")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;
                            PutData putData = new PutData("http://192.168.8.138/loginregister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    if (result.equals("Login Success")) {
                                        String[] field2 = new String[1];
                                        field2[0] = "username";
                                        String[] data2 = new String[1];
                                        data2[0] = username;
                                        PutData putData2 = new PutData("http://192.168.8.138/loginregister/checkHealthRecord.php", "POST", field2, data2);
                                        PutData putData3 = new PutData("http://192.168.8.138/loginregister/checkEmergencyContacts.php", "POST", field2, data2);
                                        if (putData2.startPut() && putData3.startPut()) {
                                            if (putData2.onComplete() && putData3.onComplete()) {
                                                String result2 = putData2.getResult();
                                                String result3 = putData3.getResult();
                                                if (result2.equals("Success") && result3.equals("Success")) {
                                                    Intent intent = new Intent(getApplicationContext(), Services.class);
                                                    sessionManager.createLoginSession(username);
                                                    sessionManager.sethashreport(true);
                                                    sessionManager.sethashreport(true);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    if (result2.equals("Success"))
                                                    {
                                                        sessionManager.sethashreport(true);
                                                    }
                                                    if (result3.equals("Success"))
                                                    {
                                                        sessionManager.sethashreport(true);
                                                    }
                                                    sessionManager.createLoginSession(username);
                                                    sessionManager.sethashreport(false);
                                                    sessionManager.sethashreport(false);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        }
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