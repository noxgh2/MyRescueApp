package com.myapp.examples.loginregister;
import static android.app.PendingIntent.getActivity;

import java.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.lang.String;
import java.util.HashMap;
import java.util.Objects;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class DamageReport extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 102;

    EditText EditTextDesc;
    ImageButton btnCamera;
    ImageView imageView, Back;
    Button buttonSubmit;
    ProgressBar progressBar;
    Bitmap imageBitmap;
    SessionManager sessionManager;
    HashMap<String, String> userdata;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage_report);


        if (ContextCompat.checkSelfPermission(DamageReport.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(DamageReport.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }

        sessionManager = new SessionManager(getApplicationContext());
        userdata = sessionManager.getUserData();

        Back = findViewById(R.id.btnBack);

        EditTextDesc = findViewById(R.id.DescriptionDam);
        btnCamera = findViewById(R.id.btnCamera);
        imageView = findViewById(R.id.ImageView);
        buttonSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressDamageR);


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DamageReport.this, MakeReport.class);
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
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.O)
                                            @Override
                                            public void onClick(View v) {
                                                final String description;
                                                description = String.valueOf(EditTextDesc.getText());
                                                if (imageView.getDrawable() != null && !description.equals("")) {
                                                    createAlertDialog();
                                                }else {
                                                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createAlertDialog() {
        final String description,username;
        username = userdata.get(SessionManager.username_key);

        description = String.valueOf(EditTextDesc.getText());
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        imageBitmap = bitmapDrawable.getBitmap();


        AlertDialog.Builder builder = new AlertDialog.Builder(DamageReport.this);

        // Set the message show for the Alert time

        String Message = "Report Type: Damage\n" + description ;
        builder.setMessage(Message);
        builder.setTitle("Confirm Your Report");
        builder.setCancelable(false);
        builder.setPositiveButton("Send", (DialogInterface.OnClickListener) (dialog, which) -> {
                if (imageView.getDrawable() != null&&!description.equals("")) {
                    byte[] imageData = convertBitmapToByteArray(imageBitmap);
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
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
                            data[0] = "Damage";
                            data[1] = description;
                            data[2] = base64Image;
                            data[3] = username;

                            PutData putData = new PutData("http://192.168.8.138/loginregister/report.php", "POST", field, data);

                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Your Report Was Received")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), Services.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    });
                } else {
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



