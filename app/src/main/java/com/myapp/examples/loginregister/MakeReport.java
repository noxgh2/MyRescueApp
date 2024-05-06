package com.myapp.examples.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MakeReport extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_report);
        RadioButton radioBtnD = findViewById(R.id.radioDamage);
        RadioButton radioBtnS = findViewById(R.id.radioRequestSupplies);
        RadioButton radioBtnBoth = findViewById(R.id.radioBoth);
        ImageView Back = findViewById(R.id.btnBack);
        Button button = findViewById(R.id.btnComplete);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeReport.this, Services.class);
                startActivity(intent);
            }
        });
            // Show a Toast message indicating that no radio button is selected
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioBtnD.isChecked()) {
                    Intent intent = new Intent(MakeReport.this, DamageReport.class);
                    startActivity(intent);
                } else if (radioBtnS.isChecked()) {
                    Intent intent = new Intent(MakeReport.this, RequestSupplies.class);
                    startActivity(intent);
                } else if (radioBtnBoth.isChecked()) {
                    Intent intent = new Intent(MakeReport.this, DamageAndSupplies.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MakeReport.this, "No option selected", Toast.LENGTH_SHORT).show();
                }
            }

            });
        }
    }
