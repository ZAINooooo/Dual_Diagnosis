package com.example.dual_diagnosis;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;
import com.rahman.dialog.Utilities.SmartDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailsActivity extends BaseActivity {

    int my_query_id;
    String access_token;
SharedPreferences sharedPreferences;
Button delete_button;
TextView m2,m4,m44,m444,m4444;
Typeface face , face2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        face = Typeface.createFromAsset(getAssets(),"ptsanswebbold.ttf");
        face2 = Typeface.createFromAsset(getAssets(),"ptsanswebregular.ttf");

        m2 = findViewById(R.id.m2);
        m4 = findViewById(R.id.m4);
        m44 = findViewById(R.id.m44);
        m444 = findViewById(R.id.m444);

        delete_button = findViewById(R.id.delete_button);

        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        access_token = sharedPreferences.getString("token", "");

         my_query_id= getIntent().getIntExtra("query_id" , 0);
        Log.d("Querry_Id" , ""+my_query_id);



        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteuser();
            }
        });


        getalldetails();
    }

    private void deleteuser()
    {

        new SmartDialogBuilder2(DetailsActivity.this)
                .setTitle("Confirmation Message")
                .setSubTitle("Do You Want To Delete A User")
                .setCancalable(true)
                .setTitleFont(face)
                .setSubTitleFont(face2).setPositiveButton("OK", new SmartDialogClickListener2() {
            @Override
            public void onClick(SmartDialog2 smartDialog) {

//                SharedPreferences.Editor editor=sharedPreferences.edit();
//                editor.remove("token").apply();
//
                smartDialog.dismiss();

                pDialog = Utilss.showSweetLoader(DetailsActivity.this, SweetAlertDialog.PROGRESS_TYPE, "Deleting User...");


                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://dd.oneviewcrm.com/DDS/public/api/user-delete/"+my_query_id, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("ResponseIs111" , response);

                        try {


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Utilss.hideSweetLoader(pDialog);
                                }
                            });

                            JSONObject jsonObject = new JSONObject(response);
                            String messages = jsonObject.getString("message");

                            new SmartDialogBuilder(DetailsActivity.this)
                                    .setTitle("Successful Message")
                                    .setSubTitle(messages)
                                    .setCancalable(true)
                                    .setTitleFont(face)
                                    .setSubTitleFont(face2).setPositiveButton("OK", new SmartDialogClickListener() {
                                @Override
                                public void onClick(SmartDialog smartDialog) {

                                    smartDialog.dismiss();
                                    startActivity(new Intent(DetailsActivity.this , Main_Activity_Module.class));

                                }


                            }).build().show();







                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(final VolleyError error) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("ErrorIs" , error.toString());

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Utilss.hideSweetLoader(pDialog);
                                            }
                                        });


                                    }
                                });
                            }
                        }) {
                    @Override
                    public Map getHeaders() {
                        HashMap headers = new HashMap();
                        headers.put("Accept","application/json");
                        headers.put("Authorization","Bearer " + access_token);
                        return headers;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Singleton.getInstance(DetailsActivity.this).getRequestQueue().add(stringRequest);




//                startActivity(new Intent(DetailsActivity.this , Login_Activity.class));

            }


        }).setNegativeButton("NO" , new SmartDialogClickListener2()
        {
            @Override
            public void onClick(SmartDialog2 smartDialog) {

                smartDialog.dismiss();
            }
        }).build().show();



    }


    private void getalldetails()
    {
        pDialog = Utilss.showSweetLoader(DetailsActivity.this, SweetAlertDialog.PROGRESS_TYPE, "Fetching Details...");


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://dd.oneviewcrm.com/DDS/public/api/users/"+my_query_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("ResponseIs111" , response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject2 = jsonObject.getJSONObject("user");



                    String name = jsonObject2.getString("name");
                    String Email = jsonObject2.getString("email");
                    String Contact_Number = jsonObject2.getString("phone_no");
                    String Address = jsonObject2.getString("address");


                    m2.setText(name);
                    m4.setText(Email);
                    m44.setText(Contact_Number);

                    if (Address.equals("null"))
                    {
                        m444.setText("GPO Box 4057, Melbourne 3001");
                    }

                    else
                    {
                        m444.setText(Address);
                    }
//                    m4444.setText(Paymint);
//
                    Log.d("MyNames" , name+Email+Contact_Number+Address);



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
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("ErrorIs" , error.toString());

                               runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Utilss.hideSweetLoader(pDialog);
                                    }
                                });


                            }
                        });
                    }
                }) {
            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Accept","application/json");
                headers.put("Authorization","Bearer " + access_token);
                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singleton.getInstance(DetailsActivity.this).getRequestQueue().add(stringRequest);



    }
}
