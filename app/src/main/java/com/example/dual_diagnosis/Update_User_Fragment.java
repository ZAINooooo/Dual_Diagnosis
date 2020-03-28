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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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

import androidx.fragment.app.Fragment;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.ContentValues.TAG;
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

                RequestQueue queue = Volley.newRequestQueue(getActivity());

                pDialog = Utilss.showSweetLoader(getActivity(), SweetAlertDialog.PROGRESS_TYPE, "Update Profile...");


                Map<String, String> postParam= new HashMap<String, String>();
                postParam.put("name",names);
                postParam.put("phone_no",phone_number);

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                       "http://dd.oneviewcrm.com/DDS/public/api/user-update/?name="+names+"&phone_no="+phone_number, new JSONObject(postParam),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
//                                msgResponse.setText(response.toString());

                                Log.d("Response_Time" , response.toString());

                                Toast.makeText(getActivity(), "Response" + ""+ response.toString(), Toast.LENGTH_SHORT).show();

                           getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Utilss.hideSweetLoader(pDialog);
                                            }
                                        });

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.d(TAG, "Error: " + error.getMessage());

                        Log.d("Error_Response" , error.toString());

//                        Toast.makeText(getActivity(), "Response" + ""+ error.toString(), Toast.LENGTH_SHORT).show();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Utilss.hideSweetLoader(pDialog);
                            }
                        });


                        new SmartDialogBuilder(getActivity())
                                .setTitle("Error Message")
                                .setSubTitle(error.toString())
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
                }) {

                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<>();
//                        headers.put("Accept","application/json");
                        headers.put("Authorization","Bearer " + access_token);
//                        headers.put("Content-Type", "application/json");
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


queue.add(jsonObjReq);
            }
        });
        return view;


    }
}
