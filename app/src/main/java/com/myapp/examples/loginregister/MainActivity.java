package com.myapp.examples.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class MainActivity extends AppCompatActivity {
    Button buttonHealthRecord, buttonEmergencyContacts;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonHealthRecord = findViewById(R.id.buttonHealthRecord);
        buttonEmergencyContacts = findViewById(R.id.buttonEmergencyContacts);
        sessionManager = new SessionManager(getApplicationContext());


        if ( sessionManager.gethashreport())
        {
            buttonHealthRecord.setVisibility(View.GONE);
        }
        if ( sessionManager.gethasecontact())
        {
           buttonEmergencyContacts.setVisibility(View.GONE);
        }

        if (sessionManager.gethasecontact() && sessionManager.gethasecontact())
        {
            Intent intent2 = new Intent(getApplicationContext(), Services.class);
            startActivity(intent2);
            finish();
        }

        buttonHealthRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HealthRecord.class);
                startActivity(intent);

            }
        });

        buttonEmergencyContacts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmergencyContacts.class);
                startActivity(intent);

            }
        });
    }
}