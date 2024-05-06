package com.myapp.examples.loginregister;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class userdata extends AppCompatActivity {

    private static final String getuserdataurl= "http://192.168.8.138/loginregister/getuserdata.php?user=";
    TextView fullName,phone,nationalId,userName;

    SessionManager sessionManager;
    Button btnHupdate,btnEupdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userdata);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> userdata = sessionManager.getUserData();
        btnHupdate = findViewById(R.id.hrecord);
        btnEupdate=findViewById(R.id.econtacts);
        String user = userdata.get(SessionManager.username_key);
        ImageView Back = findViewById(R.id.btnBack);
        fetchdata(user);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userdata.this, Services.class);
                startActivity(intent);
            }
        });
        btnHupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userdata.this, UpdateHealthRecord.class);
                startActivity(intent);
            }
        });
        btnEupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userdata.this, UpdateEmergencyContacts.class);
                startActivity(intent);
            }
        });

    }

    private void fetchdata(String user){

        fullName = findViewById(R.id.fullnametext);
        phone = findViewById(R.id.phonefext);
        nationalId = findViewById(R.id.NationalIdtext);
        userName = findViewById(R.id.usernametext);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getuserdataurl+user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            JSONObject user = array.getJSONObject(0);
                            fullName.setText(user.getString("first_name")+" "+user.getString("last_name"));
                            nationalId.setText( user.getString("National_ID"));
                            phone.setText(user.getString("phone_number"));
                            userName.setText(user.getString("username"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);



    }

}