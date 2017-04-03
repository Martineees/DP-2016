package com.lepko.martin.arquiz;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lepko.martin.arquiz.Adapters.ChartAdapter;
import com.lepko.martin.arquiz.AsyncTasks.PostAsyncTask;
import com.lepko.martin.arquiz.AsyncTasks.SimpleAsyncTask;
import com.lepko.martin.arquiz.Data.DataContainer;
import com.lepko.martin.arquiz.Entities.ChartEntity;
import com.lepko.martin.arquiz.Entities.Competitor;
import com.lepko.martin.arquiz.Fragments.Competition.CompetitionsFragment;
import com.lepko.martin.arquiz.Utils.Helper;
import com.lepko.martin.arquiz.Utils.Services;
import com.lepko.martin.arquiz.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends ListActivity {

    private static final String TAG = "ChartActivity";
    private DataContainer dataContainer;
    private ProgressBar pb;
    private ListView listView;
    private int competitionId;
    private Competitor competitor;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent intent = getIntent();
        competitionId = intent.getIntExtra("competitionId", -1);

        pb = (ProgressBar) findViewById(R.id.progressBar);

        sessionManager = new SessionManager(getApplicationContext());
        dataContainer = DataContainer.getInstance();

        try {
            competitor = sessionManager.getUserData().getCompetitorByCompetitionId(competitionId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = getListView();

        getActualChart(null);
    }

    public void getActualChart(View v) {
        List<AbstractMap.SimpleEntry<String, String>> data = new ArrayList<>();
        data.add(new AbstractMap.SimpleEntry<>("competition_id", Integer.toString(competitionId)));

        try {
            new ChartAsync().execute(Services.CHART_URL(), Helper.getQuery(data));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private int getCompetitorIndex(List<ChartEntity> chartEntities) {
        for(int i = 0; i<chartEntities.size(); i++) {
            ChartEntity entity = chartEntities.get(i);

            if(entity.getCompetitorId() == competitor.getId())
                return i;
        }

        return 0;
    }

    private void onChartSuccess(JSONObject response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response.getString("chart"));
        List<ChartEntity> chartEntities = Helper.parseChartJSON(jsonArray);

        int listFocusIndex = getCompetitorIndex(chartEntities);

        ChartAdapter chartAdapter = new ChartAdapter(getApplicationContext(), chartEntities, competitor.getId());
        setListAdapter(chartAdapter);

        pb.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        getListView().smoothScrollToPosition(listFocusIndex);
    }

    private void onError() {
        Toast.makeText(getApplicationContext(), "No response from server", Toast.LENGTH_LONG).show();
    }

    private void onError(JSONObject response) throws JSONException {
        Toast.makeText(getApplicationContext(), response.getString("error_msg"), Toast.LENGTH_LONG).show();
    }

    private class ChartAsync extends PostAsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pb.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null && !s.isEmpty()) {
                try {
                    JSONObject response = Helper.string2JSON(s);
                    if(!response.getBoolean("error"))
                        onChartSuccess(response);
                    else
                        onError(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                onError();
            }
        }
    }
}
