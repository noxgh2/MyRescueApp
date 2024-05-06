package com.myapp.examples.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.net.Uri;
public class Services extends AppCompatActivity {
    private ImageButton report,btnSecurity,btnNumbers,btninstruction,btnCompass;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(getApplicationContext());

        report = findViewById(R.id.btnReport);
        btnSecurity = findViewById(R.id.imageButton);
        btnNumbers = findViewById(R.id.imageButton3);
        btninstruction = findViewById(R.id.btnhelp);
        btnCompass = findViewById(R.id.imageButton4);


        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakeReport.class);
                startActivity(intent);

            }
        });
        btnSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=nearest police station");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });

        btnCompass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), compass.class);
                startActivity(intent);

            }
        });

        btnNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImportantNumbers.class);
                startActivity(intent);

            }
        });

        btninstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Instructions.class);
                startActivity(intent);

            }
        });
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(), userdata.class);
                startActivity(intent);
                break;
            case R.id.item2:
                Intent myintent = new Intent(getApplicationContext(), MyReports.class);
                startActivity(myintent);
                break;
            case R.id.item3:
                sessionManager.logoutUser();

                break;
        }
        return true;
    }

}