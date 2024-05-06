package com.myapp.examples.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.CheckBox;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.CompoundButton;
public class HealthRecord extends AppCompatActivity {
    RadioButton btnFemale,btnMale,btnYes,btnNo;
    CheckBox chkA1Q2,chkA2Q2,chkA3Q2,chkA4Q2,chkA5Q2;
    Button btnSave;
    ImageView btnBack;
    ProgressBar progressBar;
    private EditText textFieldDis, textFieldMed;

    private List<String> checkedCheckboxContents = new ArrayList<>();

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_record);
        btnFemale = findViewById(R.id.btnFemale);
        btnMale = findViewById(R.id.btnMale);
        btnYes = findViewById(R.id.btnYes);
        textFieldDis = findViewById(R.id.OtherDiseases);
        textFieldMed = findViewById(R.id.Medicine);
        btnNo = findViewById(R.id.btnNo);
        chkA1Q2 = findViewById(R.id.checkHeart);
        chkA2Q2 = findViewById(R.id.checkDiabetes);
        chkA3Q2 = findViewById(R.id.checkEpilepsa);
        chkA4Q2 = findViewById(R.id.checkAsthma);
        chkA5Q2 = findViewById(R.id.checkOther);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        progressBar = findViewById(R.id.progress);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> userdata = sessionManager.getUserData();

        chkA5Q2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textFieldDis.setVisibility(View.VISIBLE);
                } else {
                    textFieldDis.setVisibility(View.GONE);
                }
            }
        });

        btnYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textFieldMed.setVisibility(View.VISIBLE);
                } else {
                    textFieldMed.setVisibility(View.GONE);
                }
            }
        });
        btnNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textFieldMed.setVisibility(View.GONE);
                } else {
                    textFieldMed.setVisibility(View.VISIBLE);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthRecord.this, MainActivity.class);
                startActivity(intent);
            }
        });




        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String  radioGender, Medication,username;

                username = userdata.get(SessionManager.username_key);


                boolean checkDis = true;
                if(!(String.valueOf(btnFemale.getText())).equals("")){
                    radioGender = String.valueOf(btnFemale.getText());
                }else radioGender=String.valueOf(btnMale.getText());

                if(btnYes.isChecked()){
                    Medication = String.valueOf(textFieldMed.getText());
                }else Medication="No";

                if (chkA1Q2.isChecked()) {
                    checkedCheckboxContents.add(chkA1Q2.getText().toString());
                }
                else if (chkA2Q2.isChecked()) {
                    checkedCheckboxContents.add(chkA2Q2.getText().toString());
                }
                else if (chkA3Q2.isChecked()) {
                    checkedCheckboxContents.add(chkA3Q2.getText().toString());
                }
                else if (chkA4Q2.isChecked()) {
                    checkedCheckboxContents.add(chkA4Q2.getText().toString());
                }
                else if (chkA5Q2.isChecked()) {
                    checkedCheckboxContents.add(textFieldDis.getText().toString());
                } else checkDis = false;

                if(checkDis && !radioGender.equals("") && !Medication.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "Gender";
                            field[1] = "Dieases";
                            field[2] = "Medications";
                            field[3] = "username";
                            String[] data = new String[4];
                            data[0] = radioGender;
                            data[1]= String.valueOf(checkedCheckboxContents);
                            data[2] = Medication;
                            data[3] = username;

                            PutData putData = new PutData("http://192.168.8.138/loginregister/healthRecord.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Your health record has been successfully inserted")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        sessionManager.sethashreport(true);
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