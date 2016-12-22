package com.example.roshan.yessir.JavaClass;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by roshan on 12/19/16.
 */

public class background extends AsyncTask<String, Void, String> {
    Context context;

    public background(Context ctxt) {
        this.context = ctxt;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String absent_url = Constants.BASE_URL + "/AttendanceSystem/absent_info.php";
        if (type.equals("Absent")) {
            try {
                String Name = params[1];
                String Class = params[2];
                String Parents_No = params[3];
                String Subject = params[4];
                String Date = params[5];

                URL url = new URL(absent_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8") + "&" + URLEncoder.encode("Section", "UTF-8") + "=" + URLEncoder.encode(Class, "UTF-8") + "&" + URLEncoder.encode("Parents_No", "UTF-8") + "=" + URLEncoder.encode(Parents_No, "UTF-8") + "&" + URLEncoder.encode("Subject", "UTF-8") + "=" + URLEncoder.encode(Subject, "UTF-8") + "&" + URLEncoder.encode("Date", "UTF-8") + "=" + URLEncoder.encode(Date, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream stream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "iso-8859-1"));
                String result = "Inserted ";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                stream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(context,result, Toast.LENGTH_LONG).show();
    }

}

