package com.lepko.martin.arquiz;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lepko.martin.arquiz.AsyncTasks.PostAsyncTask;
import com.lepko.martin.arquiz.Data.DataContainer;
import com.lepko.martin.arquiz.Entities.Location;
import com.lepko.martin.arquiz.Entities.Question;
import com.lepko.martin.arquiz.Fragments.Question.QuestionFragment;
import com.lepko.martin.arquiz.Utils.Helper;
import com.lepko.martin.arquiz.Utils.Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends FragmentActivity {

    private static final String TAG = "QuestionsActivity";

    private WifiManager wifi;
    private TextView locationTextView;
    private ProgressDialog pd;
    private DataContainer dataContainer;
    private Location actualLocation;
    private QuestionFragment questionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        Intent intent = getIntent();
        int competitionId = intent.getIntExtra("competitionId", -1);

        pd = new ProgressDialog(this);
        dataContainer = DataContainer.getInstance();
        actualLocation = null;

        TextView headline1 = (TextView) findViewById(R.id.headline1);
        headline1.setText(getString(R.string.STR_QUESTIONS_LIST));

        locationTextView = (TextView) findViewById(R.id.headline2);
        locationTextView.setText(getString(R.string.STR_ACTUAL_POSITION));

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        FrameLayout fragmentContainer = (FrameLayout) findViewById(R.id.competitions_container);

        if(fragmentContainer != null) {

            if (savedInstanceState != null) return;

            questionFragment = new QuestionFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.competitions_container, questionFragment).commit();

            List<AbstractMap.SimpleEntry<String, String>> data = new ArrayList<>();
            data.add(new AbstractMap.SimpleEntry<>("competitionId", "" + competitionId));

            try {
                new QuestionsAsyncTask().execute(Services.QUESTIONS_URL(), Helper.getQuery(data));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //scanWifi(null);
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
        actualLocation = Helper.json2Location(response);
        locationTextView.setText(getString(R.string.STR_ACTUAL_POSITION_p, actualLocation.getLocation()));

        questionFragment.setAdapter(getApplicationContext(),
                dataContainer.getQuestionsByLocation(actualLocation.getId()));
    }

    private void onQuestionsSuccess(JSONObject response) throws JSONException {
        List<Question> questions = Helper.parseQuestionsJSON(
                new JSONArray(response.getString("questions")));
        dataContainer.setQuestions(questions);

        LocationReceiver locationReceiver = new LocationReceiver();
        registerReceiver(locationReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        scanWifi(null);
    }

    private void onAsyncError(JSONObject response) throws JSONException {
        locationTextView.setText(response.getString("error_msg"));
    }

    private void onAsyncError() {
        locationTextView.setText("No response from server");
    }

    private class LocationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> results = wifi.getScanResults();
            Log.i(TAG, String.valueOf(results.size()));

            List<AbstractMap.SimpleEntry<String, String>> data = new ArrayList<>();

            try {
                data.add(new AbstractMap.SimpleEntry<>("scanResults", Helper.scanResults2JSON(results)));
                new LocationAsyncTask().execute(Services.LOCATION_URL(), Helper.getQuery(data));

            } catch (JSONException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private class LocationAsyncTask extends PostAsyncTask {
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
                        onAsyncError(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                onAsyncError();
            }

            Helper.dismissProgressDialog(pd);
        }
    }

    private class QuestionsAsyncTask extends PostAsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Helper.showProgressDialog(pd,"Getting competition data");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null && !s.isEmpty()) {
                try {
                    JSONObject response = Helper.string2JSON(s);
                    if(!response.getBoolean("error"))
                        onQuestionsSuccess(response);
                    else
                        onAsyncError(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                onAsyncError();
            }

            Helper.dismissProgressDialog(pd);
        }
    }


}
