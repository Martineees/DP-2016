package com.lepko.martin.arquiz.Fragments.Competition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lepko.martin.arquiz.Adapters.CompetitionsAdapter;
import com.lepko.martin.arquiz.CompetitionsActivity;
import com.lepko.martin.arquiz.Data.DataContainer;
import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.Entities.User;
import com.lepko.martin.arquiz.QuestionsActivity;
import com.lepko.martin.arquiz.R;
import com.lepko.martin.arquiz.Utils.PermissionManager;
import com.lepko.martin.arquiz.Utils.SessionManager;

import org.json.JSONException;

import java.util.List;

public class CompetitionsFragment extends ListFragment {

    private ProgressBar progressBar;
    private ListView listView;
    private CompetitionsAdapter competitionsAdapter;
    private DataContainer dataContainer;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_loader, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressBar = (ProgressBar) getView().findViewById(R.id.progressBar);
        listView = getListView();
        dataContainer = DataContainer.getInstance();
        sessionManager = new SessionManager(getContext());
    }

    public void resetFragmentView() {
        if(progressBar != null && listView != null) {
            progressBar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    public void setAdapter(Context context, List<Competition> competitions) {
        competitionsAdapter = new CompetitionsAdapter(context, competitions);
        setListAdapter(competitionsAdapter);
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    public void onError() {progressBar.setVisibility(View.GONE);}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        try {
            if(isAlreadyInCompetition(position)) {
                if(PermissionManager.hasPermissions(getActivity(), PermissionManager.PERMISSIONS_GROUP_LOCATION)) {
                    //Continue
                } else {
                    requestPermissions(PermissionManager.PERMISSIONS_GROUP_LOCATION, PermissionManager.PERMISSION_REQUEST_LOCATION);
                }
            } else {
                CompetitionDetailFragment competitionDetailFragment = new CompetitionDetailFragment();

                Bundle args = new Bundle();
                args.putInt("competitionId", (int) id);
                competitionDetailFragment.setArguments(args);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.data_container, competitionDetailFragment);

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isAlreadyInCompetition(int index) throws JSONException {
        Competition competition = dataContainer.getCompetitionByIndex(index);
        User user = sessionManager.getUserData();

        if(user == null) return false;

        return user.isInCompetition(competition.getId());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PermissionManager.PERMISSION_REQUEST_LOCATION) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),"Permission was granted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),"You do not have needed permissions.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
