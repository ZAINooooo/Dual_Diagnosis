package com.example.dual_diagnosis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class SignUpActivity extends AppCompatActivity {
    TextView multicolorLine;
    Button sign_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        sign_up = findViewById(R.id.sign_up);


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                apicall();

                startActivity(new Intent(SignUpActivity.this, Dual_Diagnosis.class));
                finish();
            }
        });


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        multicolorLine = findViewById(R.id.multicolorline);
        String text = "<font color='#0f75bc'>Back To?</font> " + "<font color='#ed1c24'>Login</font>";
        multicolorLine.setText(Html.fromHtml(text));

        multicolorLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUpActivity.this, Login_Activity.class));
                finish();
            }
        });


    }
}
