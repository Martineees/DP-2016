package com.lepko.martin.arquiz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.Entities.Location;
import com.lepko.martin.arquiz.Entities.Question;
import com.lepko.martin.arquiz.R;

import java.util.List;

/**
 * Created by Martin on 6.3.2017.
 */

public class QuestionsAdapter extends BaseAdapter {

    private List<Question> questions;
    private Context mContext;

    public QuestionsAdapter(Context context, List<Question> competitions){
        super();
        this.mContext = context;
        this.questions = competitions;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Question getItem(int i) {
        return questions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return questions.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Question question = getItem(i);
        view = li.inflate(R.layout.row_data_item, viewGroup, false);

        TextView name = (TextView) view.findViewById(R.id.competition_name);
        TextView created = (TextView) view.findViewById(R.id.competition_created);

        name.setText(question.getName());
        created.setText(question.getLocation().getLocation());

        return view;
    }
}
