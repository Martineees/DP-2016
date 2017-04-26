package com.lepko.martin.arquiz.Fragments.Competition;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.lepko.martin.arquiz.AsyncTasks.PostAsyncTask;
import com.lepko.martin.arquiz.Data.DataContainer;
import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.Entities.Competitor;
import com.lepko.martin.arquiz.Entities.User;
import com.lepko.martin.arquiz.QuestionsActivity;
import com.lepko.martin.arquiz.R;
import com.lepko.martin.arquiz.Utils.Helper;
import com.lepko.martin.arquiz.Utils.PermissionManager;
import com.lepko.martin.arquiz.Utils.Services;
import com.lepko.martin.arquiz.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompetitionDetailFragment extends Fragment {

    private WebView webView;
    private int competitionId;
    private DataContainer dataContainer;
    private Button enterCompetitionBtn;
    private ProgressDialog pd;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_competition_detail, container, false);

        dataContainer = DataContainer.getInstance();

        Bundle args = getArguments();
        competitionId = args.getInt("competitionId", -1);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = (WebView) getView().findViewById(R.id.competition_detail);
        Competition competition = dataContainer.getCompetitionById(competitionId);
        sessionManager = new SessionManager(getContext());

        webView.loadData(competition.getDescription(), "text/html; charset=utf-8", "UTF-8");

        enterCompetitionBtn = (Button) getView().findViewById(R.id.competition_enter);

        enterCompetitionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEnter(view);
            }
        });

        pd = new ProgressDialog(getContext());
    }

    public void onEnter(View v) {

        if(sessionManager.isLoggedIn()) {

            User user = null;
            try {
                user = sessionManager.getUserData();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            List<AbstractMap.SimpleEntry<String, String>> data = new ArrayList<>();
            data.add(new AbstractMap.SimpleEntry<>("user_id", "" + user.getId()));
            data.add(new AbstractMap.SimpleEntry<>("competition_id", "" + dataContainer.getCompetitionById(competitionId).getId()));

            try {
                new EnterCompetition().execute(Services.ENTER_COMPETITION_URL(), Helper.getQuery(data));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            if(PermissionManager.hasPermissions(getActivity(), PermissionManager.PERMISSIONS_GROUP_LOCATION)) {
                goToQuestionList();
            } else {
                requestPermissions(PermissionManager.PERMISSIONS_GROUP_LOCATION, PermissionManager.PERMISSION_REQUEST_LOCATION);
            }

        }
    }

    private void goToQuestionList() {
        Competition competition = dataContainer.getCompetitionById(competitionId);
        dataContainer.setCurrentCompetition(competition);

        Intent intent = new Intent(getContext(), QuestionsActivity.class);
        intent.putExtra("competitionId", competition.getId());
        startActivity(intent);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PermissionManager.PERMISSION_REQUEST_LOCATION) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),"Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),"You do not have needed permissions.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onEnterSuccess(JSONObject response) throws JSONException {
        int competitorId = response.getInt("competitor_id");

        List<Integer> answeredQuestions = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(response.getString("answers_list"));
        for(int i = 0; i<jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            answeredQuestions.add(obj.getInt("questionId"));
        }

        Competitor competitor = new Competitor(competitorId, competitionId, answeredQuestions);
        User user = sessionManager.getUserData();

        user.addCompetitor(competitor);

        sessionManager.updateUserData(Helper.userToJSON(user).toString());

        goToQuestionList();
    }

    private void onEnterError() {
        Toast.makeText(getContext(), "No response from server", Toast.LENGTH_LONG).show();
    }

    private void onEnterError(JSONObject response) throws JSONException {
        Toast.makeText(getContext(), response.getString("error_msg"), Toast.LENGTH_LONG).show();
    }

    private class EnterCompetition extends PostAsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Helper.showProgressDialog(pd, "Entering ...");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null && !s.isEmpty()) {
                try {
                    JSONObject response = Helper.string2JSON(s);
                    if(!response.getBoolean("error"))
                        onEnterSuccess(response);
                    else
                        onEnterError(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                onEnterError();
            }

            Helper.dismissProgressDialog(pd);
        }
    }
}
