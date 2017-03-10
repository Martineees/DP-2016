package com.lepko.martin.arquiz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.R;

import java.util.List;

/**
 * Created by Martin on 6.3.2017.
 */

public class CompetitionsAdapter extends BaseAdapter {

    private List<Competition> competitions;
    private Context mContext;

    public CompetitionsAdapter(Context context,List<Competition> competitions){
        super();
        this.mContext = context;
        this.competitions = competitions;
    }

    @Override
    public int getCount() {
        return competitions.size();
    }

    @Override
    public Competition getItem(int i) {
        return competitions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return competitions.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Competition competition = getItem(i);
        view = li.inflate(R.layout.row_data_item, viewGroup, false);

        TextView name = (TextView) view.findViewById(R.id.competition_name);
        TextView created = (TextView) view.findViewById(R.id.competition_created);

        name.setText(competition.getName());
        created.setText(competition.getCreated());

        return view;
    }
}
