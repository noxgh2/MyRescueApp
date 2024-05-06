package com.myapp.examples.loginregister;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ImportantNumbers extends AppCompatActivity {
    private Button civil,HighwayPatrol,Borderpatrol ,Ambulance,TrafficPolice,GeneralEmergency;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_important_numbers);

        civil = findViewById(R.id.civilbtn);
        HighwayPatrol = findViewById(R.id.HighwayPatrolbtn);
        Borderpatrol = findViewById(R.id.Borderpatrolbtn);
        Ambulance = findViewById(R.id.Ambulancebtn);
        TrafficPolice = findViewById(R.id.TrafficPolicebtn);
        GeneralEmergency = findViewById(R.id.GeneralEmergencybtn);
        Back = findViewById(R.id.btnBack);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImportantNumbers.this, Services.class);
                startActivity(intent);
            }
        });


        civil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:998"));
                startActivity(intent);
            }
        });

        HighwayPatrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:996"));
                startActivity(intent);
            }
        });

        Borderpatrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:994"));
                startActivity(intent);
            }
        });

        Ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:997"));
                startActivity(intent);
            }
        });

        TrafficPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:993"));
                startActivity(intent);
            }
        });

        GeneralEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:112"));
                startActivity(intent);
            }
        });

    }
}