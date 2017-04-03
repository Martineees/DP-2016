package com.lepko.martin.arquiz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lepko.martin.arquiz.Entities.ChartEntity;
import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.R;

import java.util.List;

/**
 * Created by Martin on 6.3.2017.
 */

public class ChartAdapter extends BaseAdapter {

    private List<ChartEntity> entries;
    private Context mContext;
    private int ownerId;

    public ChartAdapter(Context context, List<ChartEntity> entries, int ownerId){
        super();
        this.mContext = context;
        this.entries = entries;
        this.ownerId = ownerId;
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public ChartEntity getItem(int i) {
        return entries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return entries.get(i).getCompetitorId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ChartEntity chartEntity = getItem(i);

        view = null;

        if(chartEntity.getCompetitorId() == ownerId) {
            view = li.inflate(R.layout.row_chart_item_self, viewGroup, false);
        } else {
            view = li.inflate(R.layout.row_chart_item, viewGroup, false);

            TextView userName = (TextView) view.findViewById(R.id.chartUser);
            userName.setText(chartEntity.getUserName());
        }

        TextView position = (TextView) view.findViewById(R.id.chartPosition);
        TextView points = (TextView) view.findViewById(R.id.chartPoints);

        position.setText(mContext.getString(R.string.STR_CHART_POSITION, chartEntity.getPosition()));
        points.setText(Integer.toString(chartEntity.getPoints()));

        return view;
    }
}
