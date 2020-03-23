package com.example.dual_diagnosis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Add_Space_Fragment extends Fragment {

    public Add_Space_Fragment()
    {

    }

    public static Add_Space_Fragment newInstance(String param1, String param2) {
        Add_Space_Fragment fragment = new Add_Space_Fragment();
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
        return inflater.inflate(R.layout.fragment_add_space, container, false);
    }
}
