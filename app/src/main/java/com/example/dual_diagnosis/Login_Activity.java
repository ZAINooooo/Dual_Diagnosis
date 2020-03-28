package com.example.dual_diagnosis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;
import com.rahman.dialog.Utilities.SmartDialogBuilder;


import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login_Activity extends BaseActivity {

    Typeface face , face2;

    TextView multicolorLine;
    Button login_button;
    EditText email_login,phone_login;
String email , phone,statusCode,value2,value5;
    SharedPreferences sharedPreferences;
TextView forgot_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);


        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        face = Typeface.createFromAsset(getAssets(),"ptsanswebbold.ttf");
        face2 = Typeface.createFromAsset(getAssets(),"ptsanswebregular.ttf");


        email_login = findViewById(R.id.email_login);
        phone_login = findViewById(R.id.phone_login);
        forgot_password= findViewById(R.id.forgot_password);

        login_button = findViewById(R.id.login_button);
        multicolorLine = findViewById(R.id.multicolorline);
        String text = "<font color='#0f75bc'>Don't have an account?</font> " + "<font color='#ed1c24'>SignUp</font>";
        multicolorLine.setText(Html.fromHtml(text));


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               loginapi();
            }
        });



        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login_Activity.this, ForgotPassword.class));
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

    private void loginapi()
    {

        pDialog = Utilss.showSweetLoader(Login_Activity.this, SweetAlertDialog.PROGRESS_TYPE, "Loging Up...");

        email = email_login.getText().toString();
        phone = phone_login.getText().toString();

        if (email.equals("") && phone.equals("")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Utilss.hideSweetLoader(pDialog);
                }
            });

//            SweetAlertDialog pDialog = new SweetAlertDialog(Login_Activity.this, SweetAlertDialog.ERROR_TYPE);
//            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pDialog.setTitleText("Fields Are Required..!!");
//            pDialog.setCancelable(false);
//            pDialog.show();
//            return;

            new SmartDialogBuilder(Login_Activity.this)
                    .setTitle("Error Message")
                    .setSubTitle("Fields Are Required..!!")
                    .setCancalable(true)
                    .setTitleFont(face)
                    .setSubTitleFont(face2)
                    .setPositiveButton("OK", new SmartDialogClickListener() {
                        @Override
                        public void onClick(SmartDialog smartDialog) {
                            smartDialog.dismiss();
                        }
                    }).build().show();

        } else if (email.equals("")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Utilss.hideSweetLoader(pDialog);
                        }
                    });

//                    SweetAlertDialog pDialog = new SweetAlertDialog(Login_Activity.this, SweetAlertDialog.ERROR_TYPE);
//                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                    pDialog.setTitleText("Email Is Required..!!");
//                    pDialog.setCancelable(false);
//                    pDialog.show();
//                    return;

                    new SmartDialogBuilder(Login_Activity.this)
                            .setTitle("Error Message")
                            .setSubTitle("Email Is Required..!!")
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

        } else if (phone.equals("")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Utilss.hideSweetLoader(pDialog);
                        }
                    });

//                    SweetAlertDialog pDialog = new SweetAlertDialog(Login_Activity.this, SweetAlertDialog.ERROR_TYPE);
//                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                    pDialog.setTitleText("Password Is Required..!!");
//                    pDialog.setCancelable(false);
//                    pDialog.show();
//                    return;

                    new SmartDialogBuilder(Login_Activity.this)
                            .setTitle("Error Message")
                            .setSubTitle("Password Is Required..!!")
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


        else {

            String url = "http://dd.oneviewcrm.com/DDS/public/api/login?email="+email+"+&password="+phone;

            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String responses = response.toString();
                            Log.e("response", "onResponse(): " + responses);

                            try {
                                JSONObject  json = new JSONObject(responses);
                                value2 = json.getString("status");

                                if (statusCode.equals("200") && value2.equals("success"))
                                {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.toString());
                                        JSONObject jsonObject3 = jsonObject.getJSONObject("user");

                                        String names = jsonObject3.getString("name");
                                        String email_address = jsonObject3.getString("email");
                                        String isadmin_param = jsonObject3.getString("isAdmin");

                                        String token = jsonObject.getString("access_token");
                                        Log.d("Access_Token_Value" , token);


                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Utilss.hideSweetLoader(pDialog);
                                            }
                                        });

                                        if (isadmin_param.equals("1"))
                                        {
                                            //admin
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("token", token);
                                            editor.putString("name", names);
                                            editor.putString("email", email_address);
                                            editor.apply();

                                            startActivity(new Intent(Login_Activity.this, Main_Activity_Module.class));
                                            finish();
                                        }


                                        else if (isadmin_param.equals("2"))
                                        {
                                            //user
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("token", token);
                                            editor.putString("name", names);
                                            editor.putString("email", email_address);
                                            editor.apply();

                                            startActivity(new Intent(Login_Activity.this, Dual_Diagnosis.class));
                                            finish();
                                        }







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


//                                            SweetAlertDialog pDialog = new SweetAlertDialog(Login_Activity.this, SweetAlertDialog.ERROR_TYPE);
//                                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                                            pDialog.setTitleText(value5);
//                                            pDialog.setCancelable(false);
//                                            pDialog.show();
//                                            return;


                                            new SmartDialogBuilder(Login_Activity.this)
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
                    },                        new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.Response", error.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Utilss.hideSweetLoader(pDialog);
                        }
                    });
                }
            }
            )

            {

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    statusCode = String.valueOf(response.statusCode);
                    Log.d("StatusCode3", statusCode);
                    return super.parseNetworkResponse(response);
                }
            };




            Singleton.getInstance(Login_Activity.this).getRequestQueue().add(getRequest);
        }


    }






    @Override
    public void onBackPressed() {

    }
}
