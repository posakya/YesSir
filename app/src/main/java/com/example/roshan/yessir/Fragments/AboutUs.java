package com.example.roshan.yessir.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.roshan.yessir.Manifest;
import com.example.roshan.yessir.R;

public class AboutUs extends Fragment {

    private TextView txt_mobile,txt_tel;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_about_us, container, false);
        getActivity().setTitle("About Us");
        txt_mobile = (TextView)view.findViewById(R.id.txt_mobile);
        txt_tel = (TextView)view.findViewById(R.id.txt_tel);

        txt_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9860144817"));
                startActivity(callIntent);
            }
        });
        txt_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:01-4256744"));
                startActivity(callIntent);
            }
        });
        return view;
    }


}
