package com.lepko.martin.arquiz.Fragments.Competition;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.lepko.martin.arquiz.Adapters.CompetitionsAdapter;
import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.R;

import java.util.List;

public class CompetitionsFragment extends ListFragment {

    private ProgressBar progressBar;
    private ListView listView;
    private CompetitionsAdapter competitionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_loader, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressBar = (ProgressBar) getView().findViewById(R.id.progressBar);
        listView = getListView();
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

        CompetitionDetailFragment competitionDetailFragment = new CompetitionDetailFragment();

        Bundle args = new Bundle();
        args.putInt("itemIndex", position);
        competitionDetailFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.competitions_container, competitionDetailFragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
