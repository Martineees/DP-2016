package com.lepko.martin.arquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lepko.martin.arquiz.AsyncTasks.PostAsyncTask;
import com.lepko.martin.arquiz.Data.DataContainer;
import com.lepko.martin.arquiz.Entities.Answer;
import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.Entities.Competitor;
import com.lepko.martin.arquiz.Entities.Question;
import com.lepko.martin.arquiz.Entities.User;
import com.lepko.martin.arquiz.Utils.Helper;
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

public class AnswerActivity extends Activity {

    private SessionManager sessionManager;
    private TextView note;
    private TextView answerResult;
    private Button loginBtn;
    private Button nextQuestion;
    private DataContainer dataContainer;
    private ProgressBar pb;
    private User user;
    private Competition currentCompetition;
    private int questionType;
    private boolean isCorrect;

    private int questionId;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        intent = getIntent();
        questionId = intent.getIntExtra("questionId", -1);
        questionType = intent.getIntExtra("questionType", -1);

        isCorrect = false;

        note = (TextView) findViewById(R.id.noteTextView);
        answerResult = (TextView) findViewById(R.id.answerResult);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        nextQuestion = (Button) findViewById(R.id.nextQuesitonBtn);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        dataContainer = DataContainer.getInstance();

        sessionManager = new SessionManager(getApplicationContext());

        currentCompetition = dataContainer.getCurrentCompetition();

        updateAnswerActivity();
    }

    private void evaluateOptionsType() {
        int answerIndex = intent.getIntExtra("answerIndex", -1);

        if(answerIndex != -1) {
            if (questionId != -1) {
                Question q = dataContainer.getQuestionById(questionId);

                if (q != null) {
                    Answer answer = q.getAnswers().get(answerIndex);

                    if (answer.isCorrect())
                        isCorrect = true;
                }
            }
        }
    }

    private void evaluateWriteableType() {
        String userAnswer = intent.getStringExtra("userAnswer");

        if(!userAnswer.isEmpty()) {
            if(questionId != -1) {
                Question q = dataContainer.getQuestionById(questionId);

                if(q != null) {
                    Answer answer = q.getAnswers().get(0);

                    if(answer.getName().equals(userAnswer))
                        isCorrect = true;
                }
            }
        }
    }

    private void getAnswerResult() {
        switch(questionType) {
            case 0:
                evaluateOptionsType();
                break;
            case 1:
                evaluateWriteableType();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Wrong question type", Toast.LENGTH_LONG).show();
        }
    }

    private void updateAnswerActivity() {
        if(sessionManager.isLoggedIn()) {

            try {
                user = sessionManager.getUserData();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            pb.setVisibility(View.VISIBLE);
            nextQuestion.setVisibility(View.GONE);
            loginBtn.setVisibility(View.GONE);
            answerResult.setText("");
            note.setText("");

            getAnswerResult();

            startAsyncTasks();

        } else {
            pb.setVisibility(View.GONE);
            nextQuestion.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
            answerResult.setText(getString(R.string.STR_UNKNOW_ANSWER));
            note.setText(getString(R.string.STR_LOGIN_REQUIRED));
        }
    }

    public void onNextQuestion(View v) {
        finish();
    }

    public void onLogin(View v) {
        Intent intent = new Intent(AnswerActivity.this, LoginActivity.class);
        startActivityForResult(intent, MainActivity.LOGIN_STATE_REQUEST);
    }

    private void startAsyncTasks() {

        if(sessionManager.isLoggedIn()) {

            if(user.isInCompetition(currentCompetition.getId())) {
                logAnswer();
            } else {
                enterToCompetition();
            }

        }
    }

    private void logAnswer() {
        List<AbstractMap.SimpleEntry<String, String>> data = new ArrayList<>();

        Competitor competitor = user.getCompetitorByCompetitionId(currentCompetition.getId());

        data.add(new AbstractMap.SimpleEntry<>("question_id", Integer.toString(questionId)));
        data.add(new AbstractMap.SimpleEntry<>("competitor_id", Integer.toString(competitor.getId())));
        data.add(new AbstractMap.SimpleEntry<>("is_correct", isCorrect ? "1" : "0"));

        try {
            new LogAnswerAsync().execute(Services.LOG_ANSWER_URL(), Helper.getQuery(data));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void enterToCompetition() {
        List<AbstractMap.SimpleEntry<String, String>> data = new ArrayList<>();
        data.add(new AbstractMap.SimpleEntry<>("user_id", "" + user.getId()));
        data.add(new AbstractMap.SimpleEntry<>("competition_id", "" + currentCompetition.getId()));

        try {
            new EnterCompetition().execute(Services.ENTER_COMPETITION_URL(), Helper.getQuery(data));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void onLogAnswerSuccess(JSONObject response) {
        Competitor competitor = user.getCompetitorByCompetitionId(currentCompetition.getId());
        competitor.addAnsweredQuestion(questionId);
        user.updateCompetitorAnsweredQuestionList(competitor);

        try {
            sessionManager.updateUserData(Helper.userToJSON(user).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Question question = dataContainer.getQuestionById(questionId);

        pb.setVisibility(View.GONE);
        nextQuestion.setVisibility(View.VISIBLE);

        if(isCorrect) {
            answerResult.setText(getString(R.string.STR_ANSWER_RESULT_C));
            note.setText(getString(R.string.STR_ANSWER_SCORE, question.getScore()));
        } else {
            answerResult.setText(getString(R.string.STR_ANSWER_RESULT_W));
            note.setText(getString(R.string.STR_ANSWER_SCORE, 0));
        }
    }

    private void onEnterSuccess(JSONObject response) throws JSONException {
        int competitorId = response.getInt("competitor_id");
        int competitionId = dataContainer.getCurrentCompetition().getId();

        List<Integer> answeredQuestions = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(response.getString("answers_list"));
        for(int i = 0; i<jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            answeredQuestions.add(obj.getInt("questionId"));
        }

        Competitor competitor = new Competitor(competitorId, competitionId, answeredQuestions);

        user.addCompetitor(competitor);

        sessionManager.updateUserData(Helper.userToJSON(user).toString());

        //after enter to competition log answer
        logAnswer();
    }

    private void onError() {
        Toast.makeText(getApplicationContext(), "No response from server", Toast.LENGTH_LONG).show();
    }

    private void onError(JSONObject response) throws JSONException {
        Toast.makeText(getApplicationContext(), response.getString("error_msg"), Toast.LENGTH_LONG).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MainActivity.LOGIN_STATE_REQUEST && resultCode == RESULT_OK) {
            updateAnswerActivity();
        }
    }

    private class LogAnswerAsync extends PostAsyncTask {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null && !s.isEmpty()) {
                try {
                    JSONObject response = Helper.string2JSON(s);
                    if(!response.getBoolean("error"))
                        onLogAnswerSuccess(response);
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

    private class EnterCompetition extends PostAsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
