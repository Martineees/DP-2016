package com.lepko.martin.arquiz.AsyncTasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.lepko.martin.arquiz.Utils.Helper;
import com.lepko.martin.arquiz.Utils.Services;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Martin on 10.3.2017.
 */

public class BitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = "SimpleAsyncTask";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
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
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    return bitmap;
                }

            } else return null;

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
