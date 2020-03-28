package com.example.dual_diagnosis;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends BaseActivity {
    TextView multicolorLine;
    Button sign_up;
    EditText name,email_login,password,confirm_password;
    SharedPreferences sharedPreferences;

    String username,email_address,password_signup , confirm_signup  , value2 , statusCode,value5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);

        name = findViewById(R.id.name);
        email_login = findViewById(R.id.email_login);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);

        sign_up = findViewById(R.id.sign_up);


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                apicall();

//                startActivity(new Intent(SignUpActivity.this, Dual_Diagnosis.class));
//                finish();

                signupapi();
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

    private void signupapi()
    {

        pDialog = Utilss.showSweetLoader(SignUpActivity.this, SweetAlertDialog.PROGRESS_TYPE, "Loging Up...");

        username = name.getText().toString();
        email_address = email_login.getText().toString();
        password_signup = password.getText().toString();
        confirm_signup = confirm_password.getText().toString();



        if (username.equals("") && email_address.equals("")  && password_signup.equals("") && confirm_signup.equals("") ) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Utilss.hideSweetLoader(pDialog);
                }
            });

            SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Fields Are Required..!!");
            pDialog.setCancelable(true);
            pDialog.show();
            return;

        }

        else if (username.equals("")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Utilss.hideSweetLoader(pDialog);
                        }
                    });

                    SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Name Is Required..!!");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    return;
                }
            });

        } else if (email_address.equals("")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Utilss.hideSweetLoader(pDialog);
                        }
                    });

                    SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Email Is Required..!!");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    return;

                }
            });
        }



        else if (password_signup.equals("")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Utilss.hideSweetLoader(pDialog);
                        }
                    });

                    SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Password Is Required..!!");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    return;

                }
            });
        }



        else if (confirm_signup.equals("")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Utilss.hideSweetLoader(pDialog);
                        }
                    });

                    SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Confirm Password Is Required..!!");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    return;

                }
            });
        }


        else {


            if (password_signup.matches(confirm_signup))
            {
                RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                String url = "http://dd.oneviewcrm.com/DDS/public/api/register?name="+username+"&email="+email_address+"&password="+password_signup+"&password_confirmation="+confirm_signup;

//                http://dd.oneviewcrm.com/DDS/public/api/register?name=admins&email=dual123@gmail.com&password=dual123&password_confirmation=dual123
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Json-response1", response);


                                try
                                {
                                    JSONObject  json = new JSONObject(response);
                                    value2 = json.getString("status");

                                    if (statusCode.equals("200") && value2.equals("success"))
                                    {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            JSONObject jsonObject3 = jsonObject.getJSONObject("user");

//                                            String names = jsonObject3.getString("name");
                                            String email_address = jsonObject3.getString("email");
                                            String token = jsonObject.getString("access_token");
                                            Log.d("Access_Token_Value" , token + username + email_address);


                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("token", token);
                                            editor.putString("name", username);
                                            editor.putString("email", email_address);
                                            editor.apply();

                                            startActivity(new Intent(SignUpActivity.this, Dual_Diagnosis.class));
                                            finish();

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


//&& value2.equals("error")
                                    else if (statusCode.equals("422") ) {

Log.d("Hereee" , "janja");
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


                                                SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
                                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                                pDialog.setTitleText(value5);
                                                pDialog.setCancelable(true);
                                                pDialog.show();
                                                return;
                                            }
                                        });

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(final VolleyError error)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Utilss.hideSweetLoader(pDialog);
                                            }
                                        });

                                        Log.d("Error.Response", error.toString());


//                                        SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
//                                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                                        pDialog.setTitleText("Wrong Credentials..!!");
//                                        pDialog.setCancelable(true);
//                                        pDialog.show();
//                                        return;

                                    }
                                });


                            }
                        }

                )

                {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response)
                    {
                        statusCode = String.valueOf(response.statusCode);
                        Log.d("StatusCode3", statusCode);
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String>  params = new HashMap<>();
                        params.put("Accept", "application/json");
                        params.put("cache-control", "no-cache");
                        params.put("postman-token", "72f545ee-655d-07a7-62d3-6ae10d329d52");
                        return params;
                    }

                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("name","123");
                        params.put("email","123@gmail.com");
                        params.put("password","12");
                        params.put("password_confirmation","12");

                        return params;
                    }
                };






                queue.add(postRequest);
//                Singleton.getInstance(SignUpActivity.this).getRequestQueue().add(postRequest);


            }

            else
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Utilss.hideSweetLoader(pDialog);
                    }
                });

                SweetAlertDialog pDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Password Does Not Match");
                pDialog.setCancelable(true);
                pDialog.show();
                return;
            }


        }

    }
}
