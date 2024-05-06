package com.myapp.examples.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Base64;
import java.util.HashMap;

public class RequestSupplies extends AppCompatActivity {
    EditText EditTextDesc;
    Button buttonSubmit;
    ImageView Back;
    ProgressBar progressBar;
    SessionManager sessionManager;
    HashMap<String, String> userdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_supplies);
        sessionManager = new SessionManager(getApplicationContext());
        userdata = sessionManager.getUserData();

        Back = findViewById(R.id.btnBack);

        EditTextDesc = findViewById(R.id.DescriptionDam);
        buttonSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progress);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestSupplies.this, MakeReport.class);
                startActivity(intent);
            }
        });



        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String Description;
                Description = String.valueOf(EditTextDesc.getText());
                if (!Description.equals("")) {
                    createAlertDialog();
                } else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void createAlertDialog() {

        final String Description,username;
        username = userdata.get(SessionManager.username_key);
        Description = String.valueOf(EditTextDesc.getText());

        AlertDialog.Builder builder = new AlertDialog.Builder(RequestSupplies.this);

        // Set the message show for the Alert time

        String Message = "Report Type:Request supplies\n" + Description ;
        builder.setMessage(Message);
        builder.setTitle("Confirm Your Report");
        builder.setCancelable(false);
        builder.setPositiveButton("Send", (DialogInterface.OnClickListener) (dialog, which) -> {


            if (!Description.equals("")) {

                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[4];
                        field[0] = "Type";
                        field[1] = "Description";
                        field[2] = "Image";
                        field[3] = "username";
                        String[] data = new String[4];
                        data[0] = "Request supplies";
                        data[1] = Description;
                        data[2] = "None";
                        data[3] = username;
                        PutData putData = new PutData("http://192.168.8.138/loginregister/report.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();
                                if (result.equals("Your Report Was Received")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Services.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
                }else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }

        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}