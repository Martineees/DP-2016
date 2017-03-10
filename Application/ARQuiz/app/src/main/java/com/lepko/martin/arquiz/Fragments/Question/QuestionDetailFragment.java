package com.lepko.martin.arquiz.Fragments.Question;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lepko.martin.arquiz.Data.DataContainer;
import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.QuestionsActivity;
import com.lepko.martin.arquiz.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDetailFragment extends Fragment {

    private WebView webView;
    private int itemIndex;
    private DataContainer dataContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_competition_detail, container, false);

        dataContainer = DataContainer.getInstance();

        Bundle args = getArguments();
        itemIndex = args.getInt("itemIndex", -1);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = (WebView) getView().findViewById(R.id.competition_detail);
        Competition competition = dataContainer.getCompetitionByIndex(itemIndex);

        webView.loadData(competition.getDescription(), "text/html; charset=utf-8", "UTF-8");
    }

    public void onEnter(View v) {
        Intent intent = new Intent(getContext(), QuestionsActivity.class);

        intent.putExtra("competitionId", dataContainer.getCompetitionByIndex(itemIndex).getId());

        startActivity(intent);
    }
}
