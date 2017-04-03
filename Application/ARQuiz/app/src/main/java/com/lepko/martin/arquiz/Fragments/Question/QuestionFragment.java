package com.lepko.martin.arquiz.Fragments.Question;

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
import com.lepko.martin.arquiz.Adapters.QuestionsAdapter;
import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.Entities.Question;
import com.lepko.martin.arquiz.Fragments.Competition.CompetitionDetailFragment;
import com.lepko.martin.arquiz.QuestionsActivity;
import com.lepko.martin.arquiz.R;

import java.util.List;

public class QuestionFragment extends ListFragment {

    private ProgressBar progressBar;
    private ListView listView;
    private QuestionsAdapter questionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_loader, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressBar = (ProgressBar) getView().findViewById(R.id.progressBar);
        listView = getListView();

        //QuestionsActivity activity = (QuestionsActivity) getActivity();
        //activity.callAsyncTasks();
    }

    public void resetFragmentView() {
        if(progressBar != null && listView != null) {
            progressBar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    public void setAdapter(Context context, List<Question> questions) {
        questionsAdapter = new QuestionsAdapter(context, questions);
        setListAdapter(questionsAdapter);
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    public void onError() {progressBar.setVisibility(View.GONE);}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        QuestionDetailFragment questionDetailFragment = new QuestionDetailFragment();

        Bundle args = new Bundle();
        args.putInt("questionId", (int) id);
        questionDetailFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.data_container, questionDetailFragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
