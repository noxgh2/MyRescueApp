package com.myapp.examples.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Instructions extends AppCompatActivity {
    TextView textview;
    ImageView Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instructions);
        textview = findViewById(R.id.text);
        Back = findViewById(R.id.btnBack);
    String para = "1.\tWe advise you not to leave your car. Do not leave your car. Do not leave your car.\n\n" +
            "2.\tIf you have little water, do not use it for drinking except when necessary.\n\n" +
            "3.\tDo not be exposed to the sun, do not be exposed to the sun, do not be exposed to the sun or the wind so that your sweat does not dry.\n\n" +
            "4.\tBreathe through your nose, keep your mouth closed to maintain moisture, and use a mask whenever possible. \n\n" +
            "5.\tOn the first day of the day, break the mirrors of the car to make it a SIGNAL MIRROR, then send intermittent and rapid signals to the planes flying above you. They will see you clearly by reflecting the sun's rays, making sure that the signal is intermittent and rapid so as not to harm the pilot. Or use a laser if you have one at night..\n\n" +
            "6.\tIf there are rocks around you, write in a line that is visible from the SOS aircraft or make a deep line in the sand.\n\n" +
            "7.\tIn extreme heat, if you do not find shade, dig a hole as long and as wide as you are under the car and stretch out in it. \n\n" +
            "8.\tLight the fifth (spare) tire during the day, after emptying the air to prevent explosion, as plumes of black smoke rise to the top and can be seen from a very long distance, then light the second and third tires. Thus, over the course of two days, light the same car on the same day, you have six smoke alarms, use one or two each day. \n\n" +
            "9.\tMake a radar yourself to hear sounds from afar. If you have a dish or make a dish (dish) from any part of the car, pierce it in the middle and point it at the target in a prominent place, put your ear in the middle hole at the bottom of the shower, and eavesdrop over long distances. \n\n" +
            "10.\tTake with you a stick to lean on and protect yourself from predators and reptiles.\n";
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Instructions.this, Services.class);
                startActivity(intent);
            }
        });
        textview.setText(para);
        textview.setMovementMethod(new ScrollingMovementMethod());
    }
}