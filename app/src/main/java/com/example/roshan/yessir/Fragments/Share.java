package com.example.roshan.yessir.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roshan.yessir.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Share extends Fragment {

private View view;
   private TextView txt_working_days                                                                                                      ;
    private Button btn_Save;
    private EditText editText;
    SharedPreferences sharedpreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_share, container, false);
        txt_working_days = (TextView) view.findViewById(R.id.txt_Days);
        btn_Save = (Button) view.findViewById(R.id.btn_save);
        editText = (EditText) view.findViewById(R.id.edit_days);
        sharedpreferences = getActivity().getSharedPreferences("working days", Context.MODE_PRIVATE);
        getActivity().setTitle("Days");
        String working_Days = sharedpreferences.getString("No. of Working Days","");
        txt_working_days.setText("Working Days: "+working_Days);

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("No. of Working Days", editText.getText().toString());
                editor.apply();
                Toast.makeText(getActivity(), "Saved number of working days!!!!", Toast.LENGTH_SHORT).show();
                String working_Days = sharedpreferences.getString("No. of Working Days","");
                txt_working_days.setText("Working Days: "+working_Days);
            }
        });

        return view;
    }

}
