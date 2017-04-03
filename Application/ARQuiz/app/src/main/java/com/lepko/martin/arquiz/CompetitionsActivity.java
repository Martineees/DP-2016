package com.lepko.martin.arquiz;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lepko.martin.arquiz.AsyncTasks.SimpleAsyncTask;
import com.lepko.martin.arquiz.Data.DataContainer;
import com.lepko.martin.arquiz.Fragments.Competition.CompetitionsFragment;
import com.lepko.martin.arquiz.Utils.Helper;
import com.lepko.martin.arquiz.Utils.Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CompetitionsActivity extends FragmentActivity {

    private static final String TAG = "CompetitionsActivity";

    private CompetitionsFragment competitionsFragment;

    private DataContainer dataContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        dataContainer = DataContainer.getInstance();

        TextView headline1 = (TextView) findViewById(R.id.headline1);
        headline1.setText(getString(R.string.STR_COMPETITIONS));

        TextView headline2 = (TextView) findViewById(R.id.headline2);
        headline2.setText(getString(R.string.STR_COMPETITIONS_LIST));

        FrameLayout fragmentContainer = (FrameLayout) findViewById(R.id.data_container);

        if(fragmentContainer != null) {

            if (savedInstanceState != null) return;

            competitionsFragment = new CompetitionsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.data_container, competitionsFragment).commit();

            callAsyncTasks();
        }
    }

    public void callAsyncTasks() {
        competitionsFragment.resetFragmentView();
        new CompetitionsAsyncTask().execute(Services.COMPETITIONS_URL());
    }

    @Override
    protected void onResume() {
        super.onResume();

        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 0) {
            callAsyncTasks();
        }
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i(TAG, "popping backstack");
            fm.popBackStack();
            callAsyncTasks();
        } else {
            Log.i(TAG, "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    private void onCompetitionsSuccess(JSONObject response) throws JSONException {
        dataContainer.setCompetitions(Helper.parseCompetitionsJSON(new JSONArray(response.getString("competitions"))));
        competitionsFragment.setAdapter(getApplicationContext(), dataContainer.getCompetitions());
    }

    private void onCompetitionsError(JSONObject response) throws JSONException {
        competitionsFragment.onError();
        Toast.makeText(this, response.getString("error_msg"), Toast.LENGTH_LONG).show();
    }

    private void onCompetitionsError() {
        competitionsFragment.onError();
        Toast.makeText(this, "No response from server", Toast.LENGTH_LONG).show();
    }

    private class CompetitionsAsyncTask extends SimpleAsyncTask {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null && !s.isEmpty()) {
                try {
                    JSONObject response = Helper.string2JSON(s);
                    if(!response.getBoolean("error"))
                        onCompetitionsSuccess(response);
                    else
                        onCompetitionsError(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "JSON error", Toast.LENGTH_LONG).show();
                }

            } else {
                onCompetitionsError();
            }
        }
    }
}
