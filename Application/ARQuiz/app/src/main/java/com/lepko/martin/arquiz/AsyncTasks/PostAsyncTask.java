package com.lepko.martin.arquiz.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.lepko.martin.arquiz.Utils.Helper;
import com.lepko.martin.arquiz.Utils.Services;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Martin on 10.3.2017.
 */

public class PostAsyncTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "PostAsyncTask";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(4000);
            urlConnection.setConnectTimeout(4000);
            urlConnection.setRequestMethod(Services.METHOD_POST);
            urlConnection.setDoInput(true);
            urlConnection.setDoInput(true);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bw.write(params[1]);
            bw.flush();
            bw.close();
            os.close();

            int respCode = urlConnection.getResponseCode();

            if(respCode == HttpsURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String result = Helper.getStringFromInputStream(in);
                Log.d(TAG, result);

                return result;
            } else
                return "";

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
