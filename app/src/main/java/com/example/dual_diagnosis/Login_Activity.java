package com.example.dual_diagnosis;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login_Activity extends AppCompatActivity {


    TextView multicolorLine;
    Button login_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);


        login_button = findViewById(R.id.login_button);
        multicolorLine = findViewById(R.id.multicolorline);
        String text = "<font color='#0f75bc'>Don't have an account?</font> " + "<font color='#ed1c24'>SignUp</font>";
        multicolorLine.setText(Html.fromHtml(text));


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login_Activity.this, Dual_Diagnosis.class));
                finish();
            }
        });


        multicolorLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login_Activity.this, SignUpActivity.class));
                finish();
            }
        });



    }


    @Override
    public void onBackPressed() {

    }
}
