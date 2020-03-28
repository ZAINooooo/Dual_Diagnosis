package com.example.dual_diagnosis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;
import com.rahman.dialog.Utilities.SmartDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class Update_User_Fragment extends Fragment {


    EditText enter_name , enter_phone_number;
    Button update_btn;
    SweetAlertDialog pDialog;
    Typeface face , face2;
SharedPreferences sharedPreferences;
String access_token;

    public Update_User_Fragment()
    {

    }

    public static Update_User_Fragment newInstance(String param1, String param2) {
        Update_User_Fragment fragment = new Update_User_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);


        face = Typeface.createFromAsset(getActivity().getAssets(),"ptsanswebbold.ttf");
        face2 = Typeface.createFromAsset(getActivity().getAssets(),"ptsanswebregular.ttf");

        sharedPreferences = getActivity().getSharedPreferences("DATA", MODE_PRIVATE);
        access_token = sharedPreferences.getString("token", "");


        enter_name = view.findViewById(R.id.enter_name);
        enter_phone_number = view.findViewById(R.id.enter_phone_number);
        update_btn= view.findViewById(R.id.update_btn);



        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String names = enter_name.getText().toString();
                final String phone_number = enter_phone_number.getText().toString();

                pDialog = Utilss.showSweetLoader(getActivity(), SweetAlertDialog.PROGRESS_TYPE, "Update Profile...");


                StringRequest stringRequest = new StringRequest(Request.Method.PUT, "http://dd.oneviewcrm.com/DDS/public/api/user-update/?name="+names+"&phone_no="+phone_number, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("ResponseIs111" , response);

                        try {


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Utilss.hideSweetLoader(pDialog);
                                }
                            });

                            JSONObject jsonObject = new JSONObject(response);
                            String messages = jsonObject.getString("message");

                            new SmartDialogBuilder(getActivity())
                                    .setTitle("Successful Message")
                                    .setSubTitle("Profile Updated")
                                    .setCancalable(true)
                                    .setTitleFont(face)
                                    .setSubTitleFont(face2).setPositiveButton("OK", new SmartDialogClickListener() {
                                @Override
                                public void onClick(SmartDialog smartDialog) {

                                    smartDialog.dismiss();
//                                    startActivity(new Intent(getActivity() , Main_Activity_Module.class));
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("name", names);
                                    editor.apply();

                                    getActivity().finish();
                                    getActivity().overridePendingTransition( 0, 0);
                                    startActivity(getActivity().getIntent());
                                    getActivity().overridePendingTransition( 0, 0);


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
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("ErrorIs" , error.toString());

                                        getActivity().runOnUiThread(new Runnable() {
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
//                        headers.put("Accept","application/json");
                        headers.put("Authorization","Bearer " + access_token);
                        headers.put("Content-Type","application/x-www-form-urlencoded");
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("name",names);
                        params.put("phone_no",phone_number);
                        return params;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Singleton.getInstance(getActivity()).getRequestQueue().add(stringRequest);



            }
        });
        return view;


    }
}
