package com.example.roshan.yessir.Fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;


import com.example.roshan.yessir.JavaClass.Absent_Info;
import com.example.roshan.yessir.JavaClass.background;
import com.example.roshan.yessir.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Attendance extends Fragment {
    View myView;
    private ListView listView;
    private GridView gridview;
    private ProgressDialog dialog;
    private TextView  txt_count;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.list_view, container, false);

        dialog = new ProgressDialog(this.getActivity());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading, please wait");
        new JSONTask().execute("http://10.42.0.1/AttendanceSystem/student_detail.php");

      listView = (ListView) myView.findViewById(R.id.detail_list_view);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
       final TextView txt_date = (TextView) myView.findViewById(R.id.txt_Date);
        new Timer().schedule(new TimerTask() {
                           @Override
                           public void run() {
                               if (isVisible()) {
                                   getActivity().runOnUiThread(new Runnable() {
                                       @Override
                                       public void run() {
                                           long date = System.currentTimeMillis();
                                         //  long date = System.currentTimeMillis()  + (1000 * 60 * 60 * 24);
                                           SimpleDateFormat sdf = new SimpleDateFormat(" EEEE - MMM MM dd, yyyy HH:mm:ss a");
                                           String dateString = sdf.format(date);
                                           // This code will always run on the UI thread, therefore is safe to modify UI elements.
                                           txt_date.setText(dateString);
                                       }
                                   });
                               }    }


        },0, 1000);
       // gridview = (GridView) myView.findViewById(R.id.gridview);
        txt_count = (TextView) myView.findViewById(R.id.txt_get_count);

        itemClick();
        //getActivity().setTitle("Student List");

        return myView;
    }

    private void itemClick(){
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Absent_Info value = (Absent_Info) parent.getItemAtPosition(position);
                        new background(getActivity()).execute("Absent", value.getName(), value.getSection(), value.getParents_No(), value.getSubject(),value.getDate(),value.getJpt());

                        Toast.makeText(getActivity(), "Added to Absent List", Toast.LENGTH_SHORT).show();


                        //phone call
//                            String tel = value.getPhone();
//                            Intent callIntent = new Intent(Intent.ACTION_CALL);
//                            callIntent.setData(Uri.parse("tel:"+tel));
//                            startActivity(callIntent);


                    }
                }

        );

    }

    public class JSONTask extends AsyncTask<String, String, List<Absent_Info>> {

        @Override
        protected List<Absent_Info> doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            List<Absent_Info> itemList = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finaljson = buffer.toString();

                Log.i("JSON", "String = " + finaljson);
                JSONArray array = new JSONArray(finaljson);

                int size = array.length();
                for (int i = 0; i < size; i++) {
                    JSONObject j = array.getJSONObject(i);
                    Absent_Info itemList1 = new Absent_Info();
                    itemList1.setStd_Id(j.optString("Std_Id"));
                    itemList1.setName(j.optString("Name"));
                    itemList1.setSection(j.optString("Section"));
                    itemList1.setAddress(j.optString("Address"));
                    itemList1.setPhone(j.optString("Phone"));
                    itemList1.setSubject(j.optString("Subject"));
                    itemList1.setParents_No(j.optString("Parents_No"));
                    itemList1.setDate(j.optString("Date"));
                    itemList1.setTime(j.optString("Time"));
                    itemList.add(itemList1);
                }
                return itemList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return itemList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected void onPostExecute(List<Absent_Info> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            ItemAdapter adapter = new ItemAdapter(getActivity(),R.layout.attendance_detail, result);
            listView.setAdapter(adapter);
            String count = ""+listView.getAdapter().getCount();
            txt_count.setText(count);

        }

    }

    public class ItemAdapter extends ArrayAdapter {
        public List<Absent_Info> itemList;
        private int resource;
        private LayoutInflater inflater;


        public ItemAdapter(Context context, int resource, List<Absent_Info> objects) {
            super(context, resource, objects);
            itemList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();

                convertView = inflater.inflate(R.layout.attendance_detail, null);
                holder.std_id = (TextView) convertView.findViewById(R.id.txt_id);
                holder.name = (TextView) convertView.findViewById(R.id.txt_name);
                holder.date = (TextView) convertView.findViewById(R.id.txt_Date);
                holder.phone = (TextView) convertView.findViewById(R.id.txt_phone);
                holder.section = (TextView) convertView.findViewById(R.id.txt_section);
                holder.subject = (TextView) convertView.findViewById(R.id.txt_subject);
                holder.time = (TextView) convertView.findViewById(R.id.txt_time);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            //data insert

            holder.std_id.setText(itemList.get(position).getStd_Id());
            Log.d("MainActivity", "Name = " + itemList.get(position).getName());
            holder.name.setText(itemList.get(position).getName());
            //holder.date.setText(itemList.get(position).getDate());
            long date = System.currentTimeMillis();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(date);
            holder.date.setText(dateString);
            holder.phone.setText(itemList.get(position).getPhone());
            Log.d("MainActivity", "Phone = " + itemList.get(position).getPhone());
            holder.section.setText(itemList.get(position).getSection());
            holder.subject.setText(itemList.get(position).getSubject());
            holder.time.setText(itemList.get(position).getTime());

            return convertView;
        }

        class ViewHolder {
            private TextView std_id,name,date,time,section,subject,phone,jpt;
        }
    }
}
