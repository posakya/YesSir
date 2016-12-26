package com.example.roshan.yessir.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.roshan.yessir.JavaClass.HttpHandler;
import com.example.roshan.yessir.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Abesentees extends Fragment {

    private ProgressDialog pDialog;
    private ListView lv;
    private View myView;

    // URL to get contacts JSON
    private static String url = "http://10.42.0.1/AttendanceSystem/absent_info_api.php";

    ArrayList<HashMap<String, String>> absentlist;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView= inflater.inflate(R.layout.fragment_abesentees, container, false);
        absentlist = new ArrayList<>();
        lv = (ListView) myView.findViewById(R.id.detail_list_view);
        new GetContacts().execute();
        getActivity().setTitle("Absentees");
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        HashMap<String, String> student = absentlist.get(position);
                        if(student.containsKey("Parents_No")) {
                            String tel = student.get("Parents_No");
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+tel));
                            startActivity(callIntent);
                        }
                        Toast.makeText(getActivity(), student.get("Parents_No"), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        return myView;
    }
    private class GetContacts extends AsyncTask<String, Object, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);


            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);
                        String Std_id = c.optString("Std_Id");
                        String Name = c.optString("Name");
                        String Section = c.optString("Section");
                        String Parents_No = c.optString("Parents_No");
                        String Subject = c.optString("Subject");
                        String Date = c.optString("Date");



                        // tmp hash map for single student_info
                        HashMap<String, String> student = new HashMap<>();

                        // adding each child node to HashMap key => value
                        student.put("Name", Name);
                        student.put("Section",Section);
                        student.put("Parents_No", Parents_No);
                        student.put("Subject", Subject);
                        student.put("Date",Date);

                        // adding student_info to student list
                        absentlist.add(student);
                    }
                } catch (final JSONException e) {


                }
            } else {


            }

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), absentlist,
                    R.layout.absent_detail, new String[]{ "Name", "Section", "Parents_No" , "Subject","Date"}, new int[]{
                    R.id.txt_name,R.id.txt_section,R.id.txt_phone,R.id.txt_subject,R.id.txt_date});

            lv.setAdapter(adapter);
        }

    }
}
