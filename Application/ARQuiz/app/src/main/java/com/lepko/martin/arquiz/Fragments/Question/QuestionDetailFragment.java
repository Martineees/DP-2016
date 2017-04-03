package com.lepko.martin.arquiz.Fragments.Question;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lepko.martin.arquiz.AsyncTasks.BitmapAsyncTask;
import com.lepko.martin.arquiz.CloudRecoActivity;
import com.lepko.martin.arquiz.Data.DataContainer;
import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.Entities.Question;
import com.lepko.martin.arquiz.QuestionsActivity;
import com.lepko.martin.arquiz.R;
import com.lepko.martin.arquiz.Utils.Helper;
import com.lepko.martin.arquiz.Utils.Services;

import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDetailFragment extends Fragment {

    private WebView webView;
    private int questionid;
    private DataContainer dataContainer;
    private ImageView targetImageView;
    private ProgressBar progressBar;
    private Button searchTargetBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_detail, container, false);

        dataContainer = DataContainer.getInstance();

        Bundle args = getArguments();
        questionid = args.getInt("questionId", -1);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = (ProgressBar) getView().findViewById(R.id.detailProgressBar);

        Question question = dataContainer.getQuestionById(questionid);
        List<AbstractMap.SimpleEntry<String, String>> data = new ArrayList<>();
        data.add(new AbstractMap.SimpleEntry<>("target_id", question.getTargetId()));

        targetImageView = (ImageView) getView().findViewById(R.id.target_image_hint);

        searchTargetBtn = (Button) getView().findViewById(R.id.search_target);
        searchTargetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTarget(view);
            }
        });

        try {
            new getTargetAsync().execute(Services.TARGET_IMAGE_URL(), Helper.getQuery(data));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void searchTarget(View v) {
        Intent intent = new Intent(getContext(), CloudRecoActivity.class);
        intent.putExtra("questionId", dataContainer.getQuestionById(questionid).getId());
        startActivity(intent);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    private class getTargetAsync extends BitmapAsyncTask {
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if(targetImageView != null) {
                if(bitmap != null) {
                    targetImageView.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.GONE);
                    targetImageView.setVisibility(View.VISIBLE);

                } else
                    Toast.makeText(getContext(), "No bitmap response", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getContext(), "No bitmap imageView container", Toast.LENGTH_LONG).show();
        }
    }
}
