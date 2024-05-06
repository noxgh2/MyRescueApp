package com.myapp.examples.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyReports extends AppCompatActivity {
    private static final String getuserreporturl= "http://192.168.8.138/loginregister/getuserreport.php?user=";
    List<report> reportList;
    RecyclerView recyclerView;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_reports);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> userdata = sessionManager.getUserData();
        String user = userdata.get(SessionManager.username_key);
        ImageView Back = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reportList = new ArrayList<>();

        fetchdata(user);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyReports.this, Services.class);
                startActivity(intent);
            }
        });
    }
    private void fetchdata(String user) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getuserreporturl+user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                //adding the product to product list
                                reportList.add(new report(
                                        product.getString("Description"),
                                        product.getString("reportdate"),
                                        product.getString("Type")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            reportadapter adapter = new reportadapter(MyReports.this, reportList);
                            recyclerView.setAdapter(adapter);
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