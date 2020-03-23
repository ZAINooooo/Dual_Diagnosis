package com.example.dual_diagnosis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class Questionaries extends AppCompatActivity {

    Button anxiety_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaries);


        anxiety_btn = findViewById(R.id.anxiety_btn);


        anxiety_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent mainIntent = new Intent(Questionaries.this, Questionaries.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }
}
