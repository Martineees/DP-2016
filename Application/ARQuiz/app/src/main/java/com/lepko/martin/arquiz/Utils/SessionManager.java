package com.lepko.martin.arquiz.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.lepko.martin.arquiz.Entities.Competitor;
import com.lepko.martin.arquiz.Entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Martin on 24.2.2017.
 */

public class SessionManager {

    private static final String TAG = "SessionManager";

    private static final String PREF_NAME = "Session";
    private static final String PREF_KEY_LOGIN = "isLoggedIn";
    private static final String PREF_KEY_USER = "userData";
    private static final int PRIVATE_MODE = 0;

    private SharedPreferences sharedPreferences;
    private Editor editor;

    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLoggedIn, String userData) {
        Log.d(TAG, "Setting user login: " + isLoggedIn);

        if(isLoggedIn) {
            editor.putBoolean(PREF_KEY_LOGIN, isLoggedIn);
            editor.putString(PREF_KEY_USER, userData);
        } else {
            editor.remove(PREF_KEY_LOGIN);
            editor.remove(PREF_KEY_USER);
        }
        editor.commit();
    }

    public void updateUserData(String userData) {
        Log.d(TAG, "Updating user data");

        if(isLoggedIn()) {
            editor.putString(PREF_KEY_USER, userData);
            editor.commit();
        }
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(PREF_KEY_LOGIN,false);
    }

    public User getUserData() throws JSONException {

        User user = null;
        String userJSON = sharedPreferences.getString(PREF_KEY_USER, "");
        if(!userJSON.isEmpty()) {
            JSONObject userObj = new JSONObject(userJSON);
            List<Competitor> competitorList = new LinkedList<>();

            JSONArray competitorsArray = new JSONArray(userObj.getString("competitors"));

            for(int i = 0; i < competitorsArray.length(); i++) {
                JSONObject obj = competitorsArray.getJSONObject(i);

                List<Integer> answeredQuestions = new LinkedList<>();
                JSONArray answeredQuestionsArray = new JSONArray(obj.getString("answers_list"));

                for(int j=0; j < answeredQuestionsArray.length(); j++) {
                    JSONObject answerObj = answeredQuestionsArray.getJSONObject(j);
                    answeredQuestions.add(answerObj.getInt("questionId"));
                }

                Competitor competitor = new Competitor(obj.getInt("id"), obj.getInt("competition_id"), answeredQuestions);
                competitorList.add(competitor);
            }

            user = new User(userObj.getString("user"), userObj.getInt("user_id"), userObj.getInt("is_admin") == 1, competitorList);

        } else Log.d(TAG,"Empty userJSON data");

        return user;
    }

}
