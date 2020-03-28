package com.example.dual_diagnosis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;

import com.rahman.dialog.Utilities.SmartDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ForgotPassword extends BaseActivity {

    TextView back_login;
    EditText forgot_email;
    Button login_btn;String forgot;

    Typeface face,face2;
    JSONObject json;
    String value3,statusCode,value2,value5;
    SharedPreferences sharedPreferences;

    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);

        forgot_email = findViewById(R.id.forgot_email);


        face = Typeface.createFromAsset(ForgotPassword.this.getAssets(),"ptsanswebbold.ttf");
        face2 = Typeface.createFromAsset(ForgotPassword.this.getAssets(),"ptsanswebregular.ttf");

        back_login = findViewById(R.id.back_login);
        login_btn = findViewById(R.id.login_btn);

        back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ForgotPassword.this , Login_Activity.class));
//                finish();
            }
        });




login_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        pDialog = Utilss.showSweetLoader(ForgotPassword.this, SweetAlertDialog.PROGRESS_TYPE, "Reset Password...");
        forgot = forgot_email.getText().toString();

        if (forgot.equals(""))
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utilss.hideSweetLoader(pDialog);
                }
            });

            new SmartDialogBuilder(ForgotPassword.this)
                    .setTitle("Error Message")
                    .setSubTitle("Fill Up The Email Address")
                    .setCancalable(true)
                    .setTitleFont(face)
                    .setSubTitleFont(face2)
                    .setPositiveButton("OK", new SmartDialogClickListener() {
                        @Override
                        public void onClick(SmartDialog smartDialog) {
                            smartDialog.dismiss();
                        }
                    }).build().show();


        }

        else {
            final String url = "http://dd.oneviewcrm.com/DDS/public/api/password/email";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {



                            Log.e("response", "onResponse(): " + response  + url);

                            try {
                                JSONObject  json = new JSONObject(response);
                                value2 = json.getString("status");

                                if (statusCode.equals("200") && value2.equals("success"))
                                {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.toString());
                                        JSONObject jsonObject3 = jsonObject.getJSONObject("user");

                                        String names = jsonObject3.getString("message");

//                                        Toast.makeText(ForgotPassword.this, "Password Send", Toast.LENGTH_SHORT).show();


                                        new SmartDialogBuilder(ForgotPassword.this)
                                                .setTitle("Success Message")
                                                .setSubTitle(names)
                                                .setCancalable(true)
                                                .setTitleFont(face)
                                                .setSubTitleFont(face2)
                                                .setPositiveButton("OK", new SmartDialogClickListener() {
                                                    @Override
                                                    public void onClick(SmartDialog smartDialog) {

                                                        smartDialog.dismiss();
                                                        startActivity(new Intent(ForgotPassword.this, Login_Activity.class));
                                                        finish();

                                                    }
                                                }).build().show();



                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Utilss.hideSweetLoader(pDialog);
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }


                                else if (statusCode.equals("200") && value2.equals("error")) {


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Utilss.hideSweetLoader(pDialog);
                                        }
                                    });


                                    value5 = json.getString("message");

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {


//                                         pDialog = new SweetAlertDialog(ForgotPassword.this, SweetAlertDialog.ERROR_TYPE);
//                                         pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                                         pDialog.setTitleText(value5);
//                                         pDialog.setCancelable(false);
//                                         pDialog.show();
//                                            return;

                                            new SmartDialogBuilder(ForgotPassword.this)
                                                    .setTitle("Error Message")
                                                    .setSubTitle(value5)
                                                    .setCancalable(true)
                                                    .setTitleFont(face)
                                                    .setSubTitleFont(face2)
                                                    .setPositiveButton("OK", new SmartDialogClickListener() {
                                                        @Override
                                                        public void onClick(SmartDialog smartDialog) {
                                                            smartDialog.dismiss();
                                                        }
                                                    }).build().show();

                                        }
                                    });

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(ForgotPassword.this,error.toString(),Toast.LENGTH_LONG).show();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Utilss.hideSweetLoader(pDialog);
                                }
                            });

                            Log.d("Error_Response" , error.toString());
                        }
                    })

            {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", forgot);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String>  params = new HashMap<>();
                    params.put("Accept", "application/json");
//                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    statusCode = String.valueOf(response.statusCode);
                    Log.d("StatusCode3", statusCode);
                    return super.parseNetworkResponse(response);
                }





            };

//            {
//                @Override
//                protected Map<String,String> getParams(){
//                    Map<String,String> params = new HashMap<String, String>();
//                    params.put("email","dual123@gmail.com");
////                    params.put("password",password);
//
//                    return params;
//                }
//
//            };

            RequestQueue requestQueue = Volley.newRequestQueue(ForgotPassword.this);
            requestQueue.add(stringRequest);
        }










    }
});


    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ForgotPassword.this , Login_Activity.class));
        finish();


    }
}
