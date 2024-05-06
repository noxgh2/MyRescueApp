package com.myapp.examples.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;

public class EmergencyContacts extends AppCompatActivity {
    EditText EditTextFullName, EditTextPhone;
    RadioButton btnParents, btnBroOSis, btnFriend, btnOther;
    Button btnSave;
    ImageButton Back;
    ProgressBar progressBar;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);
        Back = findViewById(R.id.backtoMain);

        EditTextFullName = findViewById(R.id.EditTextFullName);
        btnParents = findViewById(R.id.radioBtnParents);
        btnBroOSis = findViewById(R.id.radioBtnBOS);
        btnFriend = findViewById(R.id.radioBtnFriend);
        btnOther = findViewById(R.id.radioBtnOther);
        EditTextPhone = findViewById(R.id.EditTextPhone);
        btnSave = findViewById(R.id.contactSave);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> userdata = sessionManager.getUserData();



        progressBar = findViewById(R.id.progress);
        ImageButton imageButton = findViewById(R.id.Add);
        final EditText textAreaName = findViewById(R.id.AddN);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmergencyContacts.this, MainActivity.class);

                startActivity(intent);
                finish();
            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textAreaName.getVisibility() == View.VISIBLE) {
                    textAreaName.setVisibility(View.GONE);
                } else {
                    textAreaName.setVisibility(View.VISIBLE);
                }
            }
        });

        final EditText textAreaType = findViewById(R.id.AddT);

        btnOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textAreaType.setVisibility(View.VISIBLE);
                } else {
                    textAreaType.setVisibility(View.GONE);
                }
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String Name, Relation, phone,username;

                username = userdata.get(SessionManager.username_key);
                Name = String.valueOf(EditTextFullName.getText());
                Relation = (String.valueOf(btnParents.getText()).concat(String.valueOf(btnBroOSis.getText()).concat(String.valueOf(btnFriend.getText()).concat(String.valueOf(btnOther.getText())))));
                phone = String.valueOf(EditTextPhone.getText());
                if((!Name.equals("") && !Relation.equals("") && !phone.equals(""))) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "Name";
                            field[1] = "Relative_Relation";
                            field[2] = "phone";
                            field[3] = "username";
                            String[] data = new String[4];
                            data[0] = Name;
                            data[1] = Relation;
                            data[2] = phone;
                            data[3] = username;


                            PutData putData = new PutData("http://192.168.8.138/loginregister/EmergencyContacts.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Your emergency contacts has been successfully inserted")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                        sessionManager.sethasecontact(true);
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