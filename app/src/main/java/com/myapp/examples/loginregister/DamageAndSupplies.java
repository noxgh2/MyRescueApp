package com.myapp.examples.loginregister;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;

public class DamageAndSupplies extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    EditText EditTextDescS, EditTextDescD;
    ImageButton btnCamera;
    ImageView imageView,Back;
    Button btnSubmit;
    ProgressBar progressBar;
    private Bitmap imageBitmap;
    SessionManager sessionManager;
    HashMap<String, String> userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage_and_supplies);
        if (ContextCompat.checkSelfPermission(DamageAndSupplies.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(DamageAndSupplies.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        sessionManager = new SessionManager(getApplicationContext());
        userdata = sessionManager.getUserData();

        Back = findViewById(R.id.btnBack);

        EditTextDescS = findViewById(R.id.DescriptionSupp);
        EditTextDescD = findViewById(R.id.DescriptionDam);
        imageView = findViewById(R.id.ImageView);
        btnCamera = findViewById(R.id.btnCamera);
        btnSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressDamageSupp);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DamageAndSupplies.this, MakeReport.class);
                startActivity(intent);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);


            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.O)
                                            @Override
                                            public void onClick(View v) {
                                                final String descriptionD, descriptionS;
                                                descriptionS = String.valueOf(EditTextDescS.getText());
                                                descriptionD = String.valueOf(EditTextDescD.getText());

                                                if (imageView.getDrawable() != null && !descriptionS.equals("") && !descriptionD.equals("")) {
                                                    createAlertDialog();
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
        );

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createAlertDialog() {
        final String descriptionD, descriptionS,username;
        descriptionS = String.valueOf(EditTextDescS.getText());
        descriptionD = String.valueOf(EditTextDescD.getText());
        username = userdata.get(SessionManager.username_key);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        imageBitmap = bitmapDrawable.getBitmap();

        AlertDialog.Builder builder = new AlertDialog.Builder(DamageAndSupplies.this);

        // Set the message show for the Alert time

        String Message = "Report Type: Damage and Supplies\n" + descriptionS +"\n" +descriptionD  ;
        builder.setMessage(Message);
        builder.setTitle("Confirm Your Report");
        builder.setCancelable(false);
        builder.setPositiveButton("Send", (DialogInterface.OnClickListener) (dialog, which) -> {

                if (imageView.getDrawable() != null && !descriptionS.equals("") && !descriptionD.equals("")) {

                    byte[] imageData = convertBitmapToByteArray(imageBitmap);
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
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
                            data[0] = "Damage and Supplies";
                            data[1] = descriptionS.concat(descriptionD);
                            data[2] = base64Image;
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // Match the request 'pic id with requestCode
        if (requestCode == CAMERA_REQUEST)  {
            // BitMap is data structure of image file which store the image in memory
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Set the image in imageview for display
           imageView.setImageBitmap(photo);
        }
    }


    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }
    }