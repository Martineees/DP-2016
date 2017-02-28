package com.lepko.martin.arquiz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class LocationActivity extends Activity {

    private static final String TAG = "LocationActivity";

    private WifiManager wifi;
    private LocationReceiver locationReceiver;
    private TextView locationTextView;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        pd = new ProgressDialog(this);

        locationTextView = (TextView) findViewById(R.id.location_status_bar);

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        locationReceiver = new LocationReceiver();

        registerReceiver(locationReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        scanWifi(null);
    }

    public void scanWifi(View v) {
        if (!wifi.isWifiEnabled())
        {
            Toast.makeText(getApplicationContext(), getString(R.string.WIFI_PERMISSION), Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }

        wifi.startScan();
        Helper.showProgressDialog(pd, getString(R.string.WIFI_GETTING_DATA));
    }

    private void onLocationSuccess(JSONObject response) throws JSONException {
        locationTextView.setText(getString(R.string.STR_ACTUAL_POSITION_p, response.getString("block"),response.getInt("floor")));
    }

    private void onLocationError(JSONObject response) throws JSONException {
        locationTextView.setText(response.getString("error_msg"));
    }

    private void onLocationError() {
        locationTextView.setText("No response from server");
    }

    private class LocationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> results = wifi.getScanResults();
            Log.i(TAG, String.valueOf(results.size()));

            try {
                new LocationAsyncTask().execute(Services.LOCATION_URL(), Helper.scanResults2JSON(results));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class LocationAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Helper.showProgressDialog(pd,"Finding your location");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null && !s.isEmpty()) {
                try {
                    JSONObject response = Helper.string2JSON(s);
                    if(!response.getBoolean("error"))
                        onLocationSuccess(response);
                    else
                        onLocationError(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                onLocationError();
            }

            Helper.dismissProgressDialog(pd);
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

                List<AbstractMap.SimpleEntry<String, String>> data = new ArrayList<>();

                Log.i(TAG, params[1]);
                data.add(new AbstractMap.SimpleEntry<>("scanResults", params[1]));

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bw.write(Helper.getQuery(data));
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
}
